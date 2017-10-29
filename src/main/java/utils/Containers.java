package utils;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static utils.Validations.requireNonEmpty;

public final class Containers {

    private Containers() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static int maxCollectionSize(Collection<Collection> collections) {
        requireNonNull(collections, "Collection should not be null");
        requireNonEmpty(collections, "Collection should not be empty");

        int size = 0;
        for (Collection collection : collections) {
            if (collection.size() > size) {
                size = collection.size();
            }
        }

        return size;
    }
}
