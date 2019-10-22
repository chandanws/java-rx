package rx.knowledge.sharing.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.knowledge.sharing.utility.EventObserver;

@Slf4j
public class Note001_ObservableDesignPattern {

    @Test
    public void shouldObserve() {
        // 1. Something being observed
        Observable<String> observable = Observable.fromCallable(() -> "Event");
        // 2. Someone who's observing
        Observer<String> observer = new EventObserver<>();
        // 3. Link observable and observer
        observable.subscribe(observer);
    }
}
