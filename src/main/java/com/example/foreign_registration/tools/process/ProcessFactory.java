package com.example.foreign_registration.tools.process;


import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.model.process.ModelCooperation;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.ObjectNumberCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


import java.util.Optional;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ProcessFactory {

private ProcessRepository processRepository;

@Autowired
public ProcessFactory(ProcessRepository processRepository){
	this.processRepository = processRepository;
}

public Optional<Process> createOptionalNewProcess(String maxCurrentNo, Optional<Product> product, String destinedProductName,
                                                  Optional<ProductQualification> product_qualification, Optional<Status> status,
                                                  Optional<Client> client, Optional<User> order_owner, ModelCooperation model_cooperation,
                                                  DateGenerator dateGenerator, ProcessRepository processRepository) {

        String processNo = ObjectNumberCreator.createObjectNo(maxCurrentNo, dateGenerator.getCurrentYearText(), AppObject.ORDER);

        if (processNo.substring(0,5).equals("99999")){
            return null;
        }

        if (null != product.get() && product.isPresent() && null != product_qualification.get() && product_qualification.isPresent() &&
                null != status.get() && status.isPresent() && null != client.get() && client.isPresent() &&
                null != order_owner.get() && order_owner.isPresent()) {
            Process process = new Process(processNo, product.get(), destinedProductName, product_qualification.get(), status.get(), client.get(), order_owner.get(), model_cooperation, dateGenerator.getCurrentDate());
            processRepository.save(process);
            return processRepository.findByOrderNo(processNo);
        }
        return null;
    }

}
