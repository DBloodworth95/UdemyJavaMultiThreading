package task04;

/*
 * A short program to demonstrate the power of using synchronized.
 * Thread 1 attempts to decrement the objects "items" field.
 * Thread 2 attempts to increment the objects "items" field.
 * The threads start at the same time which is a problem because they both want to access "items".
 * Synchronized acts as a blocker which prevents more than one thread from accessing the method/block of code at the same time.
 * Since the increment/decrement operations are in a synchronized block, one thread can only access the "items" field at a given time.
 * Once one thread is finished with it, the other thread can begin it's mutation of "items".
 * This would be a different case if each thread was running off of a different instance of the class, as they wouldn't be accessing the same
 * "items" field in memory.
 */
public class SynchronizedExample {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        Thread thread1 = new ObjectDecrementer(synchronizedObject);
        Thread thread2 = new ObjectIncrementer(synchronizedObject);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(synchronizedObject.getItems());
    }

    private static class SynchronizedObject {

        private int items = 0;

        final Object lock = new Object();

        public SynchronizedObject() {

        }

        private void increment() {
            synchronized (this.lock) {
                items++;
            }
        }

        private void decrement() {
            synchronized (this.lock) {
                items--;
            }
        }

        public synchronized int getItems() {
            synchronized (this.lock) {
                return items;
            }
        }
    }

    private static class ObjectIncrementer extends Thread {

        private SynchronizedObject synchronizedObject;

        public ObjectIncrementer(SynchronizedObject synchronizedObject) {
            this.synchronizedObject = synchronizedObject;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++)
                synchronizedObject.increment();
        }
    }

    private static class ObjectDecrementer extends Thread {

        private SynchronizedObject synchronizedObject;

        public ObjectDecrementer(SynchronizedObject synchronizedObject) {
            this.synchronizedObject = synchronizedObject;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++)
                synchronizedObject.decrement();
        }
    }
}
