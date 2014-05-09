package org.jmlim.chat.security;

import java.util.ArrayList;
import java.util.List;

import org.jmlim.chat.model.User;
import org.jmlim.chat.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserManager userManager;

	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		// TODO Auto-generated method stub

		try {
			User user = userManager.getUser(userName);
			return new UserPrincipal(user, getAuthorities(user));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param user
	 * @return
	 */
	public List<GrantedAuthority> getAuthorities(User user) {
		if (user != null && user.getRoles() != null) {
			List<GrantedAuthority> authList = getGrantedAuthorities(user
					.getRoles());
			return authList;
		}
		return null;
	}

	/**
	 * @param roles
	 * @return
	 */
	public List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
