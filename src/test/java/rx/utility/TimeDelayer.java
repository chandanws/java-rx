package rx.utility;

public final class TimeDelayer {

    private TimeDelayer() {
        throw new IllegalStateException("Instance creation is forbidden");
    }

    public static void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
