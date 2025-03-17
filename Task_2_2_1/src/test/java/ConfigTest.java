import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.Baker;
import ru.nsu.svirsky.Courier;
import ru.nsu.svirsky.SimplePizzeriaConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigTest {
    @Test
    void test() throws IOException {
        SimplePizzeriaConfig config = new SimplePizzeriaConfig("src/test/resources/config.json");
        List<Courier> couriers = new ArrayList<>();
        couriers.add(new Courier<>(() -> 1, 3, 1000));
        couriers.add(new Courier<>(() -> 2, 1, 500));
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker<>(() -> 1, 1000));
        bakers.add(new Baker<>(() -> 1, 500));

        for (Courier courier : config.getCouriers()) {
            couriers.contains(courier);
        }

        for (Baker baker : config.getBakers()) {
            bakers.contains(baker);
        }
    }
}
