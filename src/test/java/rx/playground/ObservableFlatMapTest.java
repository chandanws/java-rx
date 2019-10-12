package rx.playground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies flattening functionality of {@link rx.Observable#flatMap(Func1) flatMap} method.
 */
public class ObservableFlatMapTest {

    private Car ford;
    private Car mazda;

    @Before
    public void setUp() {
        ford = new Car("Ford", "0001");
        mazda = new Car("Mazda", "0002");
    }

    @Test
    public void shouldFlattenInnerObservable() {
        // Act
        List<String> licences = new ArrayList<>();
        Observable
                .just(ford, mazda)
                .flatMap(this::getCarLicence)
                .subscribe(licences::add);
        // Assert
        Assert.assertEquals(2, licences.size());
        Assert.assertTrue(licences.contains("0001"));
        Assert.assertTrue(licences.contains("0002"));
    }

    private Observable<String> getCarLicence(Car car) {
        return Observable.just(car.getLicence());
    }

    /**
     * A four wheels vehicle.
     */
    public static class Car {

        private String make;
        private String licence;

        public Car(String make, String licence) {
            this.make = make;
            this.licence = licence;
        }

        public String getMake() {
            return make;
        }

        public String getLicence() {
            return licence;
        }
    }
}
