package rx.knowledge.sharing;

import org.junit.Test;
import rx.Observable;
import rx.knowledge.sharing.utility.ThreadLoggingObserver;
import rx.schedulers.Schedulers;
import rx.utility.TimeDelayer;

public class Note008_Composition {

    @Test
    public void composition() {
        Observable.just(1, 2, 3)
                  .flatMap(value -> Observable.fromCallable(() -> delayedEmission(value))
                                              .subscribeOn(Schedulers.io()))
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ThreadLoggingObserver<>());
        TimeDelayer.sleepForNumberOfSeconds(10);
    }

    private Integer delayedEmission(Integer value) {
        TimeDelayer.sleepForOneSecond();
        System.out.println(String.format("Value %s, thread's name %s",
                                         value,
                                         Thread.currentThread().getName()));
        return value;
    }
}
