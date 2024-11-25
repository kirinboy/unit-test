package net.kirinboy.training.unitteset;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    @Test
    void should_return_employee_when_given_valid_employeeId() {
        // Given
        long employeeId = 1L;
        EmployeeDao employeeDao = mock(EmployeeDao.class);
        Employee employee = new Employee(employeeId, "John Doe");
        when(employeeDao.getById(employeeId)).thenReturn(employee);
        EmployeeService employeeService = new EmployeeService(employeeDao);

        // When
        Employee result = employeeService.getById(employeeId);

        // Then
        assertEquals(employee, result);
        verify(employeeDao, times(1)).getById(employeeId);
    }

    @Test
    void should_throw_illegalArgumentException_when_given_empty_employeeId() {
        // Given
        long employeeId = 0L;
        EmployeeDao employeeDao = mock(EmployeeDao.class);
        EmployeeService employeeService = new EmployeeService(employeeDao);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getById(employeeId);
        });
        assertEquals("empty employeeId", exception.getMessage());
        verify(employeeDao, never()).getById(employeeId);
    }

    @Test
    void should_throw_employeeNotFoundException_when_given_nonexistent_employeeId() {
        // Given
        long employeeId = 1L;
        EmployeeDao employeeDao = mock(EmployeeDao.class);
        when(employeeDao.getById(employeeId)).thenReturn(null);
        EmployeeService employeeService = new EmployeeService(employeeDao);

        // When & Then
        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getById(employeeId);
        });
        assertEquals("employee not found", exception.getMessage());
        verify(employeeDao, times(1)).getById(employeeId);
    }

    @Test
    void should_update_employee_when_given_valid_employeeDto() {
        // Given
        long employeeId = 1L;
        String newName = "Jane Doe";
        EmployeeDao employeeDao = mock(EmployeeDao.class);
        Employee employee = new Employee(employeeId, "John Doe");
        when(employeeDao.getById(employeeId)).thenReturn(employee);
        EmployeeService employeeService = new EmployeeService(employeeDao);
        EmployeeDto employeeDto = new EmployeeDto(employeeId, newName);

        // When
        employeeService.updateEmployee(employeeDto);

        // Then
        assertEquals(newName, employee.getName());
        verify(employeeDao, times(1)).getById(employeeId);
        verify(employeeDao, times(1)).update(employee);
    }

    @Test
    void should_throw_employeeNotFoundException_when_given_nonexistent_employeeDto() {
        // Given
        long employeeId = 1L;
        EmployeeDao employeeDao = mock(EmployeeDao.class);
        when(employeeDao.getById(employeeId)).thenReturn(null);
        EmployeeService employeeService = new EmployeeService(employeeDao);
        EmployeeDto employeeDto = new EmployeeDto(employeeId, "Jane Doe");

        // When & Then
        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(employeeDto);
        });
        assertEquals("employee not found", exception.getMessage());
        verify(employeeDao, times(1)).getById(employeeId);
        verify(employeeDao, never()).update(any());
    }
}