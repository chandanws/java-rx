package rx.chapter01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

/**
 * Verifies functionality offered by {@link Observable#map(Func1) map} function.
 */
@Slf4j
public class ObservableMapTest {

    @Test
    public void shouldConvertEvents() {
        Observable
                .just(1, 2)
                .map(number -> number * 2)
                .subscribe(event -> log.debug("{}", event));
    }
}
