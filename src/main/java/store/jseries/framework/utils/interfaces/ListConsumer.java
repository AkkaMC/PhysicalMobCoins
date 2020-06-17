package store.jseries.framework.utils.interfaces;

import java.util.Collection;

public interface ListConsumer<T> {

    Collection<String> accept(T t);

}
