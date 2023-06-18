package test.interview.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String role;
    private Integer id;
    private Integer playerId;
    private String login;
    private String password;
    private String screenName;
    private String gender;
    private Integer age;
}