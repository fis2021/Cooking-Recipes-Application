package org.tnh.services;

import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.tnh.exceptions.ConfirmPasswordAndPasswordNotEqualException;
import org.tnh.exceptions.PasswordNoUpperCaseException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.exceptions.UsernameAlreadyExistsException;
import org.tnh.model.User;

import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    private String FIRST_NAME = "Edward";
    private String LAST_NAME = "Rosco";
    private String EMAIL = "edward@yahoo.com";
    private String USERNAME = "Edward26";
    private String PASSWORD = "Edw@rd62";
    private String CONFIRM_PASSWORD = "Edw@rd62";
    private String ROLE = "Head Chef";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Test
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    void testUserIsAddedToDatabase() throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException,
                                            PasswordNoUpperCaseException, UncompletedFieldsException
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

}