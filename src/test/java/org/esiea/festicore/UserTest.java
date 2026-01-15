package org.esiea.festicore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test User Class --- CODE T-USER-XX")
public class UserTest {

    @Test
    @DisplayName("Test T-USER-01")
    void createUserTest() {
        String name = "Bob";
        String email = "bob@example.com";
        String phone = "0634567890";
        String password = "test";

        User user = User.register(name, email, phone, password);
        assertNotNull(user,"User should not be null after registration");
        assertEquals(name, user.getName(), "User name should match the registered name");
        assertEquals(email, user.getEmail(), "User email should match the registered email");
        assertEquals(phone, user.getPhone(), "User phone should match the registered phone");
        assertEquals(password, user.getPassword(), "User password should match the registered password");
        assertTrue(user.getId().startsWith("USER"), "User ID should start with 'USER'");
    }

    @Test
    @DisplayName("Test T-USER-02")
    void verifyPhoneNumberTest() {
        String name = "Bob";
        String email = "bob@example.com";
        String phone = "1234567890";
        String password = "test";

        assertThrows(IllegalArgumentException.class, () -> User.register(name, email, phone, password), "Invalid phone number should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Test T-USER-03")
    void verifyEmailTest() {
        String name = "Bob";
        String email = "bobexample.com";
        String phone = "0634567890";
        String password = "test";

        assertThrows(IllegalArgumentException.class, () -> User.register(name, email, phone, password), "Invalid email should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Test T-USER-04")
    void userLoginTest() {
        String email = "bob@example.com";
        String password = "test";

        User user = User.register("Bob", email, "0634567890", password);
        boolean loginResult = user.login(email, password);
        assertTrue(loginResult, "User should be able to login with correct credentials");
    }

    @Test
    @DisplayName("Test T-USER-05")
    void userLoginFalsePasswordTest() {
        String email = "bob@example.com";
        String password = "test";
        String wrongPassword = "wrong";

        User user = User.register("Bob", email, "0634567890", password);
        boolean loginResult = user.login(email, wrongPassword);
        assertFalse(loginResult, "User should not be able to login with incorrect credentials");
    }

    @Test
    @DisplayName("Test T-USER-06")
    void userLoginFalseEmailTest() {
        String email = "bob@example.com";
        String wrongEmail = "alice@example.com";
        String password = "test"; 
        
        User user = User.register("Bob", email, "0634567890", password);
        boolean loginResult = user.login(wrongEmail, password);
        assertFalse(loginResult, "User should not be able to login with incorrect email");
    }
}