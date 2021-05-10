package org.tnh.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.tnh.exceptions.*;
import org.tnh.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tnh.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String firstName, String lastName, String email,String username, String password, String confirmPassword, String role) throws UsernameAlreadyExistsException, UncompletedFieldsException, PasswordNoUpperCaseException, ConfirmPasswordAndPasswordNotEqualException {
        uncompletedFields(firstName, lastName, email,username, password, confirmPassword, role);
        checkUserDoesNotAlreadyExist(username);
        passwordNoUpperCase(password);
        passwordNotEqualConfirmPassowrd(password, confirmPassword);
        userRepository.insert(new User(firstName, lastName, email, username, encodePassword(username, password), confirmPassword, role));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static void uncompletedFields(String firstName, String lastName, String email, String username, String password,String confirmPassword, String role) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher1 = pattern.matcher(firstName);
        Matcher matcher2 = pattern.matcher(lastName);
        Matcher matcher3 = pattern.matcher(email);
        Matcher matcher4= pattern.matcher(username);
        Matcher matcher5=pattern.matcher(password);
        Matcher matcher6=pattern.matcher(confirmPassword);
        Matcher matcher7=pattern.matcher(role);
        boolean matchFound1 = matcher1.find();
        boolean matchFound2 = matcher2.find();
        boolean matchFound3 = matcher3.find();
        boolean matchFound4 = matcher4.find();
        boolean matchFound5 = matcher5.find();
        boolean matchFound6 = matcher6.find();
        boolean matchFound7 = matcher7.find();
        if(!matchFound1 ) throw new UncompletedFieldsException();
        if(!matchFound2 ) throw new UncompletedFieldsException();
        if(!matchFound3 ) throw new UncompletedFieldsException();
        if(!matchFound4 ) throw new UncompletedFieldsException();
        if(!matchFound5 ) throw new UncompletedFieldsException();
        if(!matchFound6) throw new UncompletedFieldsException();
        if(!matchFound7) throw new UncompletedFieldsException();
    }

    public static void passwordNoUpperCase(String password) throws PasswordNoUpperCaseException
    {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.find();
        if(!matchFound) throw new PasswordNoUpperCaseException("The password must have one upper case!");
    }

    public static void passwordNotEqualConfirmPassowrd(String password, String confirmPassword) throws ConfirmPasswordAndPasswordNotEqualException
    {
        if(!password.equals(confirmPassword))
            throw new ConfirmPasswordAndPasswordNotEqualException("The 2 password firlds are not the same!");
    }

    public static void contValid(String username, String password) throws InvalidUsernameException, InvalidPasswordException
    {
        boolean ok1 = false, ok2=false;
        for(User user :userRepository.find())
        {
            if (Objects.equals(username, user.getUsername()))
            {
                ok1 = true;
                if(encodePassword(username,password).equals(user.getPassword()))
                {
                    ok2 = true;
                }
            }
        }
        if(!ok1)
            throw new InvalidUsernameException("Invalid username");
        if(!ok2)
            throw new InvalidPasswordException("Invalid password");
    }

    public static void loginUncompletedFields(String username, String password) throws UncompletedFieldsException {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher4= pattern.matcher(username);
        Matcher matcher5=pattern.matcher(password);
        boolean matchFound4 = matcher4.find();
        boolean matchFound5 = matcher5.find();
        if(!matchFound4 ) throw new UncompletedFieldsException();
        if(!matchFound5 ) throw new UncompletedFieldsException();
    }

    public static String getUserRole(String username)
    {
        String role = "";
        for (User user : userRepository.find())
            if (Objects.equals(username, user.getUsername()))
                role = role + user.getRole();

        return role;
    }

    private static String encodePassword(String salt, String password) {
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
