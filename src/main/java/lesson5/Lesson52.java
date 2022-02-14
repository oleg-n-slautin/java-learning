package lesson5;

import static java.lang.System.out;

public class Lesson52 {

    private static final boolean COMMON_BOOL = false;

    public static void main(String[] args) {

        if (same1() && same2() && same3()) {
            out.println("no changes");
        } else {
            out.println("has changes");
        }
    }

    static boolean same1() {
        out.println("f1");
        return COMMON_BOOL;
    }

    static boolean same2() {
        out.println("f2");
        return COMMON_BOOL;
    }

    static boolean same3() {
        out.println("f3");
        return COMMON_BOOL;
    }
}
