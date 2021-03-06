package rx.knowledge.sharing.basics;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.knowledge.sharing.utility.ThreadLoggingObserver;

@Slf4j
public class Note003_Synchronous {

    @Test
    public void shouldBeSynchronous() {
        // 1. Catch thread's name
        String threadName = Thread.currentThread().getName();
        log.debug("Client's thread '{}'", threadName);

        // 2. Subscribe to observable
        Observable<String> observable = Observable.unsafeCreate(observer -> {
            String observableThread = Thread.currentThread().getName();
            log.debug("Observable's thread '{}'", observableThread);
            observer.onNext("PUSH!");
        });
        Observer<String> observer = new ThreadLoggingObserver<>();
        observable.subscribe(observer);

        // 3. Again log thread name
        log.debug("Client's thread '{}'", threadName);

        // Output will be:
        //      Client's thread 'main'
        //      Observable's thread 'main'
        //      Observer's thread 'main'
        //      Client's thread 'main'
    }
}
