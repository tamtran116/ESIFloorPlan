package com.tamtran.myreceipt.common.model;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Created by Tam Tran on 9/5/2015.
 */
public class UserRegisterRequest {

    @NotNull
    @Size(min=2, max=20)
    private String firstName;

    @NotNull
    @Size(min=2, max=20)
    private String lastName;

    @NotNull
    @Size(max=10)
    private String phoneNumber;

    @NotNull
    private String email;

    @NotNull
    @Size(max=10)
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String recaptcha;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(String recaptcha) {
        this.recaptcha = recaptcha;
    }

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "firstName='" + firstName + '\n' +
                ", lastName='" + lastName + '\n' +
                ", phoneNumber='" + phoneNumber + '\n' +
                ", email='" + email + '\n' +
                ", userName='" + userName + '\n' +
                '}';
    }
}
