import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.*;
import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.HasNoOrderQueueException;
import ru.nsu.svirsky.exceptions.HasNoPizzaStorageException;
import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.pizzeria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Main test class for verifying the functionality of the pizzeria system.
 *
 * @author BogdanSvirsky
 */
public class MainTest {

    /**
     * Tests the main workflow of the pizzeria, including bakers, couriers, and clients.
     *
     * @throws AlreadyHasOrderException If a client tries to place an order while already having one.
     * @throws QueueClosedException     If the order queue is closed.
     * @throws InterruptedException    If the thread is interrupted.
     */
    @Test
    void test() throws AlreadyHasOrderException, QueueClosedException, InterruptedException {
        final AtomicInteger bakersCount = new AtomicInteger(0);
        final AtomicInteger couriersCount = new AtomicInteger(0);
        final AtomicInteger clientsCount = new AtomicInteger(0);
        final AtomicInteger ordersCount = new AtomicInteger(0);

        List<Baker> bakers = new ArrayList<>();
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

        List<Courier> couriers = new ArrayList<>();
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 1, 500));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 2, 750));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 3, 1000));
        couriers.add(new Courier(() -> couriersCount.addAndGet(1), 4, 1500));

        Pizzeria pizzeria = new Pizzeria(bakers, couriers, 5);

        List<Client> clients = new ArrayList<>();
        List<PizzaOrder> orders = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
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

    /**
     * Tests exception handling in the pizzeria system.
     */
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

        Client client2 = new Client(() -> 1, new BlockingQueue<>());
        assertDoesNotThrow(() -> client2.makeAnOrder(() -> 1, "dsdad"));
        assertThrows(AlreadyHasOrderException.class, () -> client2.makeAnOrder(() -> 1, "dsdad"));
    }
}