package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;

import static rx.knowledge.sharing.error.Messages.ERROR;
import static rx.knowledge.sharing.error.Messages.EVENT;
import static rx.knowledge.sharing.error.Messages.FINISH_MESSAGE;
import static rx.knowledge.sharing.error.Messages.START_MESSAGE;

public class ErrorNote001_OnErrorCallback {

    /**
     * Subscriber can provide a callback which will be invoked in case Observable's
     * pipeline encounters error.
     */
    @Test
    public void shouldNotifyObserverAboutError() {
        System.out.println(START_MESSAGE);

        Observable.<String>error(new IllegalStateException(ERROR))
                .subscribe(
                        event -> System.out.println(event),
                        error -> System.err.println(error)
                );

        System.out.println(FINISH_MESSAGE);
    }

    /**
     * In case subscriber's error-handling callback itself throws an error ->
     * thread is interrupted.
     */
    @Test
    public void shouldNotifyObserverAboutError_2() {
        System.out.println(START_MESSAGE);

        Observable.<String>error(new IllegalStateException(ERROR))
                .subscribe(
                        event -> System.out.println(event),
                        error -> {
                            throw new RuntimeException();
                        }
                );

        System.out.println(FINISH_MESSAGE);
    }

    /**
     * In case subscriber's onNext-handling callback itself throws an error ->
     * subscriber's onError-handling callback is invoked.
     */
    @Test
    public void shouldNotifyObserverAboutError_3() {
        System.out.println(START_MESSAGE);

        Observable.just(EVENT)
                  .subscribe(
                          event -> {
                              throw new IllegalStateException();
                          },
                          error -> System.err.println(error)
                  );

        System.out.println(FINISH_MESSAGE);
    }
}
