package task04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
    * A short example of a data race solution.
    * SharedClass contains an increment method which increments x and y.
    * It also contains a method which checks if y > x and prints if true.
    * The problem demonstrated is that if we have two threads, one that invokes increment()
    * and one that invokes the check whilst both looping and running at the same time,
    * a check may be invoked whilst x hasn't incremented yet as it's beaten thread 1 in the race.
    * A solution to this problem is giving the variables a "volatile" declaration.
    * This allows the variables to be read & written across threads and also keeps the
    * code execution in the correct order.
    * Alternatively, AtomicInteger can also be used but it is more expensive.
 */
public class DataRaceExample {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++)
                sharedClass.increment();
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++)
                sharedClass.checkForGreater();
        });

        thread1.start();
        thread2.start();
    }

    private static class SharedClass {
        private AtomicInteger x = new AtomicInteger(0);

        private AtomicInteger y = new AtomicInteger(0);

        private void increment() {
            x.incrementAndGet();
            y.incrementAndGet();
        }

        private void checkForGreater() {
            if (y.get() > x.get())
                System.out.println("y > x - Data race detected");

        }
    }

}
