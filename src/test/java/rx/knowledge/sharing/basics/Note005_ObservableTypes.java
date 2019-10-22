package rx.knowledge.sharing.basics;

import org.junit.Test;
import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.knowledge.sharing.utility.CompletionEventObserver;
import rx.knowledge.sharing.utility.EventObserver;
import rx.knowledge.sharing.utility.SingleEventObserver;

public class Note005_ObservableTypes {

    /**
     * Well behaved {@link rx.Single} should emit either success event or error event.
     */
    @Test
    public void shouldEmitSingleValue() {
        Single<String> single = Single.create(observer -> {
            try {
                observer.onSuccess("One");
            } catch (Throwable e) {
                observer.onError(e);
            }
        });
        SingleEventObserver<String> observer = new SingleEventObserver<>();
        single.subscribe(observer);
    }

    /**
     * {@link rx.Completable} is counterpart of a {@link Single} which notifies
     * its observers about completion of action.
     */
    @Test
    public void shouldEmitCompletionEvent() {
        Completable completable = Completable.create(observer -> {
            try {
                observer.onCompleted();
            } catch (Throwable e) {
                observer.onError(e);
            }
        });
        CompletionEventObserver observer = new CompletionEventObserver();
        completable.subscribe(observer);
    }

    @Test
    public void shouldEmitSeveralValues() {
        Observable<Integer> observable = Observable.create(observer -> {
            int count = 0;
            try {
                while (count < 3) {
                    observer.onNext(count++);
                }
            } catch (Throwable e) {
                observer.onError(e);
            }
            observer.onCompleted();
        });
        EventObserver<Integer> observer = new EventObserver<>();
        observable.subscribe(observer);
    }
}
