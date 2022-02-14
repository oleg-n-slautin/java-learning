package lesson4;

import model.Employee;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.out;
import static tips.Utils.forEach;

public class ThreadCtx {

    private static final ThreadLocal<Collection<Employee>> employees = ThreadLocal.withInitial(ArrayList::new);

    public static Collection<Employee> employees() {
        return employees.get();
    }

    public static void releaseEmployees() {
        employees.get().clear();
        employees.remove();
    }

    public static void dumpEmployees() {

        out.println("\n--- Employees ---");

        forEach(employees.get(),
            e -> out.println(e.getName() + ": " + e.getRate()));
    }
}
