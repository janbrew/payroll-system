package net.jsf;

import net.jsf.payroll.*;
import net.jsf.visual.*;

public class Main {
    public static void main(String[] args) {
        PayrollSystem.generateEmployees();

        new Frame();
    }
}