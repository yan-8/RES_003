package test.interview.api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements CheckCondition {
    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then().statusCode(statusCode);
    }

    @Override
    public String toString() {
        return "status code is [" + statusCode + "]";
    }
}
