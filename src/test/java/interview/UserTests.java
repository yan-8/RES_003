package interview;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.interview.api.configs.ProjectConfig;
import test.interview.api.dtos.UserDto;
import test.interview.api.enums.Gender;
import test.interview.api.enums.Role;
import test.interview.api.services.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static test.interview.api.conditions.Conditions.statusCode;
import static test.interview.api.conditions.Conditions.bodyVariableAndValue;
import static test.interview.api.conditions.Conditions.bodyVariable;
import static test.interview.api.conditions.Conditions.bodyValue;

@Slf4j
public class UserTests {
    private final UserService app = new UserService();

    @BeforeClass
    public void beforeTests() {
        ProjectConfig projectConfig = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = projectConfig.baseUrl();
    }

    @Test(description = "GET /player/create/{editor}?...}, as Admin role user I want to create new user with User role")
    public void createEntityWithRoleOfUserAsAdminUser() {
        String preLogin = generateString(2);
        String name = generateString(2);
        String postLogin = generateString(2);
        String userLogin = preLogin + name + postLogin;
        String userScreenName = generateString(2) + preLogin + postLogin;
        String userPassword = name + name + generateInteger(2);

        log.info("Preconditions steps");
        UserDto userDto = new UserDto(Role.USER.getRoleName(), null, null, userLogin, userPassword, userScreenName, Gender.MALE.getGenderName(), 18);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("login", userDto.getLogin());
        queryParams.put("screenName", userDto.getScreenName());
        queryParams.put("gender", userDto.getGender());
        queryParams.put("age", userDto.getAge().toString());
        queryParams.put("password", userDto.getPassword());
        queryParams.put("role", userDto.getRole());

        log.info("Steps");
        app.registerNewUserEntity(queryParams, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("id", greaterThan(0)))
                .responseShouldHave(bodyVariableAndValue("login", equalTo(userDto.getLogin())));
    }

    @Test(description = "GET /player/create/{editor}?..., as Admin role user I want to create new user with User role immediately after creating previous user}")
    public void createEntityWithRoleOfUserAsAdminUserTwice_NEGATIVE() {
    }

    @Test(description = "GET /player/get/all")
    public void getListOfAllUserEntities() {
        log.info("Preconditions steps");
        String preLogin = generateString(2);
        String name = generateString(2);
        String postLogin = generateString(2);
        String userLogin = preLogin + name + postLogin;
        String userScreenName = generateString(2) + preLogin + postLogin;
        String userPassword = name + name + generateInteger(2);

        UserDto userDto = new UserDto(Role.USER.getRoleName(), null, null, userLogin, userPassword, userScreenName, Gender.MALE.getGenderName(), 18);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("login", userDto.getLogin());
        queryParams.put("screenName", userDto.getScreenName());
        queryParams.put("gender", userDto.getGender());
        queryParams.put("age", userDto.getAge().toString());
        queryParams.put("password", userDto.getPassword());
        queryParams.put("role", userDto.getRole());

        int id = app.registerNewUserEntity(queryParams, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("login", equalTo(userLogin)))
                .getIntegerFromVariable(bodyVariable("id"));

        log.info("Steps");
        app.getAllUserEntities()
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("players.findAll {it.id == " + id + "}.screenName.sum()", equalTo(userScreenName)));
    }

    @Test(description = "GET /player/get/ALL, what if I change endpoint letters on capitals")
    public void getListOfAllUserEntitiesWherePartOfEndpointHaveCapitalLetters_NEGATIVE() {
    }

    @Test(description = "POST /player/get")
    public void getUserEntityByUserPlayerId() {
        log.info("Preconditions steps - Create new user");
        String preLogin = generateString(2);
        String name = generateString(2);
        String postLogin = generateString(2);
        String userLogin = preLogin + name + postLogin;
        String userScreenName = generateString(2) + preLogin + postLogin;
        String userPassword = name + name + generateInteger(2);

        UserDto userDto = new UserDto(Role.USER.getRoleName(), null, null, userLogin, userPassword, userScreenName, Gender.MALE.getGenderName(), 18);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("login", userDto.getLogin());
        queryParams.put("screenName", userDto.getScreenName());
        queryParams.put("gender", userDto.getGender());
        queryParams.put("age", userDto.getAge().toString());
        queryParams.put("password", userDto.getPassword());
        queryParams.put("role", userDto.getRole());

        log.info("Steps - Get user ID after user creation");
        int id = app.registerNewUserEntity(queryParams, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(200))
                .getIntegerFromVariable(bodyVariable("id"));

        UserDto userDto2 = new UserDto();
        userDto2.setPlayerId(id);

        log.info("Steps - Get all user's data by user ID");
        app.getUserEntityById(userDto2)
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("id", equalTo(userDto2.getPlayerId())))
                .responseShouldHave(bodyVariableAndValue("login", equalTo(userDto.getLogin())))
                .responseShouldHave(bodyVariableAndValue("password", equalTo(userDto.getPassword())))
                .responseShouldHave(bodyVariableAndValue("role", equalTo(userDto.getRole())));
    }

    @Test(description = "POST /player/get")
    public void getUserEntityByUserId_NEGATIVE() {
    }

    @Test(description = "PATCH /player/update/{editor}/{id}, as Admin role user I want to update data of user with User role")
    public void updateUserEntityDataWithRoleOfUserAsAdminRoleUser() {
        log.info("Preconditions steps - Create new user");
        String preLogin = generateString(2);
        String name = generateString(2);
        String postLogin = generateString(2);
        String userLogin = preLogin + name + postLogin;
        String userScreenName = generateString(2) + preLogin + postLogin;
        String userPassword = name + name + generateInteger(2);

        UserDto userDto = new UserDto(Role.USER.getRoleName(), null, null, userLogin, userPassword, userScreenName, Gender.MALE.getGenderName(), 18);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("login", userDto.getLogin());
        queryParams.put("screenName", userDto.getScreenName());
        queryParams.put("gender", userDto.getGender());
        queryParams.put("age", userDto.getAge().toString());
        queryParams.put("password", userDto.getPassword());
        queryParams.put("role", userDto.getRole());

        log.info("Steps - Get user ID after user creation");
        int id = app.registerNewUserEntity(queryParams, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(200))
                .getIntegerFromVariable(bodyVariable("id"));

        String newUserPassword = generateInteger(8);
        int newUserAge = 0;
        String femaleGender = Gender.FEMALE.getGenderName();

        UserDto userDto2 = new UserDto();
        userDto2.setPlayerId(id);

        UserDto userRemovingDto = new UserDto(userDto.getRole(), null, null, null, newUserPassword, userDto.getScreenName(), femaleGender, newUserAge);

        log.info("Steps - Update user entity in the system");
        app.updateUserEntityData(userRemovingDto, Role.ADMIN.getRoleName(), userDto2.getPlayerId())
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("id", equalTo(userDto2.getPlayerId())))
                .responseShouldHave(bodyVariableAndValue("gender", equalTo(femaleGender)))
                .responseShouldHave(bodyVariableAndValue("age", equalTo(newUserAge)));

        log.info("Steps - Check updated user entity");
        app.getUserEntityById(userDto2)
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("id", equalTo(userDto2.getPlayerId())))
                .responseShouldHave(bodyVariableAndValue("password", equalTo(newUserPassword)))
                .responseShouldHave(bodyVariableAndValue("gender", equalTo(femaleGender)))
                .responseShouldHave(bodyVariableAndValue("age", equalTo(newUserAge)));
    }

    @Test(description = "PATCH /player/update/{editor}/{id}, as Admin role user I want to update data of user with User role more than one time")
    public void updateUserEntityData_NEGATIVE() {}

    @Test(description = "DELETE /player/delete/{editor}")
    public void deleteUserEntityByUserId() {
        log.info("Preconditions steps - Create new user in the system");
        String preLogin = generateString(2);
        String name = generateString(2);
        String postLogin = generateString(2);
        String userLogin = preLogin + name + postLogin;
        String userScreenName = generateString(2) + preLogin + postLogin;
        String userPassword = name + name + generateInteger(2);

        UserDto userDto = new UserDto(Role.USER.getRoleName(), null, null, userLogin, userPassword, userScreenName, Gender.MALE.getGenderName(), 18);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("login", userDto.getLogin());
        queryParams.put("screenName", userDto.getScreenName());
        queryParams.put("gender", userDto.getGender());
        queryParams.put("age", userDto.getAge().toString());
        queryParams.put("password", userDto.getPassword());
        queryParams.put("role", userDto.getRole());

        log.info("Preconditions steps - Get user ID after user creation");
        int id = app.registerNewUserEntity(queryParams, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(200))
                .getIntegerFromVariable(bodyVariable("id"));

        UserDto userDto2 = new UserDto();
        userDto2.setPlayerId(id);

        log.info("Steps - Delete user entity from the system");
        app.deleteUserEntity(userDto2, Role.ADMIN.getRoleName())
                .responseShouldHave(statusCode(204))
                .responseShouldHave(bodyValue(emptyString()));

        log.info("Steps - Check user entity was deleted");
        app.getAllUserEntities()
                .responseShouldHave(statusCode(200))
                .responseShouldHave(bodyVariableAndValue("players.id", not(hasItem(equalTo(userDto2.getPlayerId())))));
    }

    @Test(description = "DELETE /player/delete/{editor}, ")
    public void deleteUserEntityByNonExistentUserId_NEGATIVE() {}

    private String generateString(int numberOfCharacters) {
        return RandomStringUtils.random(numberOfCharacters, true, false);
    }

    private String generateInteger(int numberOfCharacters) {
        return RandomStringUtils.random(numberOfCharacters, false, true);
    }
}
