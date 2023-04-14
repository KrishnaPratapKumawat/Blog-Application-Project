package com.codewithkpk.blog.services.impl;

import com.codewithkpk.blog.entity.ConfirmationToken;
import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.UserDataTransferOption;
import com.codewithkpk.blog.repo.ConfirmationTokenRepository;
import com.codewithkpk.blog.repo.UserRepository;
import com.codewithkpk.blog.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserEmailVerificationImpl userEmailVerification;

    @Override
    public UserDataTransferOption addUserData(UserDataTransferOption udto) throws Exception {
        User user = this.dtoToUser(udto);
        if(checkIfUserExist(user.getEmail())){
            throw new ResourceNotFoundException("User already exists","Email",1);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setDeleted(true);
        User savedUser = this.userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        Session session = Session.getInstance(new Properties());
        String message = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + udto.getUserName() + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"https://localhost:9091/user/confirm-account?token="+ confirmationToken.getConfirmationToken()+"\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setSubject("Confirmation Valid Email");
            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");

        userEmailVerification.sendEmail(mimeMessage);
        System.out.println("confirmation token : "+confirmationToken.getConfirmationToken());
        return this.userToudto(savedUser);
        }catch (Exception e){
            throw new Exception("Failed Exception "+udto.getEmail());
        }
    }


       @Override
    public UserDataTransferOption updateUserData(UserDataTransferOption udto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        user.setUserName(udto.getUserName());
        user.setEmail(udto.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAbout(udto.getAbout());
        final User updateUsr = this.userRepository.save(user);
        return this.userToudto(updateUsr);

    }

    @Override
    public UserDataTransferOption getUserData(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.userToudto(user);
    }

    @Override
    public List<UserDataTransferOption> getAllUserData() {
        List<User> users = this.userRepository.findAll();
        List<UserDataTransferOption> userDtos = users.stream().map(user -> this.userToudto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUserData(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        user.setDeleted(false);
        this.userRepository.save(user);

    }
    @Override
    public UserDataTransferOption getUserLogin(String email,String password) {
        User user = this.userRepository.findByEmail(email);
        if (user.getEmail().equals(email)) {
            return this.userToudto(user);
        }else {
            return null;
        }
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email)!=null ? true : false;
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token!=null){
            User user = userRepository.findByEmail(token.getUserEntity().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
        return ResponseEntity.ok("   <link href=\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        text-align: center;\n" +
                "        padding: 40px 0;\n" +
                "        background: #EBF0F5;\n" +
                "      }\n" +
                "        h1 {\n" +
                "          color: #88B04B;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-weight: 900;\n" +
                "          font-size: 40px;\n" +
                "          margin-bottom: 10px;\n" +
                "        }\n" +
                "        p {\n" +
                "          color: #404F5E;\n" +
                "          font-family: \"Nunito Sans\", \"Helvetica Neue\", sans-serif;\n" +
                "          font-size:20px;\n" +
                "          margin: 0;\n" +
                "        }\n" +
                "      i {\n" +
                "        color: #9ABC66;\n" +
                "        font-size: 100px;\n" +
                "        line-height: 200px;\n" +
                "        margin-left:-15px;\n" +
                "      }\n" +
                "      .card {\n" +
                "        background: white;\n" +
                "        padding: 60px;\n" +
                "        border-radius: 4px;\n" +
                "        box-shadow: 0 2px 3px #C8D0D8;\n" +
                "        display: inline-block;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "    <body>\n" +
                "      <div class=\"card\">\n" +
                "      <div style=\"border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;\">\n" +
                "        <i class=\"checkmark\">✓</i>\n" +
                "      </div>\n" +
                "        <h1>Success</h1> \n" +
                "        <p>We received your purchase request;<br/> we'll be in touch shortly!</p>\n" +
                         "<h5>Please click on the link :<a href=\" http://localhost:4200/\">Click-here</a>\n</h5>"+
                "      </div>\n" +
                "    </body>");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }


    public UserDataTransferOption userToudto(User user) {
        UserDataTransferOption userDto = this.modelMapper.map(user,UserDataTransferOption.class);
        return userDto;
    }

    public User dtoToUser(UserDataTransferOption udto) {
        User user = this.modelMapper.map(udto, User.class);
        return user;

    }

}
