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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * ChunkConsumer
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 *
 * @param <T> - data type
 */
public class ChunkConsumer<T> implements CompletableConsumer<T> {

    private final List<T> chunk = new ArrayList<>();
    private final int chunkSize;
    private final Consumer<List<T>> listConsumer;

    /**
     * Constructor
     * @param chunkSize chunk size
     * @param listConsumer list consumer
     */
    public ChunkConsumer(final int chunkSize,
                         final Consumer<List<T>> listConsumer) {

        this.chunkSize = chunkSize;
        this.listConsumer = listConsumer;
    }

    @Override
    public void accept(final T t) {

        if (chunk.size() >= chunkSize) {
            listConsumer.accept(chunk);
            chunk.clear();
        }
        chunk.add(t);
    }

    @Override
    public void onComplete() {

        if (chunk.size() > 0) {
            listConsumer.accept(chunk);
            chunk.clear();
        }
    }

    @Override
    public void onStart() {
        chunk.clear();
    }

    /**
     * cachedConsumer
     * @param chunkSize - chunkSize
     * @param listConsumer - listConsumer
     * @param <T> - type
     * @return chunkConsumer
     */
    public static <T> CompletableConsumer<T> chunkConsumer(final int chunkSize,
                                                           final Consumer<List<T>> listConsumer) {

        return new ChunkConsumer<>(chunkSize, listConsumer);
    }
}

