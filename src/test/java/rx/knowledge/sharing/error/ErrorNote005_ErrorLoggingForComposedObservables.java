package rx.knowledge.sharing.error;

import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.utility.TimeDelayer;

public class ErrorNote005_ErrorLoggingForComposedObservables {

    @Test
    public void shouldLogFirstError() {
        Observable<String> first = getErroneousObservable("FIRST");
        Observable<String> second = getErroneousObservable("SECOND");
        Observable<String> third = getErroneousObservable("THIRD");
        Observable<String> fourth = getErroneousObservable("FOURTH");
        Observable<String> fifth = getErroneousObservable("FIFTH");
        Observable<String> sixth = getErroneousObservable("SIXTH");

        Observable.zip(first, second, third, fourth, fifth, sixth, this::combine)
                  .subscribe(
                          event -> System.out.println(event),
                          error -> System.err.println(String.format("Subscriber logged '%s', on thread '%s'",
                                                                    error.getMessage(),
                                                                    Thread.currentThread().getName()))
                  );
        TimeDelayer.sleepForOneSecond();
    }

    private Observable<String> getErroneousObservable(String errorMessage) {
        return Observable
                .fromCallable(() -> getMessage(errorMessage))
                .doOnError(error -> System.err.println(String.format("Observable logged '%s', on thread '%s'",
                                                                     error.getMessage(),
                                                                     Thread.currentThread().getName())))
                .subscribeOn(Schedulers.io());
    }

    private String getMessage(String message) {
        TimeDelayer.sleepForAWhile(300);
        throw new RuntimeException(message);
    }

    private String combine(String... messages) {
        return String.join("", messages);
    }
}
