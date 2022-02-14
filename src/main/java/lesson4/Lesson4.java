package lesson4;

import dao.EmployeeDao;
import lombok.val;

import static lesson4.ThreadCtx.dumpEmployees;
import static lesson4.ThreadCtx.employees;
import static lesson4.ThreadCtx.releaseEmployees;
import static tips.Utils.forEach;

public class Lesson4 {

    public static void main(String[] args) {

        val dao = new EmployeeDao();
        val ctx = new LocalCtx();
        try {
            employees().addAll(dao.all());

            dumpEmployees();
            forEach(employees(), ctx.action());
            dumpEmployees();

        } finally {
            releaseEmployees();
        }
    }

}
