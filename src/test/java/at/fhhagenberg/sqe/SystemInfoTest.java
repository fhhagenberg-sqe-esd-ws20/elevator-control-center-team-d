package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SystemInfoTest {
    @Test
    public void testJavaVersion() {
        assertEquals("14.0.2", SystemInfo.javaVersion());
    }

    @Test
    public void testJavafxVersion() {
        assertEquals("13", SystemInfo.javafxVersion());
    }
}