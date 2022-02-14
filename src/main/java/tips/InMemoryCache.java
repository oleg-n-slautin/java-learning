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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.isNull;

/**
 * InMemoryCache
 *
 *  @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public class InMemoryCache<K, V> implements LocalCache<K, V> {

    private final Function<K, V> loader;
    private final Map<K, V> cache = new HashMap<>();

    /**
     * constructor
     * @param loader - loader
     */
    public InMemoryCache(final Function<K, V> loader) {

        this.loader = loader;
    }

    @Override
    public void close() {

        cache.clear();
    }

    @Override
    public V get(final K key) {

        V v = cache.get(key);
        if (isNull(v)) {
            v = loader.apply(key);
            cache.put(key, v);
        }
        return v;
    }
}
