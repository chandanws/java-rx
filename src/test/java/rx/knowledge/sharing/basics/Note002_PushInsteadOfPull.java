package rx.knowledge.sharing.basics;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.knowledge.sharing.utility.EventObserver;

public class Note002_PushInsteadOfPull {

    @Test
    public void shouldPush() {
        // 1. Internally, observable uses observer's call-backs to push events downstream
        Observable<String> observable = Observable.unsafeCreate(
                (Subscriber<? super String> observer) -> observer.onNext("PUSH!")
        );
        // 2. Observer, which provides call-backs to be invoked
        Observer<String> observer = new EventObserver<>();
        // 3. Link observable and observer
        observable.subscribe(observer);
    }
}
