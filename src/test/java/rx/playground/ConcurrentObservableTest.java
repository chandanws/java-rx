package rx.playground;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class ConcurrentObservableTest {

    @Test
    public void shouldBeInvokedConcurrently() {
        Observable<String> first = Observable.create((Subscriber<? super String> observer) -> {
            new Thread(() -> {
                observer.onNext("first - first");
                observer.onNext("first - second");
                observer.onCompleted();
            }).start();
        });

        Observable<String> second = Observable.create((Subscriber<? super String> observer) -> {
            new Thread(() -> {
                observer.onNext("second - first");
                observer.onNext("second - second");
                observer.onCompleted();
            }).start();
        });

        Observable
                .merge(first, second)
                .subscribe(System.out::println);
    }
}
