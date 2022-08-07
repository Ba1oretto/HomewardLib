import static com.baioretto.baiolib.util.ReflectionUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ReflectionTest {
    @TestFactory
    @DisplayName("NoArgumentTest")
    Iterable<DynamicTest> NoArgumentTest() {
        DynamicTest testNoArgumentConstructor = dynamicTest("testNoArgumentConstructor", () -> {
            Constructor<InnerTest> constructor = getConstructor(InnerTest.class);
            assertNotNull(constructor);
        });

        DynamicTest testNewInstanceWithNoArgumentConstructor = dynamicTest("testNewInstanceWithNoArgumentConstructor", () -> {
            Constructor<InnerTest> constructor = getConstructor(InnerTest.class);
            InnerTest instance = newInstance(constructor);
            assertNotNull(instance);
        });

        return Arrays.asList(testNoArgumentConstructor, testNewInstanceWithNoArgumentConstructor);
    }

    @TestFactory
    @DisplayName("AllArgumentTest")
    Iterable<DynamicTest> AllArgumentTest() {
        DynamicTest testAllArgumentConstructor = dynamicTest("testAllArgumentConstructor", () -> {
            Constructor<InnerTest> constructor = getConstructor(InnerTest.class, String.class);
            assertNotNull(constructor);
        });

        DynamicTest testNewInstanceWithAllArgumentConstructor = dynamicTest("testNewInstanceWithAllArgumentConstructor", () -> {
            Constructor<InnerTest> constructor = getConstructor(InnerTest.class, String.class);
            InnerTest instance = newInstance(constructor, "test");
            assertEquals("test", instance.string);
        });

        DynamicTest testNewInstance = dynamicTest("testNewInstance", () -> {
            InnerTest instance = newInstance(InnerTest.class, String.class, "test");
            assertEquals("test", instance.string);
        });

        return Arrays.asList(testAllArgumentConstructor, testNewInstanceWithAllArgumentConstructor, testNewInstance);
    }
}

@SuppressWarnings({"unused", "FieldCanBeLocal"})
class InnerTest {
    String string;

    private InnerTest() {}
    private InnerTest(String string) {
        this.string = string;
    }
}