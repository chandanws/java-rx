package rx.knowledge.sharing;

import org.junit.Test;
import rx.Observable;
import rx.knowledge.sharing.utility.ThreadLoggingObserver;
import rx.schedulers.Schedulers;
import rx.utility.TestSchedulers;

public class Note007_SubscribeOn {

    @Test
    public void shouldRunInDifferentThread() throws InterruptedException {
        System.out.println("Client's thread - " + Thread.currentThread().getName());

        Observable.defer(() -> {
            System.out.println("Callback execution - " + Thread.currentThread().getName());
            return Observable.just(1);
        })
                  .map(number -> {
                      System.out.println("Plus 1 - " + Thread.currentThread().getName());
                      return number + 1;
                  })
                  .map(number -> {
                      System.out.println("Plus 10 - " + Thread.currentThread().getName());
                      return number + 10;
                  })
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ThreadLoggingObserver<>());

        Thread.sleep(1000);
    }

    @Test
    public void whatAboutObserveOn() throws InterruptedException {
        System.out.println("Client's thread - " + Thread.currentThread().getName());

        Observable.defer(() -> {
            System.out.println("Callback execution - " + Thread.currentThread().getName());
            return Observable.just(1);
        })
                  .observeOn(TestSchedulers.createAScheduler())
                  .map(number -> {
                      System.out.println("Plus 1 - " + Thread.currentThread().getName());
                      return number + 1;
                  })
                  .observeOn(TestSchedulers.createBScheduler())
                  .map(number -> {
                      System.out.println("Plus 10 - " + Thread.currentThread().getName());
                      return number + 10;
                  })
                  .subscribeOn(Schedulers.io())
                  .subscribe(new ThreadLoggingObserver<>());

        Thread.sleep(1000);
    }
}
