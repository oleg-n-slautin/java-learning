package lesson5;

import static java.lang.System.out;

public class Lesson51 {

    private static final boolean COMMON_BOOL = false;

    public static void main(String[] args) {

        if (f1() || f2() || f3()) {
            out.println("any true");
        } else {
            out.println("false");
        }

        out.println("----");

        if (f1() && f2() && f3()) {
            out.println("all true");
        } else {
            out.println("false");
        }
    }

    static boolean f1() {
        out.println("f1");
        return COMMON_BOOL;
    }

    static boolean f2() {
        out.println("f2");
        return COMMON_BOOL;
    }

    static boolean f3() {
        out.println("f3");
        return COMMON_BOOL;
    }
}
