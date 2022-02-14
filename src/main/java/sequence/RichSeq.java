package sequence;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static java.util.Arrays.stream;

public class RichSeq {

    interface Sequencer {
        void sequence(String value,
                      BiConsumer<Integer, String> consumer);
    };

    public static void main(String[] args) {

        final String[] arr = {"one", "two", "three"};
        final Sequencer seq = sequencer();
        stream(arr)
            .forEach(s -> seq.sequence(s, RichSeq::sequenced));
    }

    static void sequenced(final int i, final String s) {

        System.out.println(i + ": " + s);
    }

    static Sequencer sequencer() {

        final AtomicInteger i = new AtomicInteger();
        return (value, consumer) -> consumer.accept(i.incrementAndGet(), value);
    }
}
