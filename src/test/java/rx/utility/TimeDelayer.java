package rx.utility;

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
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
