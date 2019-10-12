package rx.playground;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

/**
 * Verifies functionality of a {@link rx.subjects.Subject} class which is {@link Observable}
 * and {@link rx.Observer} at the same time.
 */
public class SubjectTest {

    private PublishSubject<String> subject;
    private Subscriber<String> firstSubscriber;
    private Subscriber<String> secondSubscriber;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        subject = PublishSubject.create();
        firstSubscriber = Mockito.spy(Subscriber.class);
        secondSubscriber = Mockito.spy(Subscriber.class);
    }

    @Test
    public void shouldBehaveLikeObservable() {
        // Act
        subject.subscribe(firstSubscriber);
        subject.onNext("First");
        subject.subscribe(secondSubscriber);
        subject.onNext("Second");
        subject.onCompleted();
        // Assert
        Mockito.verify(firstSubscriber, Mockito.times(2)).onNext(Mockito.any());
        Mockito.verify(secondSubscriber, Mockito.times(1)).onNext(Mockito.any());
        Mockito.verify(firstSubscriber, Mockito.times(1)).onCompleted();
        Mockito.verify(secondSubscriber, Mockito.times(1)).onCompleted();
    }
}
