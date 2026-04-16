package com.quiz.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Backward-compatibility entity for older rows where discriminator/role was saved as "User".
 * Treats such users as students for display and login mapping.
 * SOLID highlight: LSP (Liskov Substitution) - LegacyUser remains substitutable for User.
 */
@Entity
@DiscriminatorValue("User")
public class LegacyUser extends User {

    public LegacyUser() {
        super();
    }

    public LegacyUser(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String getDisplayRole() {
        return "Student";
    }
}
