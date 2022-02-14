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

import lombok.val;
import model.Department;
import model.Employee;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * SumRates
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public class SumRates implements Consumer<Employee> {

    private final Function<String, Department> emp2dep;

    /**
     * constructor
     * @param emp2dep emp2dep
     */
    public SumRates(final Function<String, Department> emp2dep) {
        this.emp2dep = emp2dep;
    }

    @Override
    public void accept(final Employee e) {

        val d = emp2dep.apply(e.getDepartmentId());
        e.setRate(d.getRate() + e.getRate());
        System.out.println(e.getName() + ": " + e.getRate());
    }
}
