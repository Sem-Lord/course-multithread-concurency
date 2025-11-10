package thread.cooredination.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;

        List<PowerCalculatingThread> powerCalculatingThreads = new ArrayList<>();
        powerCalculatingThreads.add(new PowerCalculatingThread(base1, power1));
        powerCalculatingThreads.add(new PowerCalculatingThread(base2, power2));

        for(Thread thread : powerCalculatingThreads){
            thread.start();
            thread.join();
        }

        for(PowerCalculatingThread thread : powerCalculatingThreads){
            if(thread.isFinished()) {
                result = result.add(thread.getResult());
            }
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
        private boolean isFinished = false;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = BigInteger.valueOf((long) Math.pow(base.doubleValue(), power.doubleValue()));
            isFinished = true;
        }

        public BigInteger getResult() { return result; }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
