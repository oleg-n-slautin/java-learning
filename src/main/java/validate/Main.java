package validate;

import lombok.val;
import model.Employee;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static tips.Utils.putIfAbsent;




public class Main {

    public interface Rule {
        boolean act();
    }

    interface Reaction {
        void react();
    }

    interface Validator {
        Validator rule(Rule rule, Reaction reaction);
        void validate();
    }

    static final int MAX_RATE = 100;
    static final int MIN_RATE = 10;

    public static void main(String[] args) {
        val e1 = new Employee().setRate(9).setName("Ivanov");
        val e2 = new Employee().setRate(101).setName("Petrov");

        val errors = new HashMap<String, List<String>>();
        val validator = validator();

        validator
            .rule(
                lt(e1.getRate(), e2.getRate()),
                () -> error(errors, "lt", ltMsg(e1.getRate(), e2.getRate()))
            ).rule(
                gt(e2.getRate(), e1.getRate()),
                () -> error(errors, "gt", ltMsg(e1.getRate(), e2.getRate()))
            ).rule(
                min(e1.getRate()),
                () -> error(errors, "min", ltMsg(e1.getRate(), MIN_RATE))
            ).rule(
                min(e2.getRate()),
                () -> error(errors, "min", ltMsg(e2.getRate(), MIN_RATE))
            ).rule(
                max(e1.getRate()),
                () -> error(errors, "max", ltMsg(MAX_RATE, e1.getRate()))
            ).rule(
                max(e2.getRate()),
                () -> error(errors, "max", ltMsg(MAX_RATE, e2.getRate()))
            ).validate();

        System.out.println("Errors: " + errors);
    }

    private static String ltMsg(final int i,
                                final int j) {

        return format("%d < %d", i, j);
    }

    static Rule lt(final int i, final int limit) {

        return () -> i < limit;
    }

    static Rule gt(final int i, final int limit) {

        return () -> i > limit;
    }

    static Rule max(final int i) {

        return () -> i > MAX_RATE;
    }

    static Rule min(final int i) {

        return () -> i < MIN_RATE;
    }


    static Validator validator() {

        final List<Pair<Rule, Reaction>> list = new LinkedList<>();

        return new Validator() {

            @Override
            public Validator rule(final Rule rule, final Reaction reaction) {
                list.add(Pair.of(rule, reaction));
                return this;
            }

            @Override
            public void validate() {
                list.forEach(
                    p -> {
                        if (p.getLeft().act()) {
                            p.getRight().react();
                        }
                    }
                );
            }
        };
    }

    static void error(final Map<String, List<String>> errors,
                      final String field,
                      final String msg) {

        putIfAbsent(errors, field, ArrayList::new).add(msg);
    }
}
