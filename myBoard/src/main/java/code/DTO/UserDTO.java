package code.DTO;

import code.Domain.User.UserEntity;
import lombok.*;
import org.apache.catalina.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class UserDTO
{
    private int userNo;

    @Size(min = 6, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String id;

    @Size(min = 6, max = 25)
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @Size(min = 2, max = 25)
    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;

    public UserEntity toEntity()
    {
        return new UserEntity().builder()
                .userNo(userNo)
                .id(id)
                .name(name)
                .password(password)
                .build();

    }

}
