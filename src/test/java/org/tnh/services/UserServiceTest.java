package org.tnh.services;

import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.tnh.exceptions.*;
import org.tnh.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    private final String FIRST_NAME = "Edward";
    private final String LAST_NAME = "Rosco";
    private final String EMAIL = "edward@yahoo.com";
    private final String USERNAME = "Edward26";
    private final String PASSWORD = "Edw@rd62";
    private final String CONFIRM_PASSWORD = "Edw@rd62";
    private final String ROLE = "Head Chef";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
    }

    @Test
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    void testUserIsAddedToDatabase() throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException, PasswordNoUpperCaseException, UncompletedFieldsException
    {
        UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(USERNAME, PASSWORD));
        assertThat(user.getRole()).isEqualTo(ROLE);
    }

    @Test
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
            UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
        });
    }

    @Test
    void testAllFieldsAreCompleted() {
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser("", LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser(FIRST_NAME, "", EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, "", ROLE));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ""));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, "", PASSWORD, CONFIRM_PASSWORD, ROLE));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser(FIRST_NAME, "", EMAIL, USERNAME, PASSWORD, "", ROLE));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.addUser("", "", "", "", "", "", ""));
    }

    @Test
    void testPasswordHasAtLeastOneUpperCaseLetter() {
        assertThrows(PasswordNoUpperCaseException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, "mere", "mere", ROLE));
    }

    @Test
    void testConfirmPasswordFieldIsTheSameAsPasswordField() {
        assertThrows(ConfirmPasswordAndPasswordNotEqualException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD + 1, CONFIRM_PASSWORD, ROLE));
        assertThrows(ConfirmPasswordAndPasswordNotEqualException.class, () ->
                UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD + 1, ROLE));
    }

    @Test
    void testLoggedUserMethod() throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException, PasswordNoUpperCaseException, UncompletedFieldsException, InvalidPasswordException, InvalidUsernameException {
        UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
        assertThrows(InvalidUsernameException.class, () ->
                UserService.loggedUser(USERNAME + 1, PASSWORD));
        assertThrows(InvalidPasswordException.class, () ->
                UserService.loggedUser(USERNAME, PASSWORD + 1));
        assertThat(UserService.loggedUser(USERNAME, PASSWORD).getUsername()).isEqualTo(USERNAME);
        assertThat(UserService.loggedUser(USERNAME, PASSWORD).getPassword()).isEqualTo(UserService.encodePassword(USERNAME, PASSWORD));
        assertThat(UserService.loggedUser(USERNAME, PASSWORD).getRole()).isEqualTo(ROLE);
    }

    @Test
    void testAllLoginFieldsAreCompleted() {
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.loginUncompletedFields(USERNAME, ""));
        assertThrows(UncompletedFieldsException.class, () ->
                UserService.loginUncompletedFields("", PASSWORD));
    }

    @Test
    void testGetUserRole() throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException, PasswordNoUpperCaseException, UncompletedFieldsException {
        UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
        assertThat(UserService.getUserRole(USERNAME)).isEqualTo(ROLE);
    }

    @Test
    void testEncoding() throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException, PasswordNoUpperCaseException, UncompletedFieldsException {
        UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CONFIRM_PASSWORD, ROLE);
        User user = UserService.getAllUsers().get(0);
        assertThat(UserService.encodePassword(USERNAME, PASSWORD)).isEqualTo(user.getPassword());
    }

}