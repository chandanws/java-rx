package testing;

import org.junit.Before;
import org.junit.Test;
import rx.Single;
import rx.observers.TestSubscriber;

/**
 * Gives examples of the most basic usage of a {@link rx.observers.TestSubscriber} class.
 */
public class BasicTestSubscriberUsage {

    private TestSubscriber<String> subscriber;

    @Before
    public void setUp() {
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldTestSuccessEvent() {
        // Arrange & Act
        Single.fromCallable(() -> "Success").subscribe(subscriber);
        // Assert
        subscriber.assertValueCount(1);
        subscriber.assertCompleted();
    }

    @Test
    public void shouldTestErrorEvent() {
        // Arrange & Act
        Single.<String>fromCallable(() -> {
            throw new IllegalStateException();
        }).subscribe(subscriber);
        // Assert
        subscriber.assertValueCount(0);
        subscriber.assertError(IllegalStateException.class);
        subscriber.assertNotCompleted();
    }
}
