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

import java.util.HashMap;
import java.util.Map;

/**
 * DepartmentDao
 *
 * @author <a href="mailto:oleg.n.slautin@gmail.com>Oleg N.Slautin</a>
 */
public class DepartmentDao {

    private static final Map<String, Department> data = new HashMap<>();

    static {
        data.put("DEV",
            new Department()
                .setDepartmentId("DEV")
                .setName("Development")
                .setRate(3)
        );
        data.put("TEST",
            new Department()
                .setDepartmentId("TEST")
                .setName("Testing")
                .setRate(4)
        );
    }

    /**
     * findByCode
     * @param code code
     * @return department
     */
    public Department findByCode(final String code) {
        return data.get(code);
    }

    /**
     * insert
     * @param code code
     * @param department department
     */
    public void insert(final String code, final Department department) {
        data.put(code, department);
    }
}
