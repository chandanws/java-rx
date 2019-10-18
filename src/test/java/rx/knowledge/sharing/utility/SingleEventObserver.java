package rx.knowledge.sharing.utility;

import lombok.extern.slf4j.Slf4j;
import rx.SingleSubscriber;

@Slf4j
public class SingleEventObserver<T> extends SingleSubscriber<T> {

    @Override
    public void onSuccess(T event) {
        log.debug("Observed {}", event);
    }

    @Override
    public void onError(Throwable e) {
        log.debug("Observed Error", e);
    }
}
