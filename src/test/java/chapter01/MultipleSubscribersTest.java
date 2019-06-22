package chapter01;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.Subscriber;

/**
 * Verifies that each subscriber is given the same sequence of events.
 */
public class MultipleSubscribersTest {

    private Subscriber<String> firstSubscriber;
    private Subscriber<String> secondSubscriber;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        firstSubscriber = Mockito.spy(Subscriber.class);
        secondSubscriber = Mockito.spy(Subscriber.class);
    }

    @Test
    public void shouldRepeatEvents() {
        // Arrange
        Observable<String> repeatedObservable = Observable.just("Repeated");
        // Act
        repeatedObservable.subscribe(firstSubscriber);
        repeatedObservable.subscribe(secondSubscriber);
        // Assert
        assertPushedEvents(firstSubscriber);
        assertPushedEvents(secondSubscriber);
    }

    private void assertPushedEvents(Subscriber<String> subscriber) {
        Mockito.verify(subscriber).onNext(Mockito.any());
        Mockito.verify(subscriber).onCompleted();
    }
}
