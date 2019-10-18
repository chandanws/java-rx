package rx.knowledge.sharing.utility;

import lombok.extern.slf4j.Slf4j;
import rx.CompletableSubscriber;
import rx.Subscription;

@Slf4j
public class CompletionEventObserver implements CompletableSubscriber {

    @Override
    public void onCompleted() {
        log.debug("Observed Completion");
    }

    @Override
    public void onError(Throwable e) {
        log.debug("Observed Error", e);
    }

    @Override
    public void onSubscribe(Subscription d) {
    }
}
