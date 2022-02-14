package func;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class Main2 {

    @Setter
    @Accessors(chain = true)
    @Getter
    @ToString
    static class FooTuple {
        String id;
        String val;

        public FooTuple(final String id, final String val) {
            this.id = id;
            this.val = val;
        }
    }

    @Setter
    @Accessors(chain = true)
    @Getter
    @ToString
    static class FooDto {
        String id;
        String val;

        public FooDto(final String id, final String val) {
            this.id = id;
            this.val = val;
        }
    }

    public static void main(String[] args) {

        List<Main2.FooDto> list = new ArrayList<>();
        Map<String, FooDto> map = new HashMap<>();

        readData(
            t -> mapData(
                t,
                (
                    (Consumer<FooDto>) list::add
                ).andThen(
                    Main2::someMethod
                ).andThen(
                    f -> map.put(f.id, f)
                )
            )
        );

        System.out.println(list);
        System.out.println(map);

    }

    private static void someMethod(final Main2.FooDto d) {

        System.out.println(">>" + d);
    }

    private static Optional<Main2.FooTuple> someFoo() {

        return Optional.of(
            new Main2.FooTuple("1", "one")
        );
    }


    private static void mapData(Main2.FooTuple tuple,
                                Consumer<Main2.FooDto> consumer) {

        ofNullable(tuple)
            .map(t -> new FooDto(t.id, t.val))
            .ifPresent(consumer);

        if (nonNull(tuple)) {
            consumer.accept(new FooDto(tuple.id, tuple.val));
        }
    }

    private static void readData(Consumer<Main2.FooTuple> consumer) {

        consumer.accept(new Main2.FooTuple("1", "one"));
        consumer.accept(new Main2.FooTuple("2", "two"));

    }

    public static <T> T nvl(final T val, final T defValue) {

        if (val != null) {
            return defValue;
        }
        return val;
    }
}
