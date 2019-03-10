package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.*;
import com.example.foreign_registration.model.process.ModelCooperation;
import com.example.foreign_registration.repository.app.*;
import com.example.foreign_registration.repository.assessment.*;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.tools.assessment.AssessmentFactory;
import com.example.foreign_registration.tools.assessment.AssessmentViewTool;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.DynamicSqlSyntaxBuilder;
import com.example.foreign_registration.tools.general.FilterManager;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.tools.general.ObjectsBinder;
import com.example.foreign_registration.tools.process.ProcessFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AssessmentController {

    private AssessmentRepository ar;
    private ClientRepository cr;
    private ProcessRepository procR;
    private ProductRepository prodR;
    private CountryRepository countR;
    private ProductQualificationRepository pqr;
    private ProductStatusRepository psr;
    private FilterParameter filterParameter;
    private AssessmentDAO assessmentDAO;
    private DynamicSqlSyntaxBuilder dynamicSqlSyntaxBuilder;
    private FilterManager filterManager;
    private StatusRepository sr;
    private DepartmentRepository dr;
    private UserRepository ur;
    private DepartmentAssessmentRepository daR;
    private AssessmentCostMHRepository assessCostMhR;
    private DateGenerator dateGenerator;
    private RequirementRepository rrr;
    private AssessmentViewTool assessmentViewTool;
    private UnitRepository unitRepository;
    private PackageSizeRepository packageSizeR;
    private AssessmentPatternRepository apr;
    private AssessDepartConfirmationRepository adcr;

    public AssessmentController(AssessmentRepository ar, ClientRepository cr, ProcessRepository procR,
                                ProductRepository prodR, CountryRepository countR, ProductQualificationRepository pqr,
                                ProductStatusRepository psr, FilterParameter filterParameter, AssessmentDAO assessmentDAO,
                                DynamicSqlSyntaxBuilder dynamicSqlSyntaxBuilder, FilterManager filterManager, StatusRepository sr,
                                DepartmentRepository dr, UserRepository ur, DepartmentAssessmentRepository daR, AssessmentCostMHRepository assessCostMhR,
                                DateGenerator dateGenerator, RequirementRepository rrr, AssessmentViewTool assessmentViewTool, UnitRepository unitRepository,
                                PackageSizeRepository packageSizeR, AssessmentPatternRepository apr, AssessDepartConfirmationRepository adcr) {
        this.ar = ar;
        this.cr = cr;
        this.procR = procR;
        this.prodR = prodR;
        this.countR = countR;
        this.pqr = pqr;
        this.psr = psr;
        this.filterParameter = filterParameter;
        this.assessmentDAO = assessmentDAO;
        this.dynamicSqlSyntaxBuilder = dynamicSqlSyntaxBuilder;
        this.sr = sr;
        this.filterManager = filterManager;
        this.dr = dr;
        this.ur = ur;
        this.daR = daR;
        this.assessCostMhR = assessCostMhR;
        this.dateGenerator = dateGenerator;
        this.rrr = rrr;
        this.assessmentViewTool = assessmentViewTool;
        this.unitRepository = unitRepository;
        this.packageSizeR = packageSizeR;
        this.apr = apr;
        this.adcr = adcr;
    }


    @GetMapping("/assessmentList")
    public String showAssessmentList(Model model, HttpServletRequest request) {

        List<Assessment> assessments = null;
        //boolean isArrayCorrect = true;

        if (filterParameter.getFiltersList().isEmpty() || filterParameter.getFiltersList() == null) {
            assessments = ar.findAll();
        } else if (filterParameter.getFiltersList().containsKey("byKeyword")) {
            assessments = ar.listAllByKeyWord(filterParameter.getFiltersList().get("byKeyword"));
        } else if (filterManager.isOtherThenKeyWord(filterParameter.getFiltersList())) {
            assessments = assessmentDAO.getFilteredAssessments(dynamicSqlSyntaxBuilder.buildFilterQuery(filterParameter.getFiltersList()));
        }

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        model.addAttribute("assessmentsList", assessments);
        model.addAttribute("filterList", filterParameter.getFiltersList());
        model.addAttribute("statusList", sr.getByCategoryIs("assessment"));
        model.addAttribute("modelCoopList", ModelCooperation.values());

        return "assessmentList";
    }


    //TODO zmienic na posta
    @GetMapping("/assessmentList/addFilter")
    public String removeFilterParam(
            @RequestParam(required = false) String byKeyword,
            @RequestParam(required = false) String byClient,
            @RequestParam(required = false) String byProductHL,
            @RequestParam(required = false) String byProductClient,
            @RequestParam(required = false) String byOrder,
            @RequestParam(required = false) String byOrderDateStart,
            @RequestParam(required = false) String byOrderDateEnd,
            @RequestParam(required = false) String byCooperationModel,
            @RequestParam(required = false) String byAssessment,
            @RequestParam(defaultValue = "0") long byStatus
    ) {


        String paramName = "";
        String paramValue = "";
        if (byKeyword != null && !"".equals(byKeyword)) {
            filterParameter.addKeyWord(byKeyword);
        } else if (byClient != null && !"".equals(byClient)) {
            paramName = "byClient";
            paramValue = byClient;
        } else if (byProductHL != null && !"".equals(byProductHL)) {
            paramName = "byProductHL";
            paramValue = byProductHL;
        } else if (byProductClient != null && !"".equals(byProductClient)) {
            paramName = "byProductClient";
            paramValue = byProductClient;
        } else if (byOrder != null && !"".equals(byOrder)) {
            paramName = "byOrder";
            paramValue = byOrder;
        } else if (byAssessment != null && !"".equals(byAssessment)) {
            paramName = "byAssessment";
            paramValue = byAssessment;
        } else if (byOrderDateStart != null && !"".equals(byOrderDateStart)) {
            paramName = "byOrderDateStart";
            paramValue = byOrderDateStart;
        } else if (byCooperationModel != null && !"".equals(byCooperationModel)) {
            paramName = "byCooperationModel";
            paramValue = byCooperationModel;
        } else if (byStatus != 0) {
            paramName = "byStatus";
            paramValue = String.valueOf(byStatus);
        }

        if ((byKeyword == null || "".equals(byKeyword)) && !"".equals(paramName) && !"".equals(paramValue)) {
            filterParameter.addFilterParameter(paramName, paramValue);
            paramName = "";
            paramValue = "";
        }

        if (byOrderDateEnd != null && !"".equals(byOrderDateEnd)) {
            paramName = "byOrderDateEnd";
            paramValue = byOrderDateEnd;
        }
        if ((byKeyword == null || "".equals(byKeyword)) && !"".equals(paramName) && !"".equals(paramValue)) {
            filterParameter.addFilterParameter(paramName, paramValue);
        }

        return "redirect:/assessmentList";
    }

    @GetMapping("assessmentList/removeFilter")
    public String removeFilterParam(@RequestParam() String filterName) {

        if ("".equals(filterName) || filterName == null) {
            return "redirect: /assessmentsList";
        } else if (!"all".equals(filterName) && filterParameter.getFiltersList().containsKey(filterName)) {
            filterParameter.removeFilterParameters(false, filterName);
        } else {
            // czy dla wartości obcych, złośliwych pozostawić czyszczenie wszystkiego?
            filterParameter.removeFilterParameters(true, null);
        }
        return "redirect:/assessmentList";
    }

    @GetMapping("/assessmentDetails")
    public String showAssessmentDetails(Model model, @RequestParam(required = true) Long id, HttpServletRequest request, Principal principal,
                                        @RequestParam(required = false) Long processId) {

        Optional<Assessment> oneAssessment = ar.findById(id);

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        model.addAttribute("multiTaskTool", assessmentViewTool);
        model.addAttribute("clientList", cr.findAll());
        model.addAttribute("requiredProdQualification", pqr.findAll());
        model.addAttribute("requiredProdStatus", psr.findAll());
        model.addAttribute("countryList", countR.findAll());
        model.addAttribute("businessModelList", ModelCooperation.values());
        model.addAttribute("departmentList", dr.findAll());
        model.addAttribute("currencyList", Currency.values());
        model.addAttribute("criticalityScale", CriticalityScale.values());
        model.addAttribute("availabilityStat", AvailabilityStatus.values());
        model.addAttribute("productList", prodR.findAll());
        model.addAttribute("unitsList", unitRepository.findAll());
        model.addAttribute("statusesForAssessment", sr.getByCategoryIs("assessment"));
        //TODO weryfikacja użytkownika zanim zostanie wrzucony do kontenerka
        model.addAttribute("logedUser", ur.findByUsername(principal.getName()).get());

        if (processId != null && processId != 0) {
            model.addAttribute("selectedProcessId", processId);
        } else if (procR.getMinLongOfProcessForAssessment(id) != 0) {
            model.addAttribute("selectedProcessId", procR.getMinLongOfProcessForAssessment(id));
        } else {
            return "forward:/assessmentError";
        }


        if (oneAssessment.get() != null && oneAssessment.isPresent()) {
            model.addAttribute("oneAssessment", oneAssessment.get());
        } else {
            model.addAttribute("errorMessage", "Błąd załadowania obiektu oceny, skontaktuj się z developerem");
            return "forward:/assessmentError";
        }

        return "assessmentDetails";
    }


    @PostMapping("/assessmentDetails/addDepartmentAssessment")
    public String addDepartmentAssessment(@RequestParam() Long assessmentDepartId, @RequestParam() String assessmentDescription,
                                          @RequestParam(required = false) Integer[] mhValue, @RequestParam(required = false) String[] mhSubject,
                                          @RequestParam(required = false) String[] costSubject, @RequestParam(required = false) Double[] costValue,
                                          @RequestParam(required = false) String[] costCurrency, @RequestParam() String criticalityLevel, Model model) {

        Optional<DepartmentAssessment> departAssessment = daR.findById(assessmentDepartId);

        if (!departAssessment.isPresent()) {
            return "forward:/assessmentError";
        }

        departAssessment.get().setDescriptonAndDateAndCriticalityAndManHours(assessmentDescription, dateGenerator.getCurrentDate(), CriticalityScale.valueOf(criticalityLevel));
        daR.save(departAssessment.get());

        List<AssessmentCostMH> assessmentCostList = departAssessment.get().getAssessmentCostMHs().stream().filter(assessmentCostMH -> 0 != assessmentCostMH.getCost()).collect(Collectors.toList());
        List<AssessmentCostMH> assessmentMHList = departAssessment.get().getAssessmentCostMHs().stream().filter(assessmentCostMH -> 0 != assessmentCostMH.getMh()).collect(Collectors.toList());

        if (assessmentCostList.size() > 0 && costValue != null && costCurrency != null && costSubject != null) {
            if (costValue.length == costSubject.length && costValue.length == costCurrency.length) {
                int maxIterator = assessmentCostList.size() >= costValue.length ? assessmentCostList.size() : costValue.length;
                int minIterator = assessmentCostList.size() >= costValue.length ? costValue.length : assessmentCostList.size();

                for (int i = 0; i < maxIterator; i++) {
                    if (i < minIterator) {
                        AssessmentCostMH assessmentCostMH = assessmentCostList.get(i);
                        assessmentCostMH.setCost(costValue[i]);
                        assessmentCostMH.setCurrency(Currency.valueOf(costCurrency[i]));
                        assessmentCostMH.setCostSubject(costSubject[i]);
                        assessCostMhR.save(assessmentCostMH);
                    } else if (assessmentCostList.size() > costValue.length) {
                        assessCostMhR.delete(assessmentCostList.get(i));
                    } else if (costValue.length > assessmentCostList.size()) {
                        assessCostMhR.save(new AssessmentCostMH(costValue[i], Currency.valueOf(costCurrency[i]), costSubject[i], departAssessment.get()));
                    }
                }
            }
        } else if (assessmentCostList.size() > 0 && costValue == null) {
            assessmentCostList.stream().forEach(assessmentCostMH -> assessCostMhR.delete(assessmentCostMH));
        } else if (assessmentCostList.size() == 0 && costValue != null && costCurrency != null && costSubject != null) {
            if (costValue.length > 0 && costValue.length == costSubject.length && costValue.length == costCurrency.length) {
                for (int i = 0; i < costValue.length; i++) {
                    assessCostMhR.save(new AssessmentCostMH(costValue[i], Currency.valueOf(costCurrency[i]), costSubject[i], departAssessment.get()));
                }
            } else {
                model.addAttribute("errorMessage", "An error of saving cost in database occured.");
                return "errorPage";
            }
        }


        if (assessmentMHList.size() > 0 && mhValue != null && mhSubject != null) {
            if (mhValue.length == mhSubject.length) {
                int maxIterator = assessmentMHList.size() >= mhValue.length ? assessmentMHList.size() : mhValue.length;
                int minIterator = assessmentMHList.size() >= mhValue.length ? mhValue.length : assessmentMHList.size();

                for (int i = 0; i < maxIterator; i++) {
                    if (i < minIterator) {
                        AssessmentCostMH assessmentCostMH = assessmentMHList.get(i);
                        assessmentCostMH.setMh(mhValue[i]);
                        assessmentCostMH.setMhSubject(mhSubject[i]);
                        assessCostMhR.save(assessmentCostMH);
                    } else if (assessmentMHList.size() > mhValue.length) {
                        assessCostMhR.delete(assessmentMHList.get(i));
                    } else if (mhValue.length > assessmentMHList.size()) {
                        assessCostMhR.save(new AssessmentCostMH(mhValue[i], mhSubject[i], departAssessment.get()));
                    }
                }
            }
        } else if (assessmentMHList.size() > 0 && mhValue == null) {
            assessmentMHList.stream().forEach(assessmentCostMH -> assessCostMhR.delete(assessmentCostMH));
        } else if (assessmentMHList.size() == 0 && mhValue != null && mhSubject != null) {
            if (mhValue.length > 0 && mhValue.length == mhSubject.length) {
                for (int i = 0; i < mhValue.length; i++) {
                    assessCostMhR.save(new AssessmentCostMH(mhValue[i], mhSubject[i], departAssessment.get()));
                }
            } else {
                model.addAttribute("errorMessage", "An error of saving men-hours in database occured.");
                return "errorPage";
            }
        }


        return "redirect:/assessmentDetails?id=" + departAssessment.get().getAssessment().getId();
    }


    @GetMapping("/assessmentError")
    public String showErrorPage(@RequestParam(defaultValue = "") String errorMessage, Model model) {

        return "errorPage";
    }

    @PostMapping("/assessmentDetails/addRequirement")
    public String addRequirement(@RequestParam() Long idAssessment, @RequestParam() String requirementDescription, Model model) {

        Optional<Assessment> choosedAssessment = ar.findById(idAssessment);
        if (!choosedAssessment.isPresent()) {
            model.addAttribute("errorMessage", "Brak ID Oceny. Jeśli błąd się powtórzy napisz do IT.");
            return "errorPage";
        }

        rrr.save(new Requirement(choosedAssessment.get(), requirementDescription));
        List<AssessDepartConfirmation> adcOptional = adcr.findByAssessment(choosedAssessment.get());
        for (AssessDepartConfirmation assessDepartConfirmation : adcOptional) {
            assessDepartConfirmation.setConfirmed(false);
            adcr.save(assessDepartConfirmation);
        }

        return "redirect:/assessmentDetails?id=" + idAssessment;
    }

    @GetMapping("/assessmentDetails/deleteRequirement")
    public String deleteRequirement(@RequestParam(defaultValue = "0") Long idRequirement, Model model) {

        if (idRequirement == 0) {
            model.addAttribute("errorMessage", "Brak ID Oceny. Jeśli błąd się powtórzy napisz do IT.");
            return "errorPage";
        }

        Optional<Requirement> regRequirement = rrr.findById(idRequirement);

        if (regRequirement.isPresent()) {
            rrr.delete(regRequirement.get());
        }

        //TODO reset dla wszystkich potwierdzeń ocen działów

        return "redirect:/assessmentDetails?id=" + regRequirement.get().getAssessment().getId();
    }

    @PostMapping("/assessmentDetails/addAssessmentSubject")
    public String addAssessmentSubject(@RequestParam() String assessmentSubject, @RequestParam(defaultValue = "0") long idDepartment,
                                       @RequestParam(defaultValue = "0") long idAssessment, Model model) {

        if (idAssessment == 0 || assessmentSubject == "" || idDepartment == 0) {
            model.addAttribute("errorMessage", "Brak ID i innych. Jeśli błąd się powtórzy napisz do IT.");
            return "errorPage";
        }

        Optional<Assessment> choosedAssessment = ar.findById(idAssessment);
        Optional<Department> department = dr.findById(idDepartment);

        if (choosedAssessment.isPresent() && department.isPresent()) {
            daR.save(new DepartmentAssessment(assessmentSubject, department.get(), choosedAssessment.get()));
        }

        //TODO reset dla potwierdzenia oceny danego działu działu jezeli nie ma to dodanie

        return "redirect:/assessmentDetails?id=" + idAssessment;
    }

    /**
     * Method for creating order of the assessment
     */
    @PostMapping("/assessmentList/addNewAssessment")
    public String addNewAssessment(@RequestParam(required = false) Long clientID, @RequestParam(required = false) String cooperationModel, @RequestParam(required = false) Long productID,
                                   @RequestParam() Long counrtyID, @RequestParam(required = false) String destinedProdName, @RequestParam() Long destinedProdStatusID,
                                   @RequestParam() String avStatus, @RequestParam(required = false) Long productQualificationID, @RequestParam() Long requiredProdQualificationID,
                                   @RequestParam(required = false) String[] dossierType, @RequestParam(required = false) String[] requirement,
                                   @RequestParam(required = false) String[] packageSize, @RequestParam(required = false) String[] packageUnit,
                                   @RequestParam(defaultValue = "0") Long choosedAssessmentID, @RequestParam(defaultValue = "0") Long processID,
                                   Principal principal, HttpServletRequest request, Model model) {

        if (!request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("errorMessage", "Nie wolno Ci.");
            return "errorPage";
        }

        Optional<Process> optionalProcess = null;
        Optional<String> maxCurrentProcessNo = procR.getMaxProcessNoForCurrentYear(dateGenerator.getFistDateOfCurrentYear(), dateGenerator.getCurrentDate());
        if (processID != null && processID > 0) {
            optionalProcess = procR.findById(processID);
        } else {
            ProcessFactory processFactory = new ProcessFactory(procR);
            optionalProcess = processFactory.createOptionalNewProcess((maxCurrentProcessNo.isPresent() && procR.getCountAllRows() != 0) ? maxCurrentProcessNo.get() : "00000",
                    prodR.findById(productID), destinedProdName, pqr.findById(productQualificationID), sr.findById((long) 1), cr.findById(clientID),
                    ur.findByUsername(principal.getName()), ModelCooperation.valueOf(cooperationModel), dateGenerator, procR);
        }

        Process selectedProcess = null;
        if (optionalProcess != null && optionalProcess.isPresent()) {
            selectedProcess = optionalProcess.get();
        } else {
            model.addAttribute("errorMessage", ".");
            return "forward:/assessmentError";
        }

        Optional<Assessment> assessmentOptional = null;
        AssessmentFactory assessmentFactory = null;
        Optional<String> maxCurrentIdAssessment = ar.getMaxAssessmentNoForCurrentYear(dateGenerator.getFistDateOfCurrentYear(), dateGenerator.getCurrentDate());
        if (choosedAssessmentID != null && choosedAssessmentID > 0) {
            assessmentOptional = ar.findById(choosedAssessmentID);
        } else {
            assessmentFactory = new AssessmentFactory(ar);
            assessmentOptional = assessmentFactory.createNewAssesssment((maxCurrentIdAssessment.isPresent() && procR.getCountAllRows() != 0) ? maxCurrentIdAssessment.get() : "00000", dateGenerator,
                    countR.findById(counrtyID), psr.findById(destinedProdStatusID), AvailabilityStatus.valueOf(avStatus),
                    pqr.findById(requiredProdQualificationID), ur.findByUsername(principal.getName()), sr.findById((long) 7),
                    dossierType, ar);
        }

        Assessment selectedAssessment = null;

        if (assessmentOptional != null && assessmentOptional.isPresent()) {
            selectedAssessment = assessmentOptional.get();
        } else {
            return "errorPage";
        }

        ObjectsBinder.createAssessmentProcessRelation(selectedProcess, selectedAssessment, procR, ar);
        if (null != assessmentFactory) {
            assessmentFactory.prepareElementsToAssessment(daR, selectedAssessment, apr);
        }


        if (requirement != null && requirement.length > 0 && (choosedAssessmentID == null || choosedAssessmentID == 0)) {
            for (String simpleRequirement : requirement) {
                rrr.save(new Requirement(selectedAssessment, simpleRequirement));
            }
        }

        if (packageSize != null && packageUnit != null && packageSize.length > 0 && packageUnit.length > 0 &&
                packageSize.length == packageUnit.length && (choosedAssessmentID == null || choosedAssessmentID == 0)) {
            for (int i = 0; i < packageSize.length; i++) {
                Optional<Unit> unitOptional = unitRepository.findByUnit(packageUnit[i]);
                try {
                    packageSizeR.save(new PackageSize(Double.valueOf(packageSize[i]), unitOptional.get(), selectedAssessment));
                } catch (NoSuchElementException e) {
                } catch (NumberFormatException f) {
                }
            }
        }

        Optional<ProductStatus> productStatusOptional = psr.findById(destinedProdStatusID);
        if (productStatusOptional.isPresent() && null != assessmentFactory) {
            assessmentFactory.prepareConfirmationDepartmentList(apr, adcr, productStatusOptional.get(), selectedAssessment);
        }

        return "redirect:/assessmentDetails?id=" + selectedAssessment.getId();
    }


}