package test.interview.api.conditions;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

@UtilityClass
public class Conditions {
    public StatusCodeCondition statusCode(int statusCode) {
        return new StatusCodeCondition(statusCode);
    }

    public BodyCondition bodyVariableAndValue(String jsonPath, Matcher matcher) {
        return new BodyCondition(jsonPath, matcher);
    }

    public BodyShortCondition bodyValue(Matcher matcher) {
        return new BodyShortCondition(matcher);
    }

    public BodyGetValueCondition bodyVariable(String jsonPath) {
        return new BodyGetValueCondition(jsonPath);
    }
}
