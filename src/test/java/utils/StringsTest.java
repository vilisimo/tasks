package utils;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
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

    @Test
    public void appendRightAppendsGivenChars() {
        String padded = Strings.padRight("test", 80, ' ');

        assertThat(padded.length(), is(80));
        assertThat(padded.charAt(80/2), is(' '));
        assertThat(padded, containsString("test"));
    }

    @Test
    public void recognizesLargerLengthThanDesiredOne() {
        String padded = Strings.padRight("testing", 6, ' ');

        assertThat(padded.length(), is("testing".length()));
        assertThat(padded, containsString("testing"));
    }

    @Test(expected = NullPointerException.class)
    public void padRightDoesNotAcceptNull() {
        Strings.padRight(null, 1, ' ');
    }

    @Test
    public void appendLeftAppendsGivenChars() {
        String padded = Strings.padLeft("test", 80, ' ');

        assertThat(padded.charAt(0), is(' '));
        assertThat(padded.length(), is(80));
        assertThat(padded, containsString("test"));
    }

    @Test
    public void padLeftRecognizesLargerLengthThanDesiredOne() {
        String padded = Strings.padLeft("testing", 6, ' ');

        assertThat(padded.length(), is("testing".length()));
        assertThat(padded, containsString("testing"));
    }

    @Test(expected = NullPointerException.class)
    public void padLeftDoesNotAcceptNull() {
        Strings.padRight(null, 1, ' ');
    }
}