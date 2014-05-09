package org.jmlim.chat.security;

import java.util.Collection;
import java.util.List;

import org.jmlim.chat.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -1343925114258012037L;

	private User user;

	private List<GrantedAuthority> authorities;

	/**
	 * @param user
	 * @param authorities
	 */
	public UserPrincipal(User user, List<GrantedAuthority> authorities) {
		super();
		this.user = user;
		this.authorities = authorities;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		if (user != null) {
			return user.getPassword();
		}
		return null;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		if (user != null) {
			return user.getUid();
		}
		return null;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

}
