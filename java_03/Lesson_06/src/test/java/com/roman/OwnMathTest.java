package com.roman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

public class OwnMathTest {

    @ParameterizedTest
    @MethodSource("maxParametersProvider")
    void shouldReturnMaxValueFromTwoPassed(int expected, int a, int b){
        Assertions.assertEquals(expected, OwnMath.max(a,b));
    }

    private static Stream<Arguments> maxParametersProvider(){

        return  Stream.of(
                Arguments.arguments(2,1,2),
                Arguments.arguments(1,1,1),
                Arguments.arguments(0,0,0),
                Arguments.arguments(-1,-1,-1),
                Arguments.arguments(1,1,-1),
                Arguments.arguments(1,-1,1)
        );

    }


    @Test
    void shouldReturnMinValueFromTwoPassed(){
        Assertions.assertEquals(1,  OwnMath.min(1,2));
    }

    @Test
    void shuldThrowIllegalArqumentExceptionWhenZeroPassedDivider(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> OwnMath.aDouble(4,0));

    }

    @ParameterizedTest
    @MethodSource("shouldReturnDividedValue")
    void shouldReturnDividedValue(double expected, double a, double b){
        Assertions.assertEquals(expected, OwnMath.aDouble(a,b));
    }

    private static Stream<Arguments> shouldReturnDividedValue(){

        return  Stream.of(
                Arguments.arguments(0.5,1,2),
                Arguments.arguments(1,1,1)
        );

    }

    @Test
    void shouldDurationNotMoreTwoSeconds(){
        Assertions.assertTimeout(Duration.ofSeconds(2), ()-> OwnMath.someMethod());
    }

}
