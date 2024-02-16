package dev.blaauwendraad.masker.json;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

final class MaskObjectInNonMaskedArrayTest {
    @ParameterizedTest
    @MethodSource("objectInNonMaskedArrayValues")
    void maskObjectInNonMaskedArray(JsonMaskerTestInstance testInstance) {
        assertThat(testInstance.jsonMasker().mask(testInstance.input())).isEqualTo(testInstance.expectedOutput());
    }

    private static Stream<JsonMaskerTestInstance> objectInNonMaskedArrayValues() throws IOException {
        return JsonMaskerTestUtil.getJsonMaskerTestInstancesFromFile("test-object-in-non-masked-array.json").stream();
    }
}
