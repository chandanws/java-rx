package chapter01;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.exceptions.CompositeException;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

/**
 * Verifies error-handling functionality of RxJava.
 */
public class ErrorHandingTest {

    private TestSubscriber<Object> subscriber;
    private Observable.OnSubscribe<Object> erroneousEventSequence;

    @Before
    public void setUp() {
        subscriber = new TestSubscriber<>();
        erroneousEventSequence = emitter -> {
            emitter.onNext(1);
            emitter.onNext(2 / 0);
            emitter.onNext(3);
        };
    }

    @Test
    public void shouldInvokeDoOnError() {
        Observable.error(new IllegalStateException("Checked exception"))
                  .doOnError(System.err::println)
                  .subscribe(subscriber);

        subscriber.assertError(IllegalStateException.class);
        subscriber.assertNotCompleted();
        subscriber.assertNoValues();
    }

    @Test
    public void shouldEmitSingleValueAndBreak() {
        Observable.create(erroneousEventSequence)
                  .doOnError(ex -> System.err.println("Error occurred"))
                  .subscribe(subscriber);

        subscriber.assertError(ArithmeticException.class);
        subscriber.assertNotCompleted();
        subscriber.assertValue(1);
    }

    /**
     * Verifies functionality, which, in case of errors, allows to substitute
     * error event with default value and eventually emit onCompleted event.
     */
    @Test
    public void shouldSubstituteErrorEvent() {
        Observable.create(erroneousEventSequence)
                  .onErrorReturn(ex -> {
                      System.err.println(ex);
                      return 2;
                  })
                  .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2);
        subscriber.assertCompleted();
    }

    /**
     * Shows the way to resume sequence of event with another sequence.
     */
    @Test
    public void shouldResumeOnErrorEventWithAnotherSequence() {
        Observable.create(erroneousEventSequence)
                  .onErrorResumeNext(Observable.just(2, 3))
                  .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2, 3);
        subscriber.assertCompleted();
    }

    /**
     * In case {@link Observable#doOnError(Action1)} throws exception itself,
     * the downstream will receive a {@link CompositeException} which wraps all of the thrown exceptions.
     */
    @Test
    public void shouldWrapIntoCompositeException() {
        Observable.error(new SecurityException())
                  .doOnError(ex -> {
                      throw new IllegalStateException();
                  })
                  .subscribe(subscriber);

        subscriber.assertError(CompositeException.class);
        subscriber.assertNotCompleted();
        subscriber.assertNoValues();
    }
}
