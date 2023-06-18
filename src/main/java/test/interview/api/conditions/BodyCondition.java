package test.interview.api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

@RequiredArgsConstructor
public class BodyCondition implements CheckCondition {
    private final String jsonPath;
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().body(jsonPath, matcher);
    }

    @Override
    public String toString() {
        return "body field [" + jsonPath + "] is " + matcher;
    }
}
