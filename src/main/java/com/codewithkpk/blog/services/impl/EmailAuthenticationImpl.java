package com.codewithkpk.blog.services.impl;

import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.PostsDto;
import com.codewithkpk.blog.repo.CategoryRepo;
import com.codewithkpk.blog.repo.UserRepository;
import com.codewithkpk.blog.services.EmailAuthentication;
import com.codewithkpk.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
@Service
public class EmailAuthenticationImpl implements EmailAuthentication {
   @Autowired
    private final UserRepository userRepository;
   @Autowired
   private  CategoryRepo categoryRepo;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    public EmailAuthenticationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public boolean sendEmail(Integer userId, Integer categoryId,PostsDto postsDto) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        PostsDto savePost = postService.createPost(postsDto,userId,categoryId);
        int postId = savePost.getPostId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date dateFormat = savePost.getCurrentDate();
        String postTitle = savePost.getPostTitle();
        String date = simpleDateFormat.format(dateFormat);
        String host = "smtp.gmail.com";
        String name = user.getUserName();
        String countPost = String.valueOf(user.getPosts().size() + 1);
        String email = user.getEmail();
        String  message ="Hello User,\n"+ "<html><head>"
                + "<title></title>"
                + "</head>"+"<LINK REL='stylesheet' HREF='stylesheet/fac_css.css' TYPE='text/css'>"
                + "<body>"
                +"<table width='900' cellpadding='0' cellspacing='0' border='0'>"
                +"<td height='5'></td></tr>"
                +"<tr><td></td></tr>"
                +"<tr><td height='5'></td></tr>"
                +"<tr><td><table border='1' width='800' cellpadding='2' cellspacing='1'  style='border-collapse: collapse' bordercolor='#EBDA2A' align='left'>"
                +"<tr class='centerheading' align='left'>"
                +"<td width='30'><b>User Id</b></td>"
                +"<td width='35'><b>User Name</b></td>"
                +"<td width='30'><b>Post title</b></td>"
                +"<td width='30'><b>Date</b></td>"
                +"<td width='30'><b>PostNumber</b></td>"
                + "</tr>"
                +"<tr>"
                +"<td width='30'>"+user.getUserId()+"</b></td>"
                +"<td width='35'>"+user.getUserName()+"</td>"
                +"<td width='30'>"+postTitle+"</td>"
                +"<td width='30'>"+date+"</td>"
                +"<td width='30'>"+countPost+"</td>"
                +"</tr>"
                +"</table>"
                +"</body></html>";
        String subject = "#"+postId+":"+"Post Details:";
        String to = email;
        String from = "krishnakumawat@shrinesoft.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", true);
        final String[] userName = {" "};
        final String[] passWord = {" "};
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                 ResourceBundle resourceBundle;
                resourceBundle = ResourceBundle.getBundle("Email");
                userName[0] = resourceBundle.getString("username");
                passWord[0] = resourceBundle.getString("password");
                return new PasswordAuthentication(userName[0],passWord[0]);
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Transport.send(mimeMessage);
            System.out.println("sent Successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
}
