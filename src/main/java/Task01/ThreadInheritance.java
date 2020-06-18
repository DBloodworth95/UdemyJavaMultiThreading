package Task01;

public class ThreadInheritance {
    //Creating a thread using java.lang.Thread directly.
    public static void main(String[] args) {
        //Initialize the thread.
        Thread thread = new NewThread();
        thread.start();
    }
    //Class which inherits Thread.
    private static class NewThread extends Thread {
        //Overriding run(); which stores code to be ran on the thread.
        @Override
        public void run() {
            //It is possible to access static methods directly using "this" because of the inheritance.
            System.out.println("Thread: " + this.getName() + " is running!");
        }
    }
}
