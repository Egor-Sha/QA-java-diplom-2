package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class User {


    public String email;
    public String password;
    public String name;

    public User(){
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru";
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }


    public User setEmailPass(String email, String password) {
        this.email = email;
        this.password = password;
        return this;
    }

    public static User getWithEmailPassOnly() {
        return new User().setEmailPass(RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru", RandomStringUtils.randomAlphabetic(6));
    }

    public User setEmailName(String email, String name) {
        this.email = email;
        this.name = name;
        return this;
    }

    public static User getWithEmailNameOnly() {
        String randomEmail = RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru";
        return new User().setEmailName(randomEmail, RandomStringUtils.randomAlphabetic(10));
    }


    public User setPassName(String password, String name) {
        this.password = password;
        this.name = name;
        return this;
    }

    public static User getWithPassNameOnly() {
        return new User().setPassName(RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(10));
    }


    public User changeEmail(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        return this;
    }
    public static User getUserWithNewEmail() {
        return new User().changeEmail("newEmail@yandex.ru", RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(10));
    }

}
