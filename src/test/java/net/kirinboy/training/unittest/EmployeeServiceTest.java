package net.kirinboy.training.unittest;

import net.kirinboy.training.unitteset.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Test
    public void should_return_employee_when_get_by_id() {
        // given
        EmployeeDao stub = mock(EmployeeDao.class);
        when(stub.getById(1L)).thenReturn(new Employee(1L, "name"));
        EmployeeService employeeService = new EmployeeService(stub);

        // when
        Employee employee = employeeService.getById(1L);

        // then
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");
    }

    @Test
    public void should_throw_exception_when_employee_id_is_empty() {
        // given
        EmployeeService employeeService = new EmployeeService(null);

        // when & then
        assertThatThrownBy(() -> employeeService.getById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("empty employeeId");
    }

    @Test
    public void should_throw_exception_when_employee_not_exist() {
        // given
        EmployeeDao stub = mock(EmployeeDao.class);
        when(stub.getById(1L)).thenReturn(null);
        EmployeeService employeeService = new EmployeeService(stub);

        // when & then
        assertThatThrownBy(() -> employeeService.getById(1L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("employee not found");

    }

    @Test
    public void should_update_employee() {
        // given
        EmployeeDao mock = mock(EmployeeDao.class);
        Employee employee = new Employee(1L, "name");
        when(mock.getById(1L)).thenReturn(employee);
        ArgumentCaptor<Employee> nameCaptor = ArgumentCaptor.forClass(Employee.class);

        EmployeeService employeeService = new EmployeeService(mock);

        // when
        employeeService.updateEmployee(new EmployeeDto(1L, "new name"));

        // then
        verify(mock, times(1)).getById(1L);
        verify(mock, times(1)).update(nameCaptor.capture());
        assertThat(nameCaptor.getValue().getName()).isEqualTo("new name");
    }

}
