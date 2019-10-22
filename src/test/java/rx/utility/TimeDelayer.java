package rx.utility;

import java.util.concurrent.ThreadLocalRandom;

public final class TimeDelayer {

    private TimeDelayer() {
        throw new IllegalStateException("Instance creation is forbidden");
    }

    public static void sleepForNumberOfSeconds(int number) {
        try {
            Thread.sleep(1000 * number);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepForAWhile() {
        sleepForAWhile(400);
    }

    public static void sleepForRandomMillis() {
        int millis = ThreadLocalRandom.current().nextInt(400, 600);
        sleepForAWhile(millis);
    }

    public static void sleepForAWhile(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
