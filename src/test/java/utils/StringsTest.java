package utils;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class StringsTest {

    @Test
    public void joinsStringArray() {
        String[] strings = {"test", "array", "of", "strings"};

        Optional<String> joined = Strings.joinStrings(strings);

        assertThat(joined.orElse(null), is("test array of strings"));
    }

    @Test
    public void joinerHandlesNull() {
        Optional<String> joined = Strings.joinStrings(null);

        assertThat(joined.orElse(null), is(nullValue()));

    }
}