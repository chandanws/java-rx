package rx.knowledge.sharing.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLoggingObserver<T> extends EventObserver<T> {

    @Override
    public void onNext(T event) {
        String observerThread = Thread.currentThread().getName();
        log.debug("Observer's thread '{}'", observerThread);
    }
}
