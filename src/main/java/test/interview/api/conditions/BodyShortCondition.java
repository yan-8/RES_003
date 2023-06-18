package test.interview.api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;

@RequiredArgsConstructor
public class BodyShortCondition implements CheckCondition {
    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().body(matcher);
    }

    @Override
    public String toString() {
        return "body field [" + matcher + "]";
    }
}
