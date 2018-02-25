package pl.com.bottega.inventory.misc;

import java.util.function.BiFunction;
import java.util.function.Consumer;

class EmptyList<T> implements FunList<T>{

    private final static EmptyList INSTANCE = new EmptyList();

    private EmptyList() {
    }

    @Override
    public FunList<T> add(T element) {
        return new NonEmptyList<>(element);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public FunList<T> remove(T element) {
        return this;
    }

    @Override
    public void each(Consumer<T> c) {

    }

    @Override
    public <AccT> AccT foldLeft(AccT identity, BiFunction<AccT, T, AccT> folder) {
        return identity;
    }

    static <T> FunList<T> getInstance() {
        return (FunList<T>) INSTANCE;
    }

}
