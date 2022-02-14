package lesson3;

import dao.EmployeeDao;
import lombok.val;

import java.util.HashSet;

import static java.lang.System.out;
import static tips.Utils.forEach;

public class Lesson3 {

    public static void main(String[] args) {

        val eDao = new EmployeeDao();
        val filtered = new HashSet<String>();

        dumpEmployees(eDao);

        val consumer = new WhenRateGtLimit(2,
            e -> filtered.add(e.getName())
        ).andThen(
            new AddRate(
                1,
                e -> !filtered.contains(e.getName())
            )
        );
        forEach(eDao.all(), consumer);

        dumpEmployees(eDao);
    }

    private static void dumpEmployees(final EmployeeDao dao) {

        out.println("\n--- Employees ---");

        forEach(dao.all(),
            e -> out.println(e.getName() + ": " + e.getRate()));
    }
}
