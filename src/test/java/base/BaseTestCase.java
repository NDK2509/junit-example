package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

abstract public class BaseTestCase {
    @BeforeEach
    protected void setup() {
        System.out.println("Before each test");
    }

    @AfterEach
    protected void teardown() {

        System.out.println("After each test");
        Mockito.framework().clearInlineMocks();
    }
}
