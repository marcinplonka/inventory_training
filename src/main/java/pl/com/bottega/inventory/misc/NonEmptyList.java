package pl.com.bottega.inventory.misc;

import java.util.function.BiFunction;
import java.util.function.Consumer;

class NonEmptyList<T> implements FunList<T>{

    private final T head;
    private final FunList<T> tail;


    public NonEmptyList(T element) {
        head = element;
        tail = EmptyList.getInstance();
    }

    public NonEmptyList(T head, FunList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public FunList add(T element) {
        return new NonEmptyList(head, tail.add(element));
    }

    @Override
    public int size() {
        return 1 + tail.size();
    }

    @Override
    public FunList<T> remove(T element) {
        if (element == head || (element != null && element.equals(head)))
        {
            return tail;
        }
        else
        {
            return new NonEmptyList<>(head, tail.remove(element));
        }
    }

    @Override
    public void each(Consumer<T> c) {
        c.accept(head);
        tail.each(c);
    }

    @Override
    public <AccT> AccT foldLeft(AccT identity, BiFunction<AccT, T, AccT> folder) {
        AccT foldedHead = folder.apply(identity, head);
        return tail.foldLeft(foldedHead, folder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NonEmptyList<?> that = (NonEmptyList<?>) o;

        if (head != null ? !head.equals(that.head) : that.head != null) return false;
        return tail.equals(that.tail);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
