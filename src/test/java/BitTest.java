import org.junit.jupiter.api.Test;

public class BitTest {
    @Test
    void test() {
        int x = 1145;
        int y = 1;
        System.out.println((x + (y << 4)) >> 4);
    }
}
