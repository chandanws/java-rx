package chapter01;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

public class ObservableCreateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservableCreateTest.class);

    private static final String JUST = "Just";
    private static final String FIRST_MESSAGE = "First Message";
    private static final String SECOND_MESSAGE = "Second Message";

    @Test
    public void shouldImitateJust() {
        Observable<String> just = Observable.create((Subscriber<? super String> subscriber) -> {
            subscriber.onNext(JUST);
            subscriber.onCompleted();
        });
        just.subscribe(value -> {
            Assert.assertEquals(value, JUST);
        });
    }

    @Test
    public void shouldBeExecutedSynchronously() {
        Observable<String> messages = Observable.create((Subscriber<? super String> subscriber) -> {
            LOGGER.info("Subscribed");
            subscriber.onNext(FIRST_MESSAGE);
            subscriber.onNext(SECOND_MESSAGE);
            subscriber.onCompleted();
        });

        LOGGER.info("Started");
        Observable<String> transformedMessages = messages.map(message -> message + " Transformed");
        LOGGER.info("Transformed");
        transformedMessages.subscribe(message -> LOGGER.info("Printed {}", message));
    }
}
