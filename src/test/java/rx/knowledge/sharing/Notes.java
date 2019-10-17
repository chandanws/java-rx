package rx.knowledge.sharing;

/**
 * <h3>RxJava Notes.</h3>
 * <ol>
 * <li>An extension of <b>Observable</b> design pattern {@link Note001_ObservableDesignPattern}.</li>
 * <li>Main idea to support events pushing {@link Note002_PushInsteadOfPull}.</li>
 * <li>Synchronous by default {@link Note003_Synchronous}.</li>
 * <li>Allows to combine observables {@link Note004_Combination}.</li>
 * <li>Also supports feedback channel, so called asynchronous pulling (used to implement back-pressure mechanism).</li>
 * <li>Central type - {@link rx.Observable}. Represents stream of events.</li>
 * <li>Individual Observable streams permit neither concurrency nor parallelism.
 * Instead, they are achieved via composition of async Observables.</li>
 * <li>Main multithreading contract: events (onNext(), onCompleted(), onError()) can never be emitted concurrently.
 * In other words, a single Observable stream must always be serialized and thread-safe.</li>
 * </ol>
 */
public class Notes {
}
