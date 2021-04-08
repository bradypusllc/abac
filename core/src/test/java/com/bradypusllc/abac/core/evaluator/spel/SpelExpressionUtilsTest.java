package com.bradypusllc.abac.core.evaluator.spel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Slf4j
class SpelExpressionUtilsTest {

    private static Stream<Arguments> conditionProvider() {
        return Stream.of(
                Arguments.of("action == FOOBAR", true),
                Arguments.of("FOOBAR == action", true),
                Arguments.of("action == action", true),
                Arguments.of("FOOBAR == FOOBAR", false),
                Arguments.of("username == 'bob'", false),
                Arguments.of("true", false)
        );
    }

    @DisplayName("Does the SpEL AST contain a field or property 'action'?")
    @ParameterizedTest(name = "Condition \"{0}\" contains 'action' -> {1}")
    @MethodSource("conditionProvider")
    void test_isActionCondition(String condition, boolean expected) {
        Assertions.assertEquals(expected, SpelExpressionUtils.isActionCondition(condition));
    }
}
