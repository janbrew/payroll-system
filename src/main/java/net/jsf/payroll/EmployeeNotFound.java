package net.jsf.payroll;

public class EmployeeNotFound extends Exception {
    EmployeeNotFound(String errorMsg, Throwable error) {
        super(errorMsg, error);
    }
}
