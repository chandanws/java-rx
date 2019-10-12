package rx.playground;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Verifies functionality to limit time allocated for event emission.
 */
public class TimeoutTest {

    private static final long LOW_TIMEOUT = 95;
    private static final long HIGH_TIMEOUT = 110;

    private Observable<Confirmation> agreement;
    private TestSubscriber<Confirmation> subscriber;

    @Before
    public void setUp() {
        Observable<Confirmation> confirmationDelay = Observable.<Confirmation>empty()
                .delay(100, TimeUnit.MILLISECONDS);
        Observable<Confirmation> confirmation = Observable.just(new Confirmation())
                                                          .delay(50, TimeUnit.MILLISECONDS);
        agreement = confirmation.concatWith(confirmationDelay);
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldNotThrowTimoutException() {
        agreement.timeout(HIGH_TIMEOUT, TimeUnit.MILLISECONDS)
                 .subscribe(subscriber);

        sleepForOneSecond();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertCompleted();
    }

    @Test
    public void shouldThrowTimeoutException() {
        agreement.timeout(LOW_TIMEOUT, TimeUnit.MILLISECONDS)
                 .subscribe(subscriber);

        sleepForOneSecond();
        subscriber.assertError(TimeoutException.class);
        subscriber.assertValueCount(1);
        subscriber.assertNotCompleted();
    }

    private void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Confirmation {
    }
}
