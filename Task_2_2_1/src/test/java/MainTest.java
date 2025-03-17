import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.*;
import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.QueueClosedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    void test() throws AlreadyHasOrderException, QueueClosedException, InterruptedException {
        final AtomicInteger bakersCount = new AtomicInteger(0);
        final AtomicInteger couriersCount = new AtomicInteger(0);
        final AtomicInteger clientsCount = new AtomicInteger(0);
        final AtomicInteger ordersCount = new AtomicInteger(0);

        List<Baker> bakers = new ArrayList<>();
        List<Courier> couriers = new ArrayList<>();

        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 500));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 750));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1000));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1500));


        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 1, 500));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 2, 750));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 3, 1000));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 4, 1500));

        Pizzeria pizzeria = new Pizzeria(bakers, couriers, 10);
        List<Client> clients = new ArrayList<>();
        List<PizzaOrder> orders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Client client = new Client(() -> clientsCount.addAndGet(1), pizzeria.getQueueForProducer());
            clients.add(client);
            orders.add(client.makeAnOrder(() -> ordersCount.addAndGet(1), "Пицца барбекю"));
        }

        pizzeria.beginWork();

        pizzeria.finishWork();

        Thread.sleep(3000);

        for (PizzaOrder order : orders) {
            assertTrue(order.isCompleted());
        }
    }
}
