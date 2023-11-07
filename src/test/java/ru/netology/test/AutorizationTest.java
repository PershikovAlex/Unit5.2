package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AutorizationTest {
    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
    }

    @Test
    void activeStatusRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void blockedStatusRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    void randomLoginRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id=password] .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void randomPasswordRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void emptyLoginRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val();
        $("[data-test-id=password] .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyPasswordRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyLoginAndPasswordRegisteredUserTest() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__control").val();
        $("[data-test-id=password] .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        $("[data-test-id=password].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void randomLoginUnregisteredUserTest() {
        var invalidUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id=password] .input__box .input__control").val(invalidUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void randomPasswordUnregisteredUserTest() {
        var invalidUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] .input__box .input__control").val(invalidUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
