package me.salisuwy.StressTest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ReferenceType {
    @JsonProperty("payment")
    PAYMENT,
    @JsonProperty("account_movement")
    ACCOUNT_MOVEMENT,
    @JsonProperty("ccb")
    CCB,
    @JsonProperty("ccc")
    CCC,
    @JsonProperty("case_resolution")
    CASE_RESOLUTION;


    private static final Map<String, ReferenceType> FORMAT_MAP = Stream
            .of(ReferenceType.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), Function.identity()));

    /**
     * Provides a ReferenceType form a String
     *
     * @param string the reference type name
     * @return a ReferenceType if the string matches one of the types, throws a EnumParsingException otherwise
     */
    @JsonCreator
    public static ReferenceType fromString(String string) {
        ReferenceType result = FORMAT_MAP.getOrDefault(string.toLowerCase(), null);

        return result;
    }
}
