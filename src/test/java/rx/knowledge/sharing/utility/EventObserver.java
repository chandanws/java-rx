package rx.knowledge.sharing.utility;

import lombok.extern.slf4j.Slf4j;
import rx.Observer;

@Slf4j
public class EventObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
        log.debug("Observed Completion");
    }

    @Override
    public void onError(Throwable e) {
        log.debug("Observed Error", e);
    }

    @Override
    public void onNext(T event) {
        log.debug("Observed {}", event);
    }
}