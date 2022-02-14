package test;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;

import static java.lang.System.out;
import static rx.Observable.empty;
import static rx.Observable.just;
import static rx.Observable.range;
import static rx.Observable.switchOnNext;

public class RxDemoTest {

    @Test
    public void testZipWith() {

        range(1, 30)
            .zipWith(just("A", "B", "C", "D"), Pair::of)
            .subscribe(out::println);
    }

    @Test
    public void testMerge() {

        just("C", "D")
            .mergeWith(just("A", "B"))
            .subscribe(out::println);
    }

    @Test
    public void testMergeNulls() {

        just("C", "D")
            .mergeWith(just(null, "B"))
            .subscribe(out::println);
    }

    @Test
    public void testConcat() {

        just("C", "D")
            .concatWith(just("A", "B"))
            .subscribe(out::println);
    }

    @Test
    public void testCompose() {

        just("C", "D")
            .compose(s -> s.zipWith(just("X", "Y"), Pair::of))
            .subscribe(out::println);
    }

    @Test
    public void testSwitchOnNext() {
        switchOnNext(just(just("X", "Y"), just("C", "D")))
            .subscribe(out::println);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testLift() {
        just(1, 2)
            .lift((Observable.Operator<Integer, Integer>) subscriber ->
                {
                    final Subscriber<Integer> s = (Subscriber<Integer>) subscriber;
                    return new Subscriber<Integer>(subscriber) {
                        @Override
                        public void onCompleted() {
                            if (s.isUnsubscribed()) return;
                            s.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (s.isUnsubscribed()) return;
                            s.onError(e);
                        }

                        @Override
                        public void onNext(Integer integer) {
                            if (s.isUnsubscribed()) return;
                            s.onNext(integer);
                            s.onNext(integer);
                        }
                    };
                }
            )
            .subscribe(out::println);
    }

    @Test
    public void testLift2() {
        Observable.<Integer>empty()
            .lift(
                (Observable.Operator<Integer, Integer>) subscriber -> {
                    subscriber.onStart();
                    subscriber.onNext(1);
                    subscriber.onNext(2);
                    subscriber.onNext(3);
                    subscriber.onCompleted();
                    return subscriber;
                }
            )
            .subscribe(out::println);
    }

    @Test
    public void testToMultimap() {
        just(
            Pair.of("A", 1),
            Pair.of("B", 1),
            Pair.of("A", 2)
        ).toMultimap(Pair::getValue)
            .subscribe(out::println);
    }

    @Test
    public void testToMap() {

        just(
            Pair.of("A", 1),
            Pair.of("B", 1),
            Pair.of("A", 2)
        ).toMap(Pair::getKey)
            .subscribe(out::println);
    }

    @Test
    public void testToMap2() {

        just(
            Pair.of("A", 1),
            Pair.of("B", 1),
            Pair.of("A", 2)
        ).toMap(Pair::getKey, Pair::getValue)
            .subscribe(out::println);
    }

    @Test
    public void testReduce() {
        Object str = empty().concatWith(just("A", "B"))
            .reduce((s1, s2) -> s1 + "+" + s2)
            .toBlocking().first();
        out.println(str);
    }

    @Test
    public void testReduce2() {

       just("A", "B", "C")
            .reduce((s1, s2) -> s1 + "+" + s2)
            .subscribe(out::println);
    }
}