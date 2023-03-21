package com.codewithkpk.blog.entity;

import com.codewithkpk.blog.payloads.UserDataTransferOption;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private  User userEntity;

    public ConfirmationToken() {}

    public ConfirmationToken(User user) {
        this.userEntity = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
