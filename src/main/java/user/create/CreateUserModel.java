package user.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserModel {

    private String email;

    private String password;

    private String name;

    public void generateNewUser() {

        int randomValue = 10000000 + (int) (Math.random() * (99999999 - 10000000 + 1));

        email = String.format("email%s@yandex.ru", randomValue);

        password = String.format("%s", randomValue);

        name = String.format("John%s", randomValue);
    }

}
