package utils;

import exceptions.EmptyCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContainersTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void figuresOutMaxCollectionSize() {
        int maxSize = Containers.maxCollectionSize(List.of(
                List.of(1, 2,3 ),
                List.of(1, 2),
                List.of(1)));

        assertThat(maxSize, is(3));
    }

    @Test
    public void figuresOutMaxSizeWhenAllCollectionsAreOfSameSize() {
        int maxSize = Containers.maxCollectionSize(List.of(
                List.of(1, 2),
                List.of(3, 4)));

        assertThat(maxSize, is(2));
    }

    @Test
    public void doesNotAcceptNullContainer() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Collection should not be null");

        Containers.maxCollectionSize(null);
    }

    @Test(expected = EmptyCollection.class)
    public void doesNotAcceptEmptyContainer() {
        Containers.maxCollectionSize(Collections.emptyList());
    }
}