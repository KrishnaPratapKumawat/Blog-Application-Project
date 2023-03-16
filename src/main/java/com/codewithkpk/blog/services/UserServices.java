package com.codewithkpk.blog.services;

import java.util.List;

import com.codewithkpk.blog.payloads.UserDataTransferOption;

public interface UserServices {
	public UserDataTransferOption addUserData(UserDataTransferOption udto) ;
	public UserDataTransferOption updateUserData(UserDataTransferOption udto,Integer userId);
	public UserDataTransferOption getUserData(Integer userId);
	public List<UserDataTransferOption> getAllUserData();
	public void deleteUserData(Integer userId);
	public UserDataTransferOption getUserLogin(String email,String password);

	public boolean checkIfUserExist(String email);


}
