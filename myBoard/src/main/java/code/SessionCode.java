package code;

import lombok.Data;
import lombok.Getter;

@Getter

public enum SessionCode
{
    USER_NO("userNo"),
    USER_ID("id"),
    USER_PASSWORD("password"),
    USER_NAME("name");

    private String code;

    SessionCode (String field) {
        code = field;
    }

    public String getCode() {
        return this.code;
    }

}
