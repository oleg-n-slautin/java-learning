package lesson7;

class Service1 {

    void doA() {
        System.out.println("A");
    }

    void doB() {
        System.out.println("B");
    }
}

interface Action0 {
    void doIt();
}


class ActionFactory {

    Service1 service = new Service1();

    Action0 a() {
        return () -> service.doA();

    }

    Action0 b() {
        return () -> service.doB();
    }
}

class ClientX {

    Action0 action;
    void foo() {
        action.doIt();
    }
}

class ClientY {

    Action0 action;
    void foo() {
        action.doIt();
    }
}

public class Test1 {

    public static void main(String[] args) {

        ActionFactory factory = new ActionFactory();

        ClientY client = new ClientY();
        client.action = factory.b();
        client.foo();
    }
}
