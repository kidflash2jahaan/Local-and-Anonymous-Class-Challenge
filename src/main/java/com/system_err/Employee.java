package com.system_err;

import java.time.LocalDate;

public record Employee(String firstName, String lastName, LocalDate hireDate) {
    public Employee(String firstName, String lastName, String hireDate) {
        this(firstName, lastName, LocalDate.parse(hireDate));
    }
}
