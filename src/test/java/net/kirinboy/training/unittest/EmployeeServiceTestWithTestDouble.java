package net.kirinboy.training.unittest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class EmployeeServiceTestWithTestDouble {

    @Test
    public void should_throw_exception_when_employee_id_is_empty_using_dummy() {
        // setup
        EmployeeService employeeService = new EmployeeService(dummyEmployeeDao());

        // exercise
        assertThatThrownBy(() -> employeeService.getById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("empty employeeId");

        // teardown
    }

    private EmployeeDao dummyEmployeeDao() {
        return null;
    }

    @Test
    public void should_return_employee_when_get_by_id_using_stub() {
        // setup
        EmployeeDao stub = mock(EmployeeDao.class);
        when(stub.getById(1L)).thenReturn(new Employee(1L, "name"));
        EmployeeService employeeService = new EmployeeService(stub);

        // exercise
        Employee employee = employeeService.getById(1L);

        // verify
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");

        // teardown
    }

    @Test
    public void should_return_employee_when_get_by_id_using_spy() {
        // setup
        EmployeeDao spy = mock(EmployeeDao.class);
        when(spy.getById(1L)).thenReturn(new Employee(1L, "name"));
        EmployeeService employeeService = new EmployeeService(spy);

        // exercise
        Employee employee = employeeService.getById(1L);

        // verify
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");

        verify(spy, only()).getById(anyLong());

        // teardown
    }

    @Test
    public void should_return_employee_when_get_by_id_using_spy2() {
        // setup
        EmployeeDao spy = spy(EmployeeDao.class);
        doReturn(new Employee(1L, "name")).when(spy).getById(1L);
        EmployeeService employeeService = new EmployeeService(spy);

        // exercise
        Employee employee = employeeService.getById(1L);

        // verify
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");

        verify(spy, only()).getById(anyLong());

        // teardown
    }

    @Test
    public void should_return_employee_when_get_by_id_using_mock() {
        // setup
        EmployeeDao spy = mock(EmployeeDao.class);
        when(spy.getById(1L)).thenReturn(new Employee(1L, "name"));
        EmployeeService employeeService = new EmployeeService(spy);

        // exercise
        Employee employee = employeeService.getById(1L);

        // verify
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");

        verify(spy, only()).getById(1L);

        // teardown
    }

    @Test
    public void should_return_employee_when_get_by_id_using_fake() {
        // setup
        EmployeeDao fake = new FakeEmployeeDao();
        EmployeeService employeeService = new EmployeeService(fake);

        // exercise
        Employee employee = employeeService.getById(1L);

        // verify
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("name");

        // teardown
    }
}
