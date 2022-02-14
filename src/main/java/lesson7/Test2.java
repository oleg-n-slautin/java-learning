package lesson7;

import java.util.function.Consumer;

class Service2 {

    void doA(int i, int j) {
        System.out.println("" + i + " " + j);
    }
}

interface ActionI {
    Consumer<Integer> doIt(int i);
}

interface ActionII {
    void doIt(int i, int j, int k);
}


class ActionFactory2 {

    Service2 service;
    ActionI actionI(int j) {
        return i -> z -> service.doA(j + i, z * j);
    }

    ActionII actionII() {
        return (i, j, k) -> service.doA(i, j + k);
    }
}

class ClientZ {

    ActionI action;
    int cnt;

    void foo(int length) {
        action.doIt(cnt).accept(length);
    }
}

class ClientZ2 {

    ActionI action;
    int cnt;

    void foo() {
        action.doIt(cnt);
    }
}


public class Test2 {

    public static void main(String[] args) {

        ActionFactory2 f = new ActionFactory2();
        f.service = new Service2();

        ClientZ z = new ClientZ();
        z.action = f.actionI(3);

        fun1(z);

    }

    private static void fun1(final ClientZ z) {
        for (int i = 0; i < 10; i++) {
            z.foo(i);
        }
    }
}
