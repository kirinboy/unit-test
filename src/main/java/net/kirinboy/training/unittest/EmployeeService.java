package net.kirinboy.training.unittest;

public class EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Employee getById(long employeeId) {
        if (employeeId == 0) {
            throw new IllegalArgumentException("empty employeeId");
        }
        Employee employee = employeeDao.getById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("employee not found");
        }
        return employee;
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeDao.getById(employeeDto.employeeId());
        if (employee == null) {
            throw new EmployeeNotFoundException("employee not found");
        }
        employee.setName(employeeDto.name());
        employeeDao.update(employee);
    }
}
