package func;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Main {

    @Setter
    @Accessors(chain = true)
    @Getter
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
    static class FooDto {
        String id;
        String val;

        public FooDto(final String id, final String val) {
            this.id = id;
            this.val = val;
        }
    }

    public static final Consumer<FooDto> FUN = d -> someMethod(d);
    public static final Function<FooTuple, FooDto> MAP = t -> new FooDto(t.id, t.val);

    public static void main(String[] args) {

        List<FooTuple> src = readData();

        List<FooDto> dst = src.stream()
            .map(MAP)
            .collect(toList());

        dst.forEach(FUN);
    }

    private static void someMethod(final FooDto d) {

        System.out.println(">>" + d);
    }

    private static List<FooTuple> readData() {

        final List<FooTuple> list = new ArrayList<>();
        list.add(new FooTuple("1", "one"));
        list.add(new FooTuple("2", "two"));

        return list;
    }
}
