package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;

import static rx.knowledge.sharing.error.Messages.ERROR;
import static rx.knowledge.sharing.error.Messages.FINISH_MESSAGE;
import static rx.knowledge.sharing.error.Messages.START_MESSAGE;

public class ErrorNote002_AbsenceOfOnErrorCallback {

    /**
     * Observable will throw (but not pass down the stream!) an exception, in case subscriber
     * doesn't provide error-handling callback.
     */
    @Test
    public void shouldInterruptClientThread() {
        System.out.println(START_MESSAGE);

        Observable.<String>error(new IllegalStateException(ERROR))
                .subscribe(
                        event -> System.out.println(event)
                );

        System.out.println(FINISH_MESSAGE);
    }
}
