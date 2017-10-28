package utils;

import exceptions.EmptyCollection;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static utils.Validations.requireNonEmpty;
import static utils.Validations.requireNonNegative;

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
        requireNonNegative(-1, "test");
    }

    @Test
    public void allowsNonNegativeNumber() {
        requireNonNegative(0, "test");
    }
}