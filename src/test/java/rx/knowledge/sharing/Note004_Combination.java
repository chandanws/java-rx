package rx.knowledge.sharing;

import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * One of the main hallmarks of RxJava library is the possibility to combine observables.
 * Original observer design pattern lacks this possibility.
 */
public class Note004_Combination {

    private final String firstEvent = "The First!";
    private final String secondEvent = "The Second!";

    @Test
    public void shouldCombine() {
        // Arrange
        Observable<String> first = Observable.fromCallable(() -> firstEvent);
        Observable<String> second = Observable.fromCallable(() -> secondEvent);
        Observable<String> combination = first.mergeWith(second);
        // Act
        TestSubscriber<String> subscriber = new TestSubscriber<>();
        combination.subscribe(subscriber);
        // Assert
        assertThat(subscriber.getOnNextEvents()).containsExactlyInAnyOrder(firstEvent, secondEvent);
    }
}
