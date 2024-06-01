package com.system_err;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            int year = random.nextInt(LocalDate.now().getYear() - 2000 + 1) + 2000; // Random year between 2000 and current year
            int month = random.nextInt(12) + 1; // Random month between 1 and 12
            int dayOfMonth = random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth()) + 1; // Random day of the month between 1 and the length of the month
            String hireDate = String.format("%04d-%02d-%02d", year, month, dayOfMonth);

            employees.add(new Employee("John" + i, "Doe" + i, hireDate));
        }

        printEmployees(employees);
    }

    public static void printEmployees(List<Employee> employeesToAdd) {
        class EmployeeAdditionalData {
            final String fullName;
            final int yearsWorked;

            public EmployeeAdditionalData(Employee employee) {
                this.fullName = employee.firstName() + " " + employee.lastName();
                this.yearsWorked = LocalDate.now().isBefore(employee.hireDate()) ? 0 : LocalDate.now().getMonthValue() < employee.hireDate().getMonthValue() ? LocalDate.now().getYear() - employee.hireDate().getYear() - 1 : LocalDate.now().getYear() - employee.hireDate().getYear();
            }

            public static List<EmployeeAdditionalData> makeList(List<Employee> employeesToAdd) {
                List<EmployeeAdditionalData> employees = new ArrayList<>(employeesToAdd.size());

                for (Employee employee : employeesToAdd) {
                    employees.add(new EmployeeAdditionalData(employee));
                }

                return employees;
            }
        }

        List<EmployeeAdditionalData> employees = EmployeeAdditionalData.makeList(employeesToAdd);
        employees.sort(Comparator.comparingInt(o -> o.yearsWorked));

        System.out.println("Employees:");
        
        int maxNameLength = 0;
        for (EmployeeAdditionalData employeeAdditionalData : employees) {
            if (employeeAdditionalData.fullName.length() > maxNameLength) {
                maxNameLength = employeeAdditionalData.fullName.length();
            }
        }

        for (EmployeeAdditionalData employee : employees) {
            System.out.printf("%-" + maxNameLength + "s worked for %2d years.\n", employee.fullName, employee.yearsWorked);
        }
    }
}