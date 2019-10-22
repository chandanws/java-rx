package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.utility.TimeDelayer;

public class ErrorNote004_OnErrorCallbackForComposedObservables {

    @Test
    public void shouldLogFirstError() {
        Observable<String> firstObservable = Observable
                .fromCallable(() -> getMessage("FIRST"))
                .subscribeOn(Schedulers.io());

        Observable<String> secondObservable = Observable
                .fromCallable(() -> getMessage("SECOND"))
                .subscribeOn(Schedulers.io());

        Observable.zip(firstObservable, secondObservable, this::combine)
                  .subscribe(
                          event -> {
                          },
                          error -> System.err.println(error.getMessage())
                  );
        TimeDelayer.sleepForOneSecond();
    }

    private String getMessage(String message) {
        TimeDelayer.sleepForRandomMillis();
        throw new RuntimeException(message);
    }

    private String combine(String first, String second) {
        return first + second;
    }
}
