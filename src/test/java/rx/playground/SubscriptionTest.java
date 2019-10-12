package rx.playground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class SubscriptionTest {

    private Observable<Number> numbersObservingFailure;
    private Observable<Number> numbersObservingSuccess;

    @Before
    public void setUp() throws Exception {
        numbersObservingFailure = Observable.create((Subscriber<? super Number> s) -> {
            s.onNext(1);
            s.onNext(2);
            s.onError(new IllegalStateException("Failed abruptly"));
        });
        numbersObservingSuccess = Observable.create((Subscriber<? super Number> s) -> {
            s.onNext(1);
            s.onNext(2);
            s.onCompleted();
        });
    }

    @Test
    public void shouldSubscribeOnlyToSuccessEvent() {
        try {
            numbersObservingFailure.subscribe(System.out::println);
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSubscribeToBothSuccessAndErrorEvents() {
        numbersObservingFailure.subscribe(
                System.out::println,
                Throwable::printStackTrace
        );
    }

    @Test
    public void shouldSubscribeToAllOfTheEvents() {
        numbersObservingSuccess.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Successfully finished")
        );
    }
}
