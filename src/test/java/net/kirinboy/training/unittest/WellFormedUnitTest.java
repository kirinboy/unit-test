package net.kirinboy.training.unittest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WellFormedUnitTest {
    @Mock
    private EmployeeDao employeeDao;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_return_employee_when_get_by_id() {
        when(employeeDao.getById(1L)).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("employee name");
        verify(employeeDao).getById(1L);
    }

    @Test
    public void should_update_employee() {
        Employee employee = new Employee(1L, "name");
        when(employeeDao.getById(1L)).thenReturn(employee);

        employeeService.updateEmployee(new EmployeeDto(1L, "new name"));

        verify(employeeDao).getById(eq(1L));
        verify(employeeDao).update(employeeCaptor.capture());
        assertThat(employeeCaptor.getValue().getName()).isEqualTo("new name");
    }

    @Test
    public void no_assertion() {
        when(employeeDao.getById(1L)).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);
        System.out.println(employee.getId());
        System.out.println(employee.getName());
    }

    @Test
    public void lack_of_assertion() {
        when(employeeDao.getById(1L)).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);
        assertThat(employee.getId()).isEqualTo(1L);
    }

    @Test
    @Disabled
    public void abused_assertion() {
        Employee employee = new Employee(1L, "employee name");
        when(employeeDao.getById(1L)).thenReturn(employee);
//        boolean result =
//                employeeService.updateEmployee(new EmployeeDto(1L, "employee name"));
//
//        assertThat(result).isTrue();
    }

    @Test
    public void incorrect_mocking() {
        when(employeeDao.getById(anyLong())).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("employee name");
    }

    @Test
    public void should_use_matcher() {
        when(employeeDao.getById(eq(1L))).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("employee name");
        verify(employeeDao).getById(eq(1L));
    }

    @Test
    public void misuse_argument_captor() {
        Employee employee = new Employee(1L, "name");
        when(employeeDao.getById(1L)).thenReturn(employee);
        doNothing().when(employeeDao).update(employeeCaptor.capture());

        employeeService.updateEmployee(new EmployeeDto(1L, "new name"));

        verify(employeeDao).getById(eq(1L));
        assertThat(employeeCaptor.getValue().getName()).isEqualTo("new name");
    }

    @Test
    public void misuse_argument_captor2() {
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        when(employeeDao.getById(idCaptor.capture())).thenReturn(new Employee(1L, "employee name"));
        Employee employee = employeeService.getById(1L);
        assertThat(idCaptor.getValue()).isEqualTo(1L);
    }
}
