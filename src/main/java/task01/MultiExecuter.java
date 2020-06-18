package task01;

import java.util.ArrayList;
import java.util.List;
/*
 * This class demonstrates an implementation of a MultiExecuter.
 * The purpose of this class is to execute multiple tasks concurrently.
 * It achieves this concurrency by passing each task in the list to a different thread.
 */
public class MultiExecuter {

    private final List<Runnable> tasks;
    //Constructor which takes a List of tasks.
    public MultiExecuter(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());
        //For each task in the List of tasks, create a thread to pass the task into.
        //Add the thread to the List of threads.
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }
        //Start each thread in the List of threads
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
