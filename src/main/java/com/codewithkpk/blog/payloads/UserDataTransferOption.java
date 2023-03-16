package com.codewithkpk.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDataTransferOption {
    private int userId;

    @NotEmpty
    @Size(min = 4,message = "Please enter Name must be minimum of 4 character")
    private String userName;
    @Email(message = "Email format are not valid !! Please Enter a proper format")
    private String email;
    @NotEmpty
    @Size(min = 8,max = 15,message = "password should be minimum 8-15 uppercase lowercase special character number.Ex:-Jhon@123")
    @Pattern(message = "Ex:-Jhon@123",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    private String about;

}
