package de.sk.basics;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestUtils {

    private static final String TEST_RESOURCES_DIR = "src/test/resources";

    private TestUtils() {
        // only utility methods
    }

    public static int @NotNull [] readIntsFromResourceFile(@NotNull String filename) throws IOException {
        Path resDir = Path.of(StringUtils.EMPTY, TEST_RESOURCES_DIR);
        Path file = resDir.resolve(filename);
        String content = Files.readString(file);
        return Arrays.stream(content.split(System.lineSeparator())).mapToInt(Integer::valueOf).toArray();
    }

    public static <T> List<T> convertPairToList(@NotNull Pair<T, T> pair) {
        List<T> result = new ArrayList<>();
        result.add(pair.getLeft());
        result.add(pair.getRight());
        return result;
    }
}
