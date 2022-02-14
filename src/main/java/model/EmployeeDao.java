/*
 *  Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * EmployeeDao
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public class EmployeeDao {

    private static final Map<String, Employee> data = new HashMap<>();

    static {
        data.put("DEV01",
            new Employee()
                .setDepartmentId("DEV")
                .setName("Ivanov Ivan")
                .setEmployeeId("DEV01")
                .setRate(1)
        );
        data.put("TEST",
            new Employee()
                .setDepartmentId("TEST")
                .setName("Petrov Petr")
                .setEmployeeId("TEST01")
                .setRate(2)
        );
    }

    /**
     * findByCode
     * @param code code
     * @return employee
     */
    public Employee findByCode(final String code) {
        return data.get(code);
    }

    /**
     * insert
     * @param code code
     * @param employee employee
     */
    public void insert(final String code, final Employee employee) {
        data.put(code, employee);
    }


    /**
     * all
     * @return employees
     */
    public Collection<Employee> all() {
        return data.values();
    }
}
