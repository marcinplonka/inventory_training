package pl.com.bottega.inventory.misc;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static pl.com.bottega.inventory.misc.EmptyList.getInstance;

public interface FunList<T> {
    FunList add(T element);

    int size();

    static <T> FunList<T> empty() {
        return getInstance();
    }

    static <T> FunList<T> empty(Class<T> klass) {
        return EmptyList.getInstance();
    }

    FunList<T> remove(T element);

    void each(Consumer<T> c);

    <AccT> AccT foldLeft(AccT identity,BiFunction<AccT, T, AccT> folder);
}
