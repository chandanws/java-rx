package rx.chapter01;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies functionality of the {@link rx.Observable#filter(Func1)} method.
 */
public class ObservableFilterTest {

    @Test
    public void shouldFilterOddNumbers() {
        // Arrange
        List<Integer> collectedNumbers = new ArrayList<>();
        // Act
        Observable
                .range(1, 5)
                .filter(number -> number % 2 == 0)
                .subscribe(collectedNumbers::add);
        // Assert
        Assert.assertEquals(2, collectedNumbers.size());
        Assert.assertTrue(collectedNumbers.contains(2));
        Assert.assertTrue(collectedNumbers.contains(4));
    }
}
