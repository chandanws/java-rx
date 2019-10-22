package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;

import static rx.knowledge.sharing.error.Messages.ERROR;
import static rx.knowledge.sharing.error.Messages.FINISH_MESSAGE;
import static rx.knowledge.sharing.error.Messages.START_MESSAGE;

public class ErrorNote003_ErrorLogging {

    @Test
    public void pipelineShouldLogException() {
        System.out.println(START_MESSAGE);

        Observable.error(new IllegalStateException(ERROR))
                  .doOnError(error -> System.err.println("SUPER SAFE PIPELINE HAS ERROR!"))
                  .subscribe(
                          event -> {
                          },
                          error -> {
                          }
                  );

        System.out.println(FINISH_MESSAGE);
    }
}
