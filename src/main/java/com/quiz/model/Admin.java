package com.quiz.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    Admin class extending User
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
