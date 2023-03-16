package com.codewithkpk.blog;

import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest(){
        User user = User.builder()
                .userId(1)
                .userName("test")
                .email("test@gmail.com")
                .password("test@123")
                .about("testing")
                .build();
        userRepository.save(user);
        Assertions.assertThat(user.getUserId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public void getUserTest(){
        List<User>users = userRepository.findAll();
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    @Rollback(value = true)
    public void updateUserTest(){
        User user =userRepository.findById(1).get();
        user.setUserName("test");
        User userUpdate = userRepository.save(user);
        Assertions.assertThat(userUpdate.getUserName()).isEqualTo("test");
    }

}
