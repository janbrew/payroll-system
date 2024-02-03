package net.jsf.payroll;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class PayrollSystem {
    private static ArrayList<ArrayList<Integer>> employees = new ArrayList<>();

    private static ArrayList<Integer> employee = null;
    private int daysWorked = 0;
    private double grossPay = 0.00, tax = 0.00, netPay = 0.00;
    
    public PayrollSystem() {
    }

    public static Optional<ArrayList<Integer>> accessEmployee(int employeeID) throws EmployeeNotFound {
        Optional<ArrayList<Integer>> employeeFound = employees.stream().filter(e -> e.get(0) == employeeID).findFirst();

        if (!employeeFound.isPresent()) {
            throw new EmployeeNotFound("Employee does not exist", null);
        }

        employee = employeeFound.get();

        return employeeFound;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    public void calculate() {
        calculateGrossSalary();
        calculateTax();
        calculateNetPay();
    }

    public void calculateGrossSalary() {
        this.grossPay = employee.get(1) * this.daysWorked;
    }

    public void calculateTax() {
        if (this.grossPay > 100000) {
            this.tax = this.grossPay * 0.20;
        }
        else {
            this.tax = this.grossPay * 0.10;
        }
    }

    public void calculateNetPay() {
        this.netPay = this.grossPay - this.tax;
    }

    public static void generateEmployees() {
        int[] employeeIDs = {123456789, 123456788, 123456777, 123456666, 123455555, 123444444, 123333333, 122222222, 111111111, 999999999};
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> employee = new ArrayList<>();

            employee.add(employeeIDs[i]);
            employee.add(random.nextInt(100, 1000));

            employees.add(employee);
        }
    }

    public int getEmployeeID() {
        return employee.get(0);
    }

    public int getemployeeDaysWorked() {
        return this.daysWorked;
    }

    public double getEmployeeDailyRate() {
        return employee.get(1);
    }
    
    public double getEmployeeGrossSalary() {
        return this.grossPay;
    }

    public double getEmployeeTax() {
        return this.tax;
    }

    public double getEmployeeNetPay() {
        return this.netPay;
    }

    public static String formatEmployeeID(int id) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < String.valueOf(id).length(); i++) {
            sb.append(String.valueOf(id).charAt(i));
            if (i == 2 || i == 5) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}   

