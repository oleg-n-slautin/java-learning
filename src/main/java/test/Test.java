package test;

import java.util.function.Predicate;

interface Foo {

    void doIt(String name);

    int age();

    default Foo andThen(final Foo foo) {
        return new Foo() {
            @Override
            public void doIt(final String name) {
                Foo.this.doIt(name);
                foo.doIt(name);
            }

            @Override
            public int age() {
                return foo.age();
            }
        };
    }

    default Foo ifThen(final Predicate<Foo> predicate, final Foo foo) {
        return new Foo() {
            @Override
            public void doIt(final String name) {
                Foo.this.doIt(name);
                if (predicate.test(Foo.this)) {
                    foo.doIt(name);
                }
            }

            @Override
            public int age() {
                return foo.age();
            }
        };
    }
}

public class Test {

    private final int gender;

    public Test(final int gender) {
        this.gender = gender;
    }

    Foo byAge(final int age) {

        return new Foo() {
            public void doIt(final String name) {
                System.out.println("" + gender + " " + age + " " + name);
            }

            @Override
            public int age() {
                return age;
            }
        };
    }

    Foo updateSalary(final Foo nested, final double salary) {

        return new Foo() {
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
        final Test tm = new Test(0);
        final Predicate<Foo> ageLimit = whenAgeLt(32);

        for (int i = 30; i < 35; i++) {
            final Foo foo = tm.byAge(i);
            final Foo chain = foo.ifThen(
                ageLimit,
                tm.updateSalary(foo, 100.)
            ).ifThen(
                ageLimit.negate(),
                tm.updateSalary(foo, -12.5)
            );

            for (int j = 0; j < 3; j++) {
                chain.doIt("Ivan " + i + "-" + j);
            }
        }
    }

    private static Predicate<Foo> whenAgeLt(final int age) {

        return f -> f.age() < age;
    }
}
