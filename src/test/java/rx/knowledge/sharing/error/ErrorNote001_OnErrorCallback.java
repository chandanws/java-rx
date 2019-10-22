package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;

public class ErrorNote001_OnErrorCallback {

    private static final String ERROR = "BOOM!";

    /**
     * Subscriber can provide a callback which will be invoked in case Observable's
     * pipeline encounters error.
     */
    @Test
    public void shouldNotifyObserverAboutError() {
        Observable.<String>error(new IllegalStateException(ERROR))
                .subscribe(
                        event -> System.out.println(event),
                        error -> System.err.println(error)
                );
    }
}
