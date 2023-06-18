package test.interview.api.asserts;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import test.interview.api.conditions.CheckCondition;
import test.interview.api.conditions.GetCondition;

@Slf4j
public class AssertResponse {
    private Response response;

    public AssertResponse(Response response) {
        this.response = response;
    }

    public AssertResponse responseShouldHave(CheckCondition condition) {
        log.info("Check condition {}", condition);
        condition.check(response);
        return this;
    }

    public String getStringFromVariable(GetCondition condition) {
        log.info("Get condition {}", condition);
        return condition.getString(response);
    }

    public Integer getIntegerFromVariable(GetCondition condition) {
        log.info("Get condition {}", condition);
        return condition.getInteger(response);
    }
}
