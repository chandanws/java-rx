package rx.utility;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TestSchedulers {

    public static Scheduler createAScheduler() {
        return createScheduler("Sched-A-%d");
    }

    public static Scheduler createBScheduler() {
        return createScheduler("Sched-B-%d");
    }

    public static Scheduler createCScheduler() {
        return createScheduler("Sched-C-%d");
    }

    private static Scheduler createScheduler(String pattern) {
        return Schedulers.from(
                Executors.newFixedThreadPool(
                        10,
                        createThreadFactory(pattern)
                )
        );
    }

    private static ThreadFactory createThreadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }
}
