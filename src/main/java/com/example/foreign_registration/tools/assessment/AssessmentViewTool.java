package com.example.foreign_registration.tools.assessment;

import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import com.example.foreign_registration.repository.assessment.DepartmentAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AssessmentViewTool {


    private DepartmentAssessmentRepository daRepository;

    @Autowired
    public AssessmentViewTool(DepartmentAssessmentRepository daRepository) {
        this.daRepository = daRepository;
    }

    public boolean isThisTailForSelectedUser(Long idDepartmentAssessment, Long idLogedUserDepartment) {

        List<DepartmentAssessment> departmentAssessmentList = daRepository.checkIfExistDepartmentAssessment(idDepartmentAssessment, idLogedUserDepartment);

        if (departmentAssessmentList == null || departmentAssessmentList.isEmpty() || departmentAssessmentList.size() == 0) {
            return false;
        }

        return true;
    }

    public boolean isEditableByThisDepartment(Long idDepartmentAssessment, Long loggedUserIdDepartment) {

        Optional<DepartmentAssessment> departmentAssessmentOptional = daRepository.findById(idDepartmentAssessment);
        if (!departmentAssessmentOptional.isPresent()) {
            return false;
        } else if (departmentAssessmentOptional.get().getAssessmentPattern() == null) {
            return departmentAssessmentOptional.get().getOther_subject_department().getId() == loggedUserIdDepartment;
        }

        return departmentAssessmentOptional.get().getAssessmentPattern().getDepartment().getId() == loggedUserIdDepartment;
    }

}
