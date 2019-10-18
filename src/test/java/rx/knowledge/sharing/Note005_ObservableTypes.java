package rx.knowledge.sharing;

import org.junit.Test;
import rx.Single;
import rx.knowledge.sharing.utility.SingleEventObserver;

public class Note005_ObservableTypes {

    /**
     * Well behaved {@link rx.Single} should emit either success event or error event.
     */
    @Test
    public void shouldEmitSingleValue() {
        Single<String> single = Single.create(observer -> {
            try {
                observer.onSuccess("One");
            } catch (Throwable e) {
                observer.onError(e);
            }
        });
        SingleEventObserver<String> observer = new SingleEventObserver<>();
        single.subscribe(observer);
    }
}
