package model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;

    private String password;

    private String name;

    public User(int seed) {
        int randomValue = 10000000 + seed + (int) (Math.random() * (99999999 + seed - 10000000 + 1));

        email = String.format("email%s@yandex.ru", randomValue);

        password = String.format("%s", randomValue);

        name = String.format("John%s", randomValue);
    }

}
