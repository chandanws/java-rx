/**
 * The most important thing one has to keep in mind while working with RxJava library is
 * that an {@link java.util.Observable} and all of the operators applied to it are single-threaded in
 * nature.<br>
 * In order to introduce multithreading you have to instruct your operators to work on particular {@link rx.Scheduler}.<br>
 * But, even though an operator is working on a different thread, notifications, which an {@link rx.Observer} receives
 * are sequential in nature. This is one of the design guidelines listed on 'The Observable Contract' page.<br>
 *
 * @see <a href="http://reactivex.io/documentation/contract.html">The Observable Contract</a>
 *
 * @author Valentine Shemyako
 * @since October 11, 2019
 */
package rx;
