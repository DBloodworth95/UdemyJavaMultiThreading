package task01;

public class BasicThreadCreation {
    //Example of a basic thread using Runnable();
    public static void main(String[] args) {
        //Declaring the thread which takes a Runnable() as an arg.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //This code will run in the thread we have created.
                System.out.println("Thread: " + Thread.currentThread().getName() + " is now running.");
                System.out.println(Thread.currentThread().getName() + " has a priority of " + Thread.currentThread().getPriority());
            }
        });
        //Setting name of thread.
        thread.setName("Worker Thread");
        //Setting the priority (10 being the max, 1 being the lowest).
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Thread: " + Thread.currentThread().getName() + " is about to start.");
        //Starting the thread.
        thread.start();
        System.out.println("Thread: " + Thread.currentThread().getName() + " has started.");
    }
}
