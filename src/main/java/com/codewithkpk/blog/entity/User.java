package com.codewithkpk.blog.entity;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name="user_Data")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(name="user_name")
	private String userName;
	@Column(name="user_email")
	private String email;
	@Column(name="user_password")
	private String password;
	@Column(name="user_about")
	private String about;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Posts> posts= new ArrayList<>();
}
