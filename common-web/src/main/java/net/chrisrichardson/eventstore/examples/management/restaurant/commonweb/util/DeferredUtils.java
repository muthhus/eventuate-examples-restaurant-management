package net.chrisrichardson.eventstore.examples.management.restaurant.commonweb.util;

import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.atomic.AtomicReference;

public class DeferredUtils {

  public static <T> DeferredResult<T> toDeferredResult(Observable<T> o) {
    final DeferredResult<T> d = new DeferredResult<T>();
    final AtomicReference<T> r = new AtomicReference<T>();

    try {
      o.single().subscribe(new Subscriber<T>() {
        @Override
        public void onCompleted() {
          d.setResult(r.get());
        }

        @Override
        public void onError(Throwable e) {
          d.setErrorResult(e);
        }

        @Override
        public void onNext(T t) {
          r.set(t);
        }
      });
    } catch (Exception e) {
      d.setErrorResult(e);
    }
    return d;
  }
}
