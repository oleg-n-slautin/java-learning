package lesson4;

import lesson3.AddRate;
import lesson3.WhenRateGtLimit;
import model.Employee;

import java.util.HashSet;
import java.util.function.Consumer;

public class LocalCtx {

    private final Consumer<Employee> action;
    private final HashSet<String> filtered = new HashSet<>();

    public LocalCtx() {
        this.action = new WhenRateGtLimit(2,
            e -> filtered.add(e.getName())
        ).andThen(
            new AddRate(
                1,
                e -> !filtered.contains(e.getName())
            )
        );
    }

    public Consumer<Employee> action() {
        return action;
    }

    public HashSet<String> filtered() {
        return filtered;
    }
}
