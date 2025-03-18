import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.config.SimplePizzeriaConfig;
import ru.nsu.svirsky.pizzeria.Baker;
import ru.nsu.svirsky.pizzeria.Courier;

/**
 * Test class for verifying the configuration loading functionality.
 *
 * @author BogdanSvirsky
 */
public class ConfigTest {

    /**
     * Tests the loading of bakers and couriers from a configuration file.
     *
     * @throws IOException If the configuration file cannot be read or parsed.
     */
    @Test
    void test() throws IOException {
        // Load configuration from the JSON file
        SimplePizzeriaConfig config = new SimplePizzeriaConfig("src/test/resources/config.json");

        // Create expected lists of couriers and bakers
        List<Courier> couriers = new ArrayList<>();
        couriers.add(new Courier<>(() -> 1, 3, 1000));
        couriers.add(new Courier<>(() -> 2, 1, 500));

        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker<>(() -> 1, 1000));
        bakers.add(new Baker<>(() -> 1, 500));

        // Verify that the loaded couriers match the expected ones
        for (Courier courier : config.getCouriers()) {
            assert couriers.contains(courier);
        }

        // Verify that the loaded bakers match the expected ones
        for (Baker baker : config.getBakers()) {
            assert bakers.contains(baker);
        }
    }
}