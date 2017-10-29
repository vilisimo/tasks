package utils;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static utils.Validations.requireNonEmpty;

public final class Containers {

    private Containers() {
        throw new AssertionError("The class should not be instantiated");
    }

    /**
     * Makes each collection of the same size by adding a specified filler.
     */
    public static <T> void normalizeListSizes(List<List<T>> lists, T filler) {
        requireNonNull(lists, "Collection should not be null");

        if (lists.isEmpty()) {
            return;
        }

        int maxSize = maxListSize(lists);

        for (List<T> collection : lists) {
            for (int i = collection.size(); i < maxSize; i++) {
                collection.add(filler);
            }
        }
    }

    public static <T> int maxListSize(List<List<T>> lists) {
        requireNonNull(lists, "Collection should not be null");
        requireNonEmpty(lists, "Collection should not be empty");

        int size = 0;
        for (Collection collection : lists) {
            if (collection.size() > size) {
                size = collection.size();
            }
        }

        return size;
    }
}
