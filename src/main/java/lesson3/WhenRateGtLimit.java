package lesson3;

import model.Employee;

import java.util.function.Consumer;

public class WhenRateGtLimit implements Consumer<Employee> {

    private final int limit;
    private final Consumer<Employee> whenLimit;

    public WhenRateGtLimit(final int limit,
                           final Consumer<Employee> whenLimit) {

        this.limit = limit;
        this.whenLimit = whenLimit;
    }

    @Override
    public void accept(final Employee e) {

        if (e.getRate() >= limit) {
            whenLimit.accept(e);
        }
    }
}
