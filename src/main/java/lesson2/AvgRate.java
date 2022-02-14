/*
 *  Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package lesson2;

import model.Employee;

import java.util.List;
import java.util.function.Consumer;

/**
 * AvgRate
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public class AvgRate implements Consumer<List<Employee>> {

    @Override
    public void accept(final List<Employee> employees) {

        employees.stream()
            .mapToInt(Employee::getRate)
            .average()
            .ifPresent(a -> System.out.println("average: " + a));
    }
}
