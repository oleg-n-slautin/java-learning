package sequence;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static java.util.Arrays.stream;

public class Main {

    interface Sequencer {
        void sequence(String value,
                      BiConsumer<Integer, String> consumer);
    };

    public static void main(String[] args) {

        final String[] arr = {"one", "two", "three"};
        final Map<Integer, String> map = new LinkedHashMap<>();
        final Sequencer seq = sequencer();
        stream(arr)
            .forEach(s -> seq.sequence(s, map::put));

        System.out.println(map);
    }

    static Sequencer sequencer() {

        final AtomicInteger i = new AtomicInteger();
        return (value, consumer) -> consumer.accept(i.incrementAndGet(), value);
    }
}
