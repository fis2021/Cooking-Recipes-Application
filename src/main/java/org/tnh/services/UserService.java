package org.tnh.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.tnh.exceptions.*;
import org.tnh.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.tnh.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;
    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(getPathToFile("users.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void closeDatabase() {
        database.close();
    }

    public static void addUser(String firstName, String lastName, String email,String username, String password, String confirmPassword, String role)
            throws UsernameAlreadyExistsException, UncompletedFieldsException, PasswordNoUpperCaseException, ConfirmPasswordAndPasswordNotEqualException, FirstNameIsNotUniqueException
    {
        uncompletedFields(firstName, lastName, email,username, password, confirmPassword, role);
        checkFirstNameIsNotUnique(firstName);
        checkUserDoesNotAlreadyExist(username);
        passwordNoUpperCase(password);
        passwordNotEqualConfirmPassword(password, confirmPassword);
        userRepository.insert(new User(firstName, lastName, email, username, encodePassword(username, password), role));
    }

    public static List<User> getAllUsers() {
        return userRepository.find().toList();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static void checkFirstNameIsNotUnique(String firstName) throws FirstNameIsNotUniqueException {
        for (User user : userRepository.find()) {
            if (Objects.equals(firstName, user.getFirstName()))
                throw new FirstNameIsNotUniqueException();
        }
    }

    public static void uncompletedFields(String firstName, String lastName, String email, String username, String password,String confirmPassword, String role) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        if (!pattern.matcher(firstName).find()
            || !pattern.matcher(lastName).find()
            || !pattern.matcher(email).find()
            || !pattern.matcher(username).find()
            || !pattern.matcher(password).find()
            || !pattern.matcher(confirmPassword).find()
            || !pattern.matcher(role).find())
                throw new UncompletedFieldsException();
    }

    public static void passwordNoUpperCase(String password) throws PasswordNoUpperCaseException
    {
        Pattern pattern = Pattern.compile("[A-Z]");
        if(!pattern.matcher(password).find()) throw new PasswordNoUpperCaseException("The password must have one upper case!");
    }

    public static void passwordNotEqualConfirmPassword(String password, String confirmPassword) throws ConfirmPasswordAndPasswordNotEqualException
    {
        if(!password.equals(confirmPassword))
            throw new ConfirmPasswordAndPasswordNotEqualException("The 2 password fields are not the same!");
    }


    public static User loggedUser(String username,String password) throws InvalidUsernameException, InvalidPasswordException {
        int ok = 0;
        for(User user : userRepository.find())
        {
            if (Objects.equals(username, user.getUsername()))
            {   ok = 1;
                if (encodePassword(username, password).equals(user.getPassword())) {
                    return user;
                }
            }
        }
        if (ok == 0)    throw new InvalidUsernameException("Invalid username");
        else            throw new InvalidPasswordException("Invalid password");
    }

    public static void loginUncompletedFields(String username, String password) throws UncompletedFieldsException {
        Pattern pattern = Pattern.compile("[\\S+]");
        if(!pattern.matcher(username).find()) throw new UncompletedFieldsException();
        if(!pattern.matcher(password).find()) throw new UncompletedFieldsException();
    }

    public static String getUserRole(String username)
    {
        StringBuilder role = new StringBuilder();
        for (User user : userRepository.find())
            if (Objects.equals(username, user.getUsername()))
                role.append(user.getRole());

        return role.toString();
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }


}
