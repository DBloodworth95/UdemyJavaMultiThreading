package task04;

public class AnotherSynchronizedExample {
    public static void main(String[] args) throws InterruptedException {
        SyncObject object = new SyncObject();
        Thread thread1 = new Incrementor(object);
        Thread thread2 = new Decrementor(object);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(object.getItems());

    }

    private static class SyncObject {
        private int items = 0;

        private final Object object = new Object();

        public SyncObject() {

        }

        private void increment() {
            synchronized (this.object) {
                items++;
            }
        }

        private void decrement() {
            synchronized (this.object) {
                items--;
            }
        }

        public int getItems() {
            synchronized (this.object) {
                return items;
            }
        }
    }

    private static class Incrementor extends Thread {
        private final SyncObject object;

        public Incrementor(SyncObject object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++)
            object.increment();
        }
    }

    private static class Decrementor extends Thread {
        private final SyncObject object;

        public Decrementor(SyncObject object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++)
                object.decrement();
        }
    }
}
