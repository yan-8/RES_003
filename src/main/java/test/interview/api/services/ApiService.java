package test.interview.api.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import test.interview.api.configs.ProjectConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiService {
    public RequestSpecification requestSpecificationSetup() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFilters(getRequestResponseLogging())
                .setContentType(ContentType.JSON).build();
        return RestAssured.given().spec(requestSpecification);
    }

    private List<Filter> getRequestResponseLogging() {
//        Boolean isAble = Boolean.valueOf(System.getProperty("logging", "true"));
        ProjectConfig projectConfig = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        if (projectConfig.requestResponseLogging()) {
            return Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            return Collections.emptyList();
        }
    }
}
