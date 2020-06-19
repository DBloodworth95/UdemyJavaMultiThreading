package task02;

import java.math.BigInteger;
/*
    * This program demonstrates a demanding computational task of working out the result of a "to the power of" calculation.
    * The program interrupts the thread before it has time to calculate the result.
    * This interruption is created when a check is made to see if the current thread has been interrupted somewhere in the code (main).
    * Without this check, we have no logic to handle the thread interruption which means that the interruption would fail without it.
 */
public class InterruptingBigTask {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationClass(new BigInteger("20000"), new BigInteger("10000000")));
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationClass implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationClass(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = " + getResult(base, power));
        }

        private BigInteger getResult(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                //Logic to handle the thread interruption.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Premature interruption of thread.");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
