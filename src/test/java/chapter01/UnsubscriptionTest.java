package chapter01;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

public class UnsubscriptionTest {

    @Test
    public void shouldUnsubscribe() {
        Observable<Number> numbersObservingSuccess = Observable.create((Subscriber<? super Number> s) -> {
            s.onNext(1);
            Assert.fail();
            s.onNext(2);
            s.onCompleted();
        });

        numbersObservingSuccess.subscribe(new Subscriber<Number>() {
            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Failed");
            }

            @Override
            public void onNext(Number number) {
                if (number.equals(1)) {
                    this.unsubscribe();
                }
            }
        });
    }
}
