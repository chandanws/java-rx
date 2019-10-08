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

    @Before
    public void setUp() {
        subscriber = new TestSubscriber<>();
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
    public void name() {
        Observable.OnSubscribe<Object> onSubscribe = emitter -> {
            emitter.onNext(1);
            emitter.onNext(2 / 0);
            emitter.onNext(3);
        };
        Observable.create(onSubscribe)
                  .doOnError(ex -> System.err.println("Error occurred"))
                  .subscribe(subscriber);

        subscriber.assertError(ArithmeticException.class);
        subscriber.assertNotCompleted();
        subscriber.assertValue(1);
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
