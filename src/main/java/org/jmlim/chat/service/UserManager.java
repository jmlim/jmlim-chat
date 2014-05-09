package org.jmlim.chat.service;

import java.util.List;

import org.jmlim.chat.model.User;

/**
 * @author sec
 * 
 */
public interface UserManager {

	public User getUser(String userId);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

	public List<User> getUsers();
}
