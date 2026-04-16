package com.quiz.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    SOLID/GRASP highlights:
    - LSP (Liskov Substitution): Admin is a valid specialization of User and can be used anywhere User is expected.
    - OCP (Open/Closed Principle): Adds role-specific behavior (getDisplayRole) via extension, not modification of User.
*/
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @OneToMany(
        mappedBy = "createdBy",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Quiz> createdQuizzes = new ArrayList<>();

    public Admin() {
        super();
    }

    public Admin(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getDisplayRole() {
        return "Administrator";
    }

    public List<Quiz> getCreatedQuizzes() {
        return createdQuizzes;
    }

    public void setCreatedQuizzes(List<Quiz> createdQuizzes) {
        this.createdQuizzes = createdQuizzes;
    }
}
