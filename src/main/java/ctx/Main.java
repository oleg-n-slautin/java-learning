package ctx;

import lombok.val;
import model.EmployeeDao;

import static ctx.ThreadCtx.dumpEmployees;
import static ctx.ThreadCtx.employees;
import static ctx.ThreadCtx.releaseEmployees;
import static tips.Utils.forEach;

public class Main {

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
