package net.kirinboy.training.unittest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

public class DependedComponentTest {
    static class DependedComponent {
        public String someMethod() {
            return "some method";
        }

        public String otherMethod() {
            return someMethod();
        }
    }

    @Test
    public void stub() {
        var stub = Mockito.mock(DependedComponent.class);
        Mockito.when(stub.someMethod()).thenReturn("stub");
        assertEquals("stub", stub.someMethod());
        assertNull(stub.otherMethod());
    }

    @Test
    public void spy() {
        var spy = Mockito.spy(DependedComponent.class);
        Mockito.when(spy.someMethod()).thenReturn("spy");
        assertEquals("spy", spy.someMethod());
        assertEquals("spy", spy.otherMethod());
    }

    @Test
    public void mock() {
        var mock = Mockito.mock(DependedComponent.class);
        Mockito.when(mock.someMethod()).thenReturn("mock");
        assertEquals("mock", mock.someMethod());
        verify(mock, Mockito.only()).someMethod();
    }
}