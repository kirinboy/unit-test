package net.kirinboy.training.unittest;

import net.kirinboy.training.unittest.ai.Employee;
import net.kirinboy.training.unittest.ai.EmployeeDao;

import java.util.HashMap;
import java.util.Map;

public class FakeEmployeeDao extends EmployeeDao {
    private final Map<Long, Employee> employees = new HashMap<>();

    public FakeEmployeeDao() {
        employees.put(1L, new Employee(1L, "name"));
    }

    @Override
    public Employee getById(long employeeId) {
        return employees.get(employeeId);
    }
}
