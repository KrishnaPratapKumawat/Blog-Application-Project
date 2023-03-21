package com.codewithkpk.blog.controller;

import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.ApiResponse;
import com.codewithkpk.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.codewithkpk.blog.payloads.UserDataTransferOption;
import com.codewithkpk.blog.services.UserServices;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    //Add userData
    @PostMapping("/adduser")
    public ResponseEntity<UserDataTransferOption> addUserData(@Valid @RequestBody UserDataTransferOption udto) throws Exception {
        UserDataTransferOption addUserDto = this.userServices.addUserData(udto);
        return new ResponseEntity<>(addUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/getAllUserData")
    public ResponseEntity<List<UserDataTransferOption>> getAllUserData() {

        return ResponseEntity.ok(this.userServices.getAllUserData());
    }

    @GetMapping(value = "/getUserData/{userId}")
    public ResponseEntity<UserDataTransferOption> getUserData(@PathVariable int userId) {

        return ResponseEntity.ok(this.userServices.getUserData(userId));
    }

    @PutMapping("/updateUserData/{userId}")
    public ResponseEntity<UserDataTransferOption> updateUserData(@Valid @RequestBody UserDataTransferOption utdo, @PathVariable int userId) {
        UserDataTransferOption updateUdto = this.userServices.updateUserData(utdo, userId);
        return ResponseEntity.ok(updateUdto);
    }

    @DeleteMapping("/deleteUserData/{userId}")
    public ResponseEntity<ApiResponse> deleteUserData(@PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        boolean userStatus =user.isDeleted() ;
        if (userStatus) {
            this.userServices.deleteUserData(userId);
            return new ResponseEntity(new ApiResponse("user deleted Successfully", true), HttpStatus.OK);
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User Not found with userId : "+userId,false));
        }
    }

    @GetMapping(value = "/login/{email}/{password}")
    public ResponseEntity getUserLogin(@PathVariable String email, @PathVariable String password) {
        boolean ifUserExist = userServices.checkIfUserExist(email);
        if (ifUserExist) {
            User user = this.userRepository.findByEmail(email);
            boolean enabled = user.isEnabled();
            if (enabled) {
                boolean userActive = user.isDeleted();
                if (userActive) {
                    String encodedPassword = user.getPassword();
                    boolean isPasswordMatch = encoder.matches(password, encodedPassword);
                    if (isPasswordMatch) {
                        return ResponseEntity.ok(this.userServices.getUserLogin(user.getEmail(), user.getPassword()));
                    } else {
                        return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body("Password not match");
                    }
                }else {
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse("User is Not Found : ",false));
                }
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Please Verify User Email");
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Enter correct email");
        }
    }
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return this.userServices.confirmEmail(confirmationToken);
    }
}
