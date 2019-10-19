package rx.knowledge.sharing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Observable streams allow to write code which is executed lazily until subscription.
 */
public class Note006_FromEagerToLazy {

    private final String INVOCATION_MESSAGE = "Numbers Are Being Generated";

    @Rule
    public final SystemOutRule systemOutput = new SystemOutRule().enableLog();

    /**
     * Demonstrates an approach which is only appropriate in case of absence of IO operations.
     */
    @Test
    public void shouldBeExecutedEagerly() {
        // Arrange
        Observable.just(generateNumbers()); // 'just' operator only for in-memory computation
        // No subscription -> One might think no action
        assertThat(systemOutput.getLog()).isEqualTo(INVOCATION_MESSAGE);
    }

    @Test
    public void shouldBeExecutedLazily() {
        // Arrange
        Observable.fromCallable(this::generateNumbers);
        // No subscription -> No action
        assertThat(systemOutput.getLog()).isEmpty();
    }

    private List<Integer> generateNumbers() {
        System.out.print(INVOCATION_MESSAGE);
        return Arrays.asList(1, 2, 3, 4, 5);
    }
}
