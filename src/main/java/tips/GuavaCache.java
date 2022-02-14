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

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static tips.Utils.wrapRuntimeIfNeeded;

/**
 * GuavaCache
 *
 *  @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public class GuavaCache<K, V> implements LocalCache<K, V> {

    private final LoadingCache<K, V> cache;

    /**
     * constructor
     * @param maxSize maxSize
     * @param expireAfter expireAfter
     * @param loader loader
     */
    public GuavaCache(final int maxSize,
                      final Duration expireAfter,
                      final Function<K, V> loader) {

        cache = CacheBuilder.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expireAfter.toMillis(), TimeUnit.MILLISECONDS)
            .build(
                new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) {
                        return loader.apply(key);
                    }
                }
            );
    }

    @Override
    public void close() {

        cache.cleanUp();
    }

    @Override
    public V get(final K key) {

        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            throw wrapRuntimeIfNeeded(e);
        }
    }
}
