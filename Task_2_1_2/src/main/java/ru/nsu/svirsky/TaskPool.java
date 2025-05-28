package ru.nsu.svirsky;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskPool {
    private Set<Task> tasks = new HashSet<>();
    private List<Task> availableTasks = new ArrayList<>();
    private final Object lock = new Object();

    public TaskPool(int[] array, int chunkSize) {
        for (int i = 0; i < array.length; i += chunkSize) {
            tasks.add(new Task(Arrays.copyOfRange(array, i,
                    Math.min(i + chunkSize, array.length))));
        }
        availableTasks.addAll(tasks);
    }

    public Task getTask() {
        synchronized (lock) {
            if (availableTasks.isEmpty()) {
                return null;
            }
            return availableTasks.removeFirst();
        }
    }

    public boolean isCompleted() {
        return tasks.stream().anyMatch((task) -> task.getStatus() == Task.Status.COMPLETED);
    }

    public boolean getResult() {
        return tasks.stream().anyMatch(Task::getResult);
    }
}
