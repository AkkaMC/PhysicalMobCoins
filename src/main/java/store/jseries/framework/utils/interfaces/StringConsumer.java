package store.jseries.framework.utils.interfaces;

@FunctionalInterface
public interface StringConsumer<T> {

    String accept(T t);

}