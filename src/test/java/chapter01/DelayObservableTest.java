package chapter01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Verifies behavior of the {@link rx.Observable#delay(long, TimeUnit) delay}
 * and {@link rx.Observable#timer(long, TimeUnit) timer} methods.
 */
@Slf4j
public class DelayObservableTest {

    @Test
    public void shouldPostponeEvents() throws InterruptedException {
        Observable.just(1, 2, 3)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(event -> log.debug("{}", event));

        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void shouldDelayEvents() throws InterruptedException {
        Observable.just("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
                .flatMap(word -> Observable.timer(word.length(), TimeUnit.SECONDS).map(x -> word))
                .subscribe(event -> log.debug("{}", event));

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void shouldPreserveEventsOrder() throws InterruptedException {
        Observable.just("abc", "a", "ab")
                .concatMap(word -> Observable.timer(word.length(), TimeUnit.SECONDS).map(x -> word))
                .subscribe(event -> log.debug("{}", event));

        TimeUnit.SECONDS.sleep(10);
    }
}
