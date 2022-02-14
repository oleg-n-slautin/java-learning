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

/**
 * Local cache
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 *
 * @param <K> - key type
 * @param <V> - value type
 */
public interface LocalCache<K, V> extends AutoCloseable {

    /**
     * Get value from cache
     * @param key - key
     * @return value
     */
    V get(K key);
}