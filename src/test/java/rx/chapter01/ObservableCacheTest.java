package rx.chapter01;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Verifies functionality of {@link Observable#cache()} method.
 */
public class ObservableCacheTest {

    @Test
    public void shouldCacheEvents() {
        // Arrange
        AtomicInteger invocations = new AtomicInteger();
        Observable<String> cachedObservable = Observable.create((Subscriber<? super String> subscriber) -> {
            invocations.getAndIncrement();
            subscriber.onNext("Pushed");
            subscriber.onCompleted();
        }).cache();
        // Act
        cachedObservable.subscribe(System.out::println);
        cachedObservable.subscribe(System.out::println);
        // Assert
        Assert.assertEquals(1, invocations.get());
    }
}
