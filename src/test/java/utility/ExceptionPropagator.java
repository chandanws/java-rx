package utility;

import rx.Observable;
import rx.exceptions.Exceptions;

public class ExceptionPropagator {

    private ExceptionPropagator() {
        throw new IllegalStateException("Instance creation is forbidden");
    }

    public static <T> Observable.Transformer<T, T> propagateException() {
        return observable -> observable.onErrorReturn(throwable -> {
            System.out.println("Error propagator in action");
            throw Exceptions.propagate(throwable);
        });
    }
}
