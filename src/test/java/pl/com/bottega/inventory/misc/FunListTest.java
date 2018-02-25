package pl.com.bottega.inventory.misc;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FunListTest {

    @Test
    public void addElementToEmptyList() {
        FunList list = FunList.empty();

        FunList newList = list.add("element");

        assertThat(newList.size() == 1);
    }

    @Test
    public void addThreeElementsToEmptyList() {
        FunList list = FunList.empty();

        FunList newList = list.add(1).add(2).add(3);

        assertThat(newList.size() == 3);
    }

    @Test
    public void removeThreeElementsfromList() {

        FunList list = FunList.empty();

        FunList newList = list.add(1).add(null).add(3);

        FunList removedElementList = newList.remove(null);


        assertThat(removedElementList.size() == 2);
    }

    @Test
    public void mapsList() {
        FunList<String> strings = FunList.empty(String.class).add("a").add("b").add("c");
        String fold = "abc";
        assertThat(strings.foldLeft("",(acc, string) -> acc + string)).isEqualTo(fold);
    }

    @Test
    public void filtersElements() {
        FunList ints = FunList.empty(Integer.class).add(1).add(2).add(3).add(4).add(5);
//        FunList ods = ints.filter();
    }
}
