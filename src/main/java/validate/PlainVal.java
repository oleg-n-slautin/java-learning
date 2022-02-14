package validate;

import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

class Foo {
    final int i;
    final int j;

    public Foo(final int i, final int j) {
        this.i = i;
        this.j = j;
    }
}


interface Rule {
    boolean act();
}

interface Reaction {
    void react();
}

interface Validator {
    Validator addRule(Rule rule, Reaction reaction);
    void validate();
}

interface PojoValidator<T> {
    void validate(T t);
}

public class PlainVal {

    static final int MAX_INT = 100;
    static final int MIN_INT = 10;

    public static void main(String[] args) {
        final Foo f = new Foo(1, 1000);
        Validator validator = validator();
        validator
            .addRule(
                lt(f.i, f.j),
                () -> error(f.i + " < " + f.j)
            ).addRule(
                lt(f.i, MIN_INT),
                () -> error(f.i + " < " + MIN_INT)
            ).addRule(
                gt(f.i, MAX_INT),
                () -> error(f.i + " > " + MAX_INT)
            ).addRule(
                lt(f.j, MIN_INT),
                () -> error(f.j + " < " + MIN_INT)
            ).addRule(
                gt(f.j, MAX_INT),
                () -> error(f.j + " > " + MAX_INT)
            ).validate();
    }

    static Rule lt(final int i, final int limit) {

        return () -> i < limit;
    }

    static Rule gt(final int i, final int limit) {

        return () -> i > limit;
    }


    static Validator validator() {

        final List<Pair<Rule, Reaction>> list = new LinkedList<>();

        return new Validator() {
            @Override
            public Validator addRule(final Rule predicate, final Reaction reaction) {
                list.add(Pair.of(predicate, reaction));
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

    static void ok(String s) {

        System.out.println("ok " + s);
    }

    static void error(String s) {

        System.err.println("error " + s);
    }

}



//        if (f.i > MAX_INT) {
//        error("f.i GT max value " + f.i + " " + MAX_INT);
//    } else if (f.i < MIN_INT) {
//        error("f.i LT min value " + f.i + " " + MIN_INT);
//    } else {
//        ok("f.i " + f.i);
//    }
//
//        if (f.j > MAX_INT) {
//        error("f.j GT max value " + f.j + " " + MAX_INT);
//    } else if (f.j < MIN_INT) {
//        error("f.j LT min value " + f.j + " " + MIN_INT);
//    } else {
//        ok("f.j " + f.j);
//    }