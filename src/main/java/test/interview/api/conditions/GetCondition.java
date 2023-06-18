package test.interview.api.conditions;

import io.restassured.response.Response;

public interface GetCondition {
    String getString(Response response);
    Integer getInteger(Response response);
}
