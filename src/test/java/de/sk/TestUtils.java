package de.sk;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * TODO
 */
public final class TestUtils {

    private static final String TEST_RESOURCES_DIR = "src/test/resources";

    private TestUtils() {
        // only utility methods
    }

    /**
     * TODO
     * @param filename
     * @return
     * @throws IOException
     */
    public static int @NotNull [] readIntsFromResourceFile(@NotNull String filename) throws IOException {
        Path resDir = Path.of(StringUtils.EMPTY, TEST_RESOURCES_DIR);
        Path file = resDir.resolve(filename);
        String content = Files.readString(file);
        return Arrays.stream(content.split(System.lineSeparator())).mapToInt(Integer::valueOf).toArray();
    }

    /**
     * TODO
     * @param pair
     * @return
     * @param <T>
     */
    public static <T> List<T> convertPairToList(@NotNull Pair<T, T> pair) {
        List<T> result = new ArrayList<>();
        result.add(pair.getLeft());
        result.add(pair.getRight());
        return result;
    }

    /**
     * TODO
     * @param numberOfMocks
     * @param clazz
     * @return
     * @param <T>
     */
    public static @NotNull <T> List<T> createMocks(int numberOfMocks, @NotNull Class<T> clazz) {
        List<T> mocks = new ArrayList<>();
        for (int i = 0; i < numberOfMocks; i++) {
            T jobMock = mock(clazz);
            mocks.add(jobMock);
        }
        return mocks;
    }
}
