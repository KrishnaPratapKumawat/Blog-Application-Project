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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailAuthenticationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public boolean sendEmail(Integer userId, Integer categoryId,PostsDto postsDto) throws Exception {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        PostsDto savePost = postService.createPost(postsDto,userId,categoryId);
        int postId = savePost.getPostId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date dateFormat = savePost.getCurrentDate();
        String postTitle = savePost.getPostTitle();
        String date = simpleDateFormat.format(dateFormat);
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
        Session session = Session.getInstance(new Properties());
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");
            javaMailSender.send(mimeMessage);

        }catch (Exception e){
            throw new Exception("Failed Exception "+email);
        }
        return true;
    }
}

