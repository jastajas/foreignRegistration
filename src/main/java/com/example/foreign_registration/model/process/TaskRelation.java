package com.example.foreign_registration.model.process;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class TaskRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Task mainTask;

    @ManyToOne
    private Task relatedTask;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    public TaskRelation() {
    }

    public TaskRelation(Task mainTask, Task relatedTask, RelationType relationType) {
        this.mainTask = mainTask;
        this.relatedTask = relatedTask;
        this.relationType = relationType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getMainTask() {
        return mainTask;
    }

    public void setMainTask(Task mainTask) {
        this.mainTask = mainTask;
    }

    public Task getRelatedTask() {
        return relatedTask;
    }

    public void setRelatedTask(Task relatedTask) {
        this.relatedTask = relatedTask;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRelation that = (TaskRelation) o;
        return id == that.id &&
                Objects.equals(mainTask, that.mainTask) &&
                Objects.equals(relatedTask, that.relatedTask) &&
                relationType == that.relationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainTask, relatedTask, relationType);
    }

    @Override
    public String toString() {
        return "TaskRelation{" +
                "id=" + id +
                ", mainTask=" + mainTask +
                ", relatedTask=" + relatedTask +
                ", relationType=" + relationType +
                '}';
    }
}
