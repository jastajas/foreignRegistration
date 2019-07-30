package com.example.foreign_registration.tools.process;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.model.process.ModelCooperation;
import com.example.foreign_registration.model.process.ProcessForm;
import com.example.foreign_registration.repository.app.*;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.Factory;
import com.example.foreign_registration.tools.general.ObjectNumberCreator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcessFactory extends Factory {


    private ProcessRepository processRepo;
    private ProductRepository productRepo;
    private ProductQualificationRepository prodQualificationRepo;
    private ClientRepository clientRepo;
    private StatusRepository statusRepo;
    private UserRepository userRepo;

    public ProcessFactory(DateGenerator dateGenerator, ProcessRepository processRepo, ProductRepository productRepo, ProductQualificationRepository prodQualificationRepo, ClientRepository clientRepo, StatusRepository statusRepo, UserRepository userRepo) {
        super(dateGenerator);
        this.processRepo = processRepo;
        this.productRepo = productRepo;
        this.prodQualificationRepo = prodQualificationRepo;
        this.clientRepo = clientRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
    }


    @Override
    public String createNumber() throws OversizeNumberException {
        String maxCurrentProcessNumber = processRepo
                .getMaxProcessNoForCurrentYear(getDateGenerator().getFistDateOfCurrentYear(), getDateGenerator().getCurrentDate())
                .orElse("00000")
                .substring(0, 5);

        return ObjectNumberCreator.createObjectNo(maxCurrentProcessNumber, getDateGenerator().getCurrentYearText(), AppObjectType.ORDER);
    }

    @Override
    public AppObject create(AppObjectForm appObjectForm) throws OversizeNumberException, BuildingAppObjectException {
        if (!(appObjectForm instanceof ProcessForm)) {
            throw new BuildingAppObjectException("Process Form Exception");
        }
        ProcessForm processForm = (ProcessForm) appObjectForm;
        if (processForm.isValid()) {
            throw new BuildingAppObjectException("Process Form Validation Error");
        }

        Process process = new Process();
        process.setNumber(createNumber());

        productRepo
                .findById(processForm.getIdProduct())
                .ifPresentOrElse(product -> process.setProduct(product), () -> new BuildingAppObjectException("Product not found"));

        clientRepo
                .findById(processForm.getIdClient())
                .ifPresentOrElse(client -> process.setClient(client), () -> new BuildingAppObjectException("Client not found"));

        prodQualificationRepo
                .findById(processForm.getIdProductQualification())
                .ifPresentOrElse(productQualification -> process.setProduct_qualification(productQualification), () -> new BuildingAppObjectException("Product Qualification not found"));

        userRepo
                .findByUsername(processForm.getCreatorUsername())
                .ifPresentOrElse(user -> process.setCreator(user), () -> new BuildingAppObjectException("User/Creator not found"));

        statusRepo
                .findById((long) 1)
                .ifPresentOrElse(status -> process.setStatus(status), () -> new BuildingAppObjectException("Status not found"));
        //Enum exception? element not found
        try {
            process.setModel_cooperation(ModelCooperation.valueOf(processForm.getModelCooperation()));
        }catch (IllegalArgumentException a){
            throw new BuildingAppObjectException("Model Cooperation exception. IllegalArgumentException");
        }
        process.setDestined_product_name(processForm.getDestinedProductName());
        process.setCreationDate(getDateGenerator().getCurrentDate());

        processRepo.save(process);
        return processRepo
                .findByNumber(process.getNumber())
                .orElseThrow(() -> new BuildingAppObjectException("Object process not found"));

    }
}
