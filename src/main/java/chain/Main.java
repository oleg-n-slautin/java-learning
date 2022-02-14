package chain;

import java.util.function.Predicate;

interface SomeAction {

    void doIt(String name);

    int age();

    default SomeAction andThen(final SomeAction someAction) {
        return new SomeAction() {
            @Override
            public void doIt(final String name) {
                SomeAction.this.doIt(name);
                someAction.doIt(name);
            }

            @Override
            public int age() {
                return someAction.age();
            }
        };
    }

    default SomeAction ifThen(final Predicate<SomeAction> predicate, final SomeAction someAction) {
        return new SomeAction() {
            @Override
            public void doIt(final String name) {
                SomeAction.this.doIt(name);
                if (predicate.test(SomeAction.this)) {
                    someAction.doIt(name);
                }
            }

            @Override
            public int age() {
                return someAction.age();
            }
        };
    }
}

public class Main {

    private final int gender;

    public Main(final int gender) {
        this.gender = gender;
    }

    SomeAction byAge(final int age) {

        return new SomeAction() {
            public void doIt(final String name) {
                System.out.println("" + gender + " " + age + " " + name);
            }

            @Override
            public int age() {
                return age;
            }
        };
    }

    SomeAction updateSalary(final SomeAction nested, final double salary) {

        return new SomeAction() {
            public void doIt(final String name) {
                System.out.println("bingo!!! " + name + " salary "
                    + (salary > 0 ? "increased" : "decreased")
                    + " by " + salary);
            }

            @Override
            public int age() {
                return nested.age();
            }
        };
    }

    public static void main(String[] args) {

        //first all males
        final Main tm = new Main(0);
        final Predicate<SomeAction> ageLimit = whenAgeLt(32);

        for (int i = 30; i < 35; i++) {
            final SomeAction someAction = tm.byAge(i);
            final SomeAction chain = someAction
                .ifThen(
                    ageLimit,
                    tm.updateSalary(someAction, 100.)
                ).ifThen(
                    ageLimit.negate(),
                    tm.updateSalary(someAction, -12.5)
                );

            for (int j = 0; j < 3; j++) {
                chain.doIt("Ivan " + i + "-" + j);
            }
        }
    }

    private static Predicate<SomeAction> whenAgeLt(final int age) {

        return f -> f.age() < age;
    }
}
