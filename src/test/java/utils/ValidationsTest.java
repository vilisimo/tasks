package utils;

import exceptions.EmptyCollection;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static utils.Validations.requireLarger;
import static utils.Validations.requireNonEmpty;

public class ValidationsTest {

    @Test(expected = EmptyCollection.class)
    public void recognizesEmptyMap() {
        requireNonEmpty(Collections.emptyMap(), "test");
    }

    @Test
    public void allowsNonEmptyMap() {
        requireNonEmpty(Map.of("a", "b"), "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void recognizesNegativeNumber() {
        requireLarger(0, -1, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotPermitLowerNumber() {
        requireLarger(10, 9, "test");
    }

    @Test
    public void allowsNonNegativeNumber() {
        requireLarger(0, 0, "test");
    }
}