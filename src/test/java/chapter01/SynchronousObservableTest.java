package chapter01;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class SynchronousObservableTest {

    @Test
    public void shouldBeSynchronousByDefault() {
        Observable<String> wordsObserved = Observable.create((Subscriber<? super String> s) -> {
            s.onNext("first");
            s.onNext("second");
            s.onNext("third");
            s.onCompleted();
        });
        wordsObserved
                .map((String word) -> String.format("%s has been processed", word))
                .subscribe(System.out::println);
    }
}
