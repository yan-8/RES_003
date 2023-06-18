package test.interview.api.services;

import test.interview.api.asserts.AssertResponse;
import test.interview.api.dtos.UserDto;
import test.interview.api.enums.Role;

import java.util.Map;

public class UserService extends ApiService {
    public AssertResponse registerNewUserEntity(Map<String, String> queryParams, String roleName) {
        return new AssertResponse(requestSpecificationSetup()
                    .basePath("/player/create/{editor}")
                    .pathParam("editor", Role.ADMIN.getRoleName())
                    .queryParams(queryParams)
                .when()
                    .get());
    }

    public AssertResponse getAllUserEntities() {
        return new AssertResponse(requestSpecificationSetup()
                    .basePath("/player/get/all")
                .when()
                    .get());
    }

    public AssertResponse getUserEntityById(UserDto userDto) {
        return new AssertResponse(requestSpecificationSetup()
                    .basePath("/player/get")
                    .body(userDto)
                .when()
                    .post());
    }

    public AssertResponse updateUserEntityData(UserDto userDto, String roleName, int id) {
        return new AssertResponse(requestSpecificationSetup()
                    .basePath("/player/update/{editor}/{id}")
                    .pathParam("editor", roleName)
                    .pathParam("id", id)
                    .body(userDto)
                .when()
                    .patch());
    }

    public AssertResponse deleteUserEntity(UserDto userDto, String roleName) {
        return new AssertResponse(requestSpecificationSetup()
                    .basePath("/player/delete/{editor}")
                    .pathParam("editor", roleName)
                    .body(userDto)
                .when()
                    .delete());
    }
}
