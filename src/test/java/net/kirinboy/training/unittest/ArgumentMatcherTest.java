package net.kirinboy.training.unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArgumentMatcherTest {
    static class DependedComponent {
        String docMethod(String someArgument) {
            // call other API to get data
            return "Some result based on the API return";
        }
    }

    static class SystemUnderTest {
        DependedComponent doc;

        String sutMethod(String someArgument) {
            return doc.docMethod(someArgument);
        }
    }

    @Mock
    DependedComponent doc;
    @InjectMocks
    SystemUnderTest sut;

    @Test
    public void matcher() {
        when(doc.docMethod(any())).thenReturn("doc");
        assertEquals("doc", sut.sutMethod("any"));

        when(doc.docMethod(anyString())).thenReturn("doc");
        assertEquals("doc", sut.sutMethod("any string"));

        when(doc.docMethod(eq("sut"))).thenReturn("doc");
        assertEquals("doc", sut.sutMethod("sut"));

//        when(doc.docMethod(eq("sut"))).thenReturn("doc");
//        assertEquals("doc", sut.sutMethod("not match"));

        when(doc.docMethod("sut")).thenReturn("doc");
        assertEquals("doc", sut.sutMethod("sut"));
    }
}
