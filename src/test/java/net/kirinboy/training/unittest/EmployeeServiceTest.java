package net.kirinboy.training.unittest;

import net.kirinboy.training.unitteset.Employee;
import net.kirinboy.training.unitteset.EmployeeDao;
import net.kirinboy.training.unitteset.EmployeeDto;
import net.kirinboy.training.unitteset.EmployeeNotFoundException;
import net.kirinboy.training.unitteset.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeService employeeService;

    @Captor
    private ArgumentCaptor<Employee> nameCaptor;

    @Test
    void should_update_employee() {
        // given
        Employee employee = new Employee(1L, "name");
        when(employeeDao.getById(1L)).thenReturn(employee);
//        ArgumentCaptor<Employee> nameCaptor = ArgumentCaptor.forClass(Employee.class);

        // when
        employeeService.updateEmployee(new EmployeeDto(1L, "new name"));

        // then
        verify(employeeDao, times(1)).getById(1L);
        verify(employeeDao, times(1)).update(nameCaptor.capture());
        assertThat(nameCaptor.getValue().getName()).isEqualTo("new name");
    }

    @Test
    void should_return_employee_when_get_by_id() {
        // given
        when(employeeDao.getById(1L))
                .thenReturn(new Employee(1L, "name"));
        // when
        Employee employee = employeeService.getById(1L);

        // then
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");
    }

    @Test
    void should_throw_exception_when_employee_id_is_empty() {
        // when & then
        assertThatThrownBy(() -> employeeService.getById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("empty employeeId");
    }

    @Test
    void should_throw_exception_when_employee_not_exist() {
        // given
        when(employeeDao.getById(1L)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> employeeService.getById(1L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("employee not found");

    }
}
