package dev.blaauwendraad.masker.json;

import com.fasterxml.jackson.databind.JsonNode;
import dev.blaauwendraad.masker.json.config.JsonMaskingConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import randomgen.json.RandomJsonGenerator;
import randomgen.json.RandomJsonGeneratorConfig;

import java.util.Set;

//TODO
@Disabled("Have to implement objecta and array masking first in the Jackson masker")
final class FuzzingTest {
    private static final int SECONDS_FOR_EACH_TEST_TO_RUN = 10;

    @ValueSource(ints = {SECONDS_FOR_EACH_TEST_TO_RUN})
        // duration in seconds the tests runs for
    void fuzzing_NoArrayNoObjectValueMasking(int secondsToRunTest) {
        long startTime = System.currentTimeMillis();
        int randomTestExecuted = 0;
        while (System.currentTimeMillis() < startTime + 10 * 1000) {
            Set<String> targetKeys = Set.of("targetKey1", "targetKey2");
            JsonMasker keyContainsMasker = new KeyContainsMasker(JsonMaskingConfig.custom(
                    targetKeys,
                    JsonMaskingConfig.TargetKeyMode.MASK
            ).build());
            RandomJsonGenerator randomJsonGenerator =
                    new RandomJsonGenerator(RandomJsonGeneratorConfig.builder().createConfig());
            JsonNode randomJsonNode = randomJsonGenerator.createRandomJsonNode();
            String randomJsonNodeString = randomJsonNode.toPrettyString();
            String keyContainsOutput = keyContainsMasker.mask(randomJsonNodeString);
            String jacksonMaskingOutput = ParseAndMaskUtil.mask(
                    randomJsonNode,
                    targetKeys,
                    JsonMaskingConfig.TargetKeyMode.MASK
            ).toPrettyString();
            Assertions.assertEquals(
                    jacksonMaskingOutput,
                    keyContainsOutput,
                    "Failed for input: " + randomJsonNodeString
            );
            randomTestExecuted++;
        }
        System.out.printf(
                "Executed %d randomly generated test scenarios in %d seconds%n",
                randomTestExecuted,
                secondsToRunTest
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {SECONDS_FOR_EACH_TEST_TO_RUN})
        // duration in seconds the tests runs for
    void fuzzing_AllowKeys_NoObjectArrayValuesMasking(int secondsToRunTest) {
        long startTime = System.currentTimeMillis();
        int randomTestExecuted = 0;
        while (System.currentTimeMillis() < startTime + 10 * 1000) {
            Set<String> targetKeys = Set.of("targetKey1", "targetKey2");
            JsonMasker keyContainsMasker = new KeyContainsMasker(JsonMaskingConfig.custom(
                    targetKeys,
                    JsonMaskingConfig.TargetKeyMode.ALLOW
            ).build());
            RandomJsonGenerator randomJsonGenerator =
                    new RandomJsonGenerator(RandomJsonGeneratorConfig.builder().createConfig());
            JsonNode randomJsonNode = randomJsonGenerator.createRandomJsonNode();
            String randomJsonNodeString = randomJsonNode.toPrettyString();
            String keyContainsOutput = keyContainsMasker.mask(randomJsonNodeString);
            String jacksonMaskingOutput = ParseAndMaskUtil.mask(
                    randomJsonNode,
                    targetKeys,
                    JsonMaskingConfig.TargetKeyMode.ALLOW
            ).toPrettyString();
            Assertions.assertEquals(
                    jacksonMaskingOutput,
                    keyContainsOutput,
                    "Failed for input: " + randomJsonNodeString
            );
            randomTestExecuted++;
        }
        System.out.printf(
                "Executed %d randomly generated test scenarios in %d seconds%n",
                randomTestExecuted,
                secondsToRunTest
        );
    }
}
