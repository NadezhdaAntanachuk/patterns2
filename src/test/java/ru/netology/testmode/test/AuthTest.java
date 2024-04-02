package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.selector.ByDeepShadow.cssSelector;

import static ru.netology.testmode.data.DataGenerator.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @MethodOrderer.DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $(cssSelector("[data-test-id='login'] input")).setValue(registeredUser.getLogin());
        $(cssSelector("[data-test-id='password'] input")).setValue(registeredUser.getPassword());
        $(cssSelector("button.button")).click();
        $(cssSelector("h2")).shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $(cssSelector("[data-test-id='login'] input")).setValue(notRegisteredUser.getLogin());
        $(cssSelector("[data-test-id='password'] input")).setValue(notRegisteredUser.getPassword());
        $(cssSelector("button.button")).click();
        $(cssSelector("[data-test-id='error-notification'] .notification__content"))
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль."), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $(cssSelector("[data-test-id='login'] input")).setValue(wrongLogin);
        $(cssSelector("[data-test-id='password'] input")).setValue(registeredUser.getPassword());
        $(cssSelector("button.button")).click();
        $(cssSelector("[data-test-id='error-notification'] .notification__content"))
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $(cssSelector("[data-test-id='login'] input")).setValue(registeredUser.getLogin());
        $(cssSelector("[data-test-id='password'] input")).setValue(wrongPassword);
        $(cssSelector("button.button")).click();
        $(cssSelector("[data-test-id='error-notification'] .notification__content"))
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
}