package rx.playground;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.utility.TimeDelayer;

import java.util.concurrent.TimeUnit;

/**
 * RxJava has special scheduler for time management.
 */
public class TimeManagementTest {

    private TestScheduler scheduler;
    private TestSubscriber<Character> subscriber;

    @Before
    public void setUp() {
        scheduler = Schedulers.test();
        subscriber = new TestSubscriber<>();
    }

    /**
     * Time is frozen in case {@link TestScheduler} is used. Thus no events
     * are emitted.
     */
    @Test
    public void shouldNotEmitAnyValues() {
        Observable.just('A', 'B', 'C')
                  .subscribeOn(scheduler)
                  .subscribe(subscriber);

        TimeDelayer.sleepForOneSecond();
        subscriber.assertNotCompleted();
        subscriber.assertNoValues();
    }

    /**
     * But we can manually advance the clock by specified amount of time,
     * or to particular moment in time.
     */
    @Test
    public void shouldEmitEvents() {
        Observable.just('A', 'B', 'C')
                  .subscribeOn(scheduler)
                  .subscribe(subscriber);

        scheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);
        subscriber.assertCompleted();
        subscriber.assertValueCount(3);
        subscriber.assertValues('A', 'B', 'C');

        TimeDelayer.sleepForOneSecond();
    }
}
