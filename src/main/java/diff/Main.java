package diff;

import lombok.val;
import model.Department;
import model.Employee;

import static diff.DiffChain.diffChain;
import static diff.PojoDiff.pojoDiff;
import static diff.PojoDiffChain.pojoDiffChain;

public class Main {

  public static void main(String[] args) {

    System.out.println("\n======== FIELDS =========");
    fieldsDiff();

    System.out.println("\n======== OBJECTS =========");
    objectDiff();

    System.out.println("\n======== OBJECTS CHAIN =========");
    objectChainDiff();
  }

  static void fieldsDiff() {

    val e1 = new Employee().setRate(1).setName("Ivanov");
    val e2 = new Employee().setRate(2).setName("Petrov");

    diffChain(e1::getRate, e2::getRate, intChange("emp.rate"))
        .add(e1::getName, e2::getName, strChange("emp.name"))
        .eval();
  }


  static void objectDiff() {

    val e1 = new Employee().setRate(1).setName("Ivanov");
    val e2 = new Employee().setRate(2).setName("Petrov");

    pojoDiff(e1, e2)
        .add(Employee::getRate, intChange("emp.rate"))
        .add(Employee::getName, strChange("emp.name"))
        .eval();
  }

  static void objectChainDiff() {

    val e1 = new Employee().setRate(1).setName("Ivanov");
    val e2 = new Employee().setRate(2).setName("Petrov");

    val d1 = new Department().setDepartmentId("TST")
        .setName("Testing");
    val d2 = new Department().setDepartmentId("DEV")
        .setName("Development");

    pojoDiffChain(
        pojoDiff(e1, e2)
            .add(Employee::getRate, intChange("emp.rate"))
            .add(Employee::getName, strChange("emp.name"))
    ).and(
        pojoDiff(d1, d2)
            .add(Department::getDepartmentId, strChange("dep.code"))
            .add(Department::getName, strChange("dep.name"))
    ).eval();
  }

  private static DiffAction<Integer> intChange(final String fname) {
    return (l, r) -> System.out.println(
        fname + " " + l + " != " + r);
  }

  private static DiffAction<String> strChange(final String fname) {
    return (l, r) -> System.out.println(
        fname + " " + l + " != " + r);
  }
}
