package test.interview.api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BodyGetValueCondition implements GetCondition {
    private final String jsonPath;

    @Override
    public String getString(Response response) {
        return response.then().extract().jsonPath().getString(jsonPath);
    }

    @Override
    public Integer getInteger(Response response) {
        return response.then().extract().jsonPath().getInt(jsonPath);
    }

    @Override
    public String toString() {
        return "body field [" + jsonPath + "] ";
    }
}
