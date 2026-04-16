package com.quiz.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    SOLID/GRASP highlights:
    - LSP (Liskov Substitution): Student is substitutable for User in shared workflows.
    - OCP (Open/Closed Principle): Role-specific behavior is added through extension of User.
*/
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Result> results = new ArrayList<>();

    public Student() {
        super();
    }

    public Student(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getDisplayRole() {
        return "Student";
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
