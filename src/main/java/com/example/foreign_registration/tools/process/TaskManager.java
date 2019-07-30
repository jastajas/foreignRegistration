package com.example.foreign_registration.tools.process;

import com.example.foreign_registration.model.process.Task;
import com.example.foreign_registration.model.process.TaskProgressRegister;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.process.TaskProgressRegisterRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class TaskManager {

    private TaskProgressRegisterRepository tprr;

    @Autowired
    public TaskManager(TaskProgressRegisterRepository tprr) {
        this.tprr = tprr;
    }

    public void registerTaskProgress(Process process, List<Task> taskList) {
        DateGenerator dateGenerator = new DateGenerator();
        Date today = dateGenerator.getCurrentDate();

        Optional<TaskProgressRegister> tprOptional = tprr.getByProcessAndRegisterDate(process, today);
        int sumMhToDo = taskList.stream().filter(task -> 19 != task.getStatus().getId()).mapToInt(task -> task.getManEffort()).sum();
        TaskProgressRegister taskProgressRegister = null;

        if (tprOptional.isPresent()) {
            taskProgressRegister = tprOptional.get();
            taskProgressRegister.setSumMhToDo(sumMhToDo);
        } else {
            taskProgressRegister = new TaskProgressRegister(sumMhToDo, today, process);
        }
        tprr.save(taskProgressRegister);
    }

}
