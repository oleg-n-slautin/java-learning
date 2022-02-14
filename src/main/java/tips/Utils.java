/*
 *  Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package tips;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Utils
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public final class Utils {

    private Utils() {
    }

    /**
     * Wrap a Throwable into a RuntimeException.
     * Does nothing if it is already a RuntimeException.
     * @param toWrap original exception
     * @return wrapped exception
     */
    public static RuntimeException wrapRuntimeIfNeeded(final Throwable toWrap) {

        return toWrap instanceof RuntimeException
            ? (RuntimeException) toWrap
            : wrapRuntime(toWrap);
    }

    /**
     * Wrap a Throwable into a FallbackException.
     * @param toWrap original exception
     * @return wrapped exception
     */
    public static RuntimeException wrapRuntime(final Throwable toWrap) {
        return new RuntimeException(nvl(toWrap, i -> nvl(i.getClass(), Class::getSimpleName)), toWrap);
    }

    /**
     * Replace value if is null with default value
     *
     * @param val - value
     * @param fun - function to get not null value
     * @return default value if original value is null
     * @param <T> the type of instance to return.
     * @param <V> the type of origin value.
     */
    public static <V, T> T nvl(final V val, final Function<V, T> fun) {

        if (isNull(val)) {
            return null;
        }
        return fun.apply(val);
    }

    public static <T> T nvl(final T val, final Supplier<T> defValue) {

        if (isNull(val)) {
            return defValue.get();
        }
        return val;
    }

    /**
     * Do action within the consumer lifecircle (<code>onStart</>, <code>callback.exec()</>, <code>onComplete</>)
     * @param consumer - consumer
     */
    public static <T> Action0 withinChunk(final Consumer<Consumer<T>> action,
                                          final CompletableConsumer<T> consumer) {

        return () -> {
            consumer.onStart();
            action.accept(consumer);
            consumer.onComplete();
        };
    }

    public static <T> void forEach(final Collection<T> collection,
                                   final Consumer<T> consumer) {

        if (nonNull(collection) && collection.size() > 0) {
            collection.forEach(consumer);
        }
    }

    public static <K, V> V putIfAbsent(final Map<K, V> map, final K key,
                                       final Supplier<V> value) {

        V v = map.get(key);
        if (isNull(v)) {
            v = value.get();
            map.put(key, v);
        }
        return v;
    }

    public static <T> void ifNonNull(final T val, final Consumer<T> consumer) {
        if (nonNull(val)) {
            consumer.accept(val);
        }
    }
}
