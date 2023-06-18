package test.interview.api.conditions;

import io.restassured.response.Response;

public interface CheckCondition {
    void check(Response response);
}
