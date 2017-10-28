package utils;

import exceptions.EmptyCollection;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static utils.Validations.requireNonEmpty;

public class ValidationsTest {

    @Test(expected = EmptyCollection.class)
    public void recognizesEmptyMap() {
        requireNonEmpty(Collections.emptyMap(), "test");
    }

    @Test
    public void recognizesNonEmptyMap() {
        requireNonEmpty(Map.of("a", "b"), "test");
    }
}