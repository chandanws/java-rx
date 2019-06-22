package chapter01;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.Subscriber;

/**
 * Tests {@link rx.Observable} which emits only single event and gracefully stops.
 */
public class JustObservableTest {

    private Subscriber<Integer> subscriber;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        subscriber = Mockito.spy(Subscriber.class);
    }

    @Test
    public void shouldEmitSingleEvent() {
        // Arrange
        Observable<Integer> justObservable = Observable.just(1);
        // Act
        justObservable.subscribe(subscriber);
        // Assert
        Mockito.verify(subscriber).onNext(Mockito.any());
        Mockito.verify(subscriber).onCompleted();
    }

    @Test
    public void shouldImitateJustObservable() {
        // Arrange
        Observable<Integer> justObservable = Observable.create((Subscriber<? super Integer> justSubscriber) -> {
            justSubscriber.onNext(1);
            justSubscriber.onCompleted();
        });
        // Act
        justObservable.subscribe(subscriber);
        // Assert
        Mockito.verify(subscriber).onNext(Mockito.any());
        Mockito.verify(subscriber).onCompleted();
    }
}
