package TeachingPlanner.DailyPlanner.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Periods {
    PERIODO_1,
    PERIODO_2,
    PERIODO_3,
    PERIODO_4,
    PERIODO_5;


    @com.fasterxml.jackson.annotation.JsonCreator
    public static Periods fromString(String value) {
        return Periods.valueOf(value.toUpperCase());
    }


    @JsonValue
    public String toValue() {
        return name();
    }
}
