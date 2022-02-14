package consume;

import model.Employee;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AddRate implements Consumer<Employee> {

    private final int toAdd;
    private final Predicate<Employee> whenAdd;

    public AddRate(final int toAdd,
                   final Predicate<Employee> whenAdd) {

        this.toAdd = toAdd;
        this.whenAdd = whenAdd;
    }

    @Override
    public void accept(final Employee e) {

        if (whenAdd.test(e)) {
            e.setRate(e.getRate() + toAdd);
        }
    }
}
