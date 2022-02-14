/*
 *  Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package chunk;

import lombok.val;
import model.DepartmentDao;
import model.EmployeeDao;
import tips.InMemoryCache;

import static tips.ChunkConsumer.chunkConsumer;
import static tips.Utils.forEach;
import static tips.Utils.withinChunk;

/**
 * Lesson2
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public class Main {

    public static void main(String[] args) {

        val eDao = new EmployeeDao();
        val dDao = new DepartmentDao();

        val cache = new InMemoryCache<>(dDao::findByCode);
        val sum = new SumRates(cache::get);

        val avg = new AvgRate();

        val action = withinChunk(
            consumer -> forEach(eDao.all(), sum.andThen(consumer)),
            chunkConsumer(100, avg)
        );

        action.exec();
    }

}
