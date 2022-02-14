package consume;

import model.Department;
import model.Employee;

import java.util.List;

public interface DummyCtx {

    Department department(Employee emp);
    void overLimit(Employee emp);
    List<Employee> employees();
}
