package rx.knowledge.sharing.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLoggingObserver<T> extends EventObserver<T> {

    @Override
    public void onNext(T event) {
        String observerThread = Thread.currentThread().getName();
        System.out.println(String.format("Observer's thread '%s'", observerThread));
    }

    @Override
    public void onCompleted() {
        String observerThread = Thread.currentThread().getName();
        System.out.println(String.format("Observed Completion '%s'", observerThread));
    }
}
