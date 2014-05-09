package org.jmlim.chat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmlim.chat.model.User;
import org.jmlim.chat.service.AbstractJpaDaoService;
import org.jmlim.chat.service.UserManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * 
 */
@Service("userManager")
@Transactional
public class UserManagerImpl extends AbstractJpaDaoService implements
		UserManager {

	/**
	 * @see org.haengbokhan.service.UserManager#getUser(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public User getUser(String userId) {

		List<User> results = getEntityManager()
				.createNamedQuery("org.jmlim.chat.model.User@getUser(uId)",
						User.class).setParameter("uId", userId).getResultList();

		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * @see org.haengbokhan.service.UserManager#createUser(org.jmlim.chat.model.User)
	 */
	public void createUser(User user) {
		user.setCreatedDate(new Date());
		List<String> userRoles = user.getRoles();
		if (userRoles == null || userRoles.isEmpty()) {
			userRoles = new ArrayList<String>();
			userRoles.add("ROLE_USER");
		}
		user.setRoles(userRoles);
		getEntityManager().persist(user);
	}

	/**
	 * @see org.haengbokhan.service.UserManager#updateUser(org.jmlim.chat.model.User)
	 */
	public void updateUser(User user) {
		user.setModifiedDate(new Date());
		getEntityManager().merge(user);
	}

	/**
	 * @see org.haengbokhan.service.UserManager#deleteUser(org.jmlim.chat.model.User)
	 */
	public void deleteUser(User user) {
		getEntityManager().remove(user);
	}

	/**
	 * @see org.haengbokhan.service.UserManager#getUsers()
	 */
	@Transactional(readOnly = true)
	public List<User> getUsers() {

		List<User> results = getEntityManager().createNamedQuery(
				"org.jmlim.chat.model.User@getUser()", User.class)
				.getResultList();
		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}
}
