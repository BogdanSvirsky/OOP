import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.*;
import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.HasNoOrderQueueException;
import ru.nsu.svirsky.exceptions.HasNoPizzaStorageException;
import ru.nsu.svirsky.exceptions.QueueClosedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 500));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 750));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1000));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1500));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 500));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 750));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1000));
        bakers.add(new Baker(() -> bakersCount.addAndGet(1), 1500));


        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 1, 500));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 2, 750));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 3, 1000));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 4, 1500));

        Pizzeria pizzeria = new Pizzeria(bakers, couriers, 5);
        List<Client> clients = new ArrayList<>();
        List<PizzaOrder> orders = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Client client = new Client(() -> clientsCount.addAndGet(1), pizzeria.getQueueForProducer());
            clients.add(client);
            orders.add(client.makeAnOrder(() -> ordersCount.addAndGet(1), "Пицца барбекю"));
        }

        pizzeria.beginWork();

        pizzeria.finishWork();

        pizzeria.waitAll();

        for (PizzaOrder order : orders) {
            assertTrue(order.isCompleted());
        }
    }

    @Test
    void exceptionsTest() {
        Baker baker = new Baker(() -> 0, 1);
        baker.setOrderQueue(new BlockingQueue<>());
        assertThrows(HasNoPizzaStorageException.class, baker::beginWork);

        baker = new Baker(() -> 0, 1);
        baker.setPizzaStorage(new BlockingQueue<>());
        assertThrows(HasNoOrderQueueException.class, baker::beginWork);

        BlockingQueue queue = new BlockingQueue();
        Client client = new Client(() -> 1, queue);
        queue.close();
        assertThrows(QueueClosedException.class, () -> client.makeAnOrder(() -> 1, ""));
    }
}
