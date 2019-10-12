package rx.playground;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.exceptions.OnErrorNotImplementedException;
import rx.schedulers.Schedulers;
import rx.utility.ExceptionPropagator;


public class ErrorPropagation {

    private Observable<Object> erroneousEvents;

    @Before
    public void setUp() {
        erroneousEvents = Observable.create(subscriber -> {
            subscriber.onNext(2);
        });
    }

    /**
     * In case {@link rx.Subscriber#onError(Throwable)} is not implemented ->
     * subscriber's thread is interrupted (sync code).
     */
    @Test(expected = OnErrorNotImplementedException.class)
    public void shouldInterruptSyncSubscribersThread() {
        erroneousEvents.subscribe(System.out::println);
    }

    /**
     * Exceptions can be easily lost in case observable-composing thread / subscription thread
     * has already gone -> at least exception is thrown in observable's thread.
     */
    @Test
    public void shouldInterruptAsyncObserversThread() throws InterruptedException {
        erroneousEvents.subscribeOn(Schedulers.io())
                       .observeOn(Schedulers.computation())
                       .compose(ExceptionPropagator.propagateException())
                       .subscribe(System.out::print);

        Thread.sleep(1000);
    }
}
