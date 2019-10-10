package chapter01;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.exceptions.CompositeException;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Verifies error-handling functionality of RxJava.
 * Note: it's important to remember that all operator in RxJava swallow exceptions
 * in case error-handling functionality is introduced, e.g. onErrorReturn. Logging
 * and monitoring part is up to user.
 */
public class ErrorHandingTest {

    private TestSubscriber<Object> subscriber;
    private Observable.OnSubscribe<Object> erroneousEventSequence;
    private Observable.OnSubscribe<Object> correctEventSequence;
    private Observable<Object> asyncErroneousObservable;
    private Observable<Object> asyncCorrectObservable;

    @Before
    public void setUp() {
        subscriber = new TestSubscriber<>();
        erroneousEventSequence = emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3 / 0);
        };
        correctEventSequence = emitter -> {
            emitter.onNext(4);
            emitter.onNext(5);
            emitter.onNext(6);
        };
        asyncErroneousObservable = Observable.create(erroneousEventSequence)
                                             .subscribeOn(Schedulers.io())
                                             .observeOn(Schedulers.computation());
        asyncCorrectObservable = Observable.create(correctEventSequence)
                                           .subscribeOn(Schedulers.io())
                                           .observeOn(Schedulers.computation());
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
        subscriber.assertValues(1, 2);
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
                      return 3;
                  })
                  .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2, 3);
        subscriber.assertCompleted();
    }

    /**
     * Shows the way to resume sequence of event with another sequence.
     */
    @Test
    public void shouldResumeOnErrorEventWithAnotherSequence() {
        Observable.create(erroneousEventSequence)
                  .onErrorResumeNext(Observable.just(3, 4))
                  .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValues(1, 2, 3, 4);
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

    @Test
    public void flatMapChainShouldBeInterruptedInCaseOfError() {
        Observable.create(erroneousEventSequence)
                  .doOnError(ex -> System.err.println("Outer observable error 1"))
                  .doOnError(ex -> System.err.println("Outer observable error 2"))
                  // In case of error - event is not emitted, thus no transformation happens
                  .flatMap(event -> Observable.create(correctEventSequence)
                                              .doOnError(ex -> System.err.println("Inner observable error")))
                  .doOnError(ex -> System.err.println("Merged observable error 1"))
                  .doOnError(ex -> System.err.println("Merged observable error 2"))
                  .doOnNext(System.out::println)
                  .subscribe(subscriber);

        subscriber.assertError(ArithmeticException.class);
        subscriber.assertValues(4, 5, 6, 4, 5, 6);
        subscriber.assertNotCompleted();
    }

    @Test
    public void asyncFlatMapChainShouldBeInterruptedInCaseOfError() throws InterruptedException {
        asyncErroneousObservable
                .doOnError(ex -> printError("Outer"))
                .flatMap(event -> asyncCorrectObservable
                        .doOnNext(innerEvent -> printEvent("Inner"))
                        .doOnError(ex -> printError("Inner")))
                .doOnError(ex -> printError("Merged"))
                .doOnNext(event -> printEvent("Merged"))
                .subscribe(subscriber);

        Thread.sleep(1000);
        subscriber.assertError(ArithmeticException.class);
        subscriber.assertNotCompleted();
    }

    private void printError(String name) {
        System.err.println(String.format("%s observable error - %s", name, Thread.currentThread().getName()));
    }

    private void printEvent(String name) {
        System.out.println(String.format("%s observable event - %s", name, Thread.currentThread().getName()));
    }
}
