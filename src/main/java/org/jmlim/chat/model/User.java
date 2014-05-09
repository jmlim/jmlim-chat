package org.jmlim.chat.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
		@NamedQuery(name = "org.jmlim.chat.model.User@getUser(uId)", query = "from User as user where U_ID = :uId"),
		@NamedQuery(name = "org.jmlim.chat.model.User@getUser()", query = "from User as user") })
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6582137205440661975L;

	// uid 를 부적합한 식별자 때문에 변경하였음.
	@Id
	@Column(name = "U_ID", nullable = false)
	@Size(min = 5, max = 15)
	private String uid;

	@Column(name = "NAME", nullable = false)
	@Size(min = 2, max = 30)
	@NotEmpty
	private String name;

	@Column(name = "EMAIL", nullable = false)
	@NotEmpty
	@Email
	private String email;

	@Column(name = "PASSWORD")
	@NotEmpty
	private String password;

	@Column(name = "ROLES")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = { @JoinColumn(name = "user_id") })
	private List<String> roles;

	public User() {
	}

	/**
	 * @param uid
	 * @param name
	 * @param email
	 */
	public User(String uid, String name, String email) {
		this.uid = uid;
		this.name = name;
		this.email = email;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User information ( uid : " + this.getUid() + ", name : "
				+ this.getName() + ", email : " + this.getEmail()
				+ ", roles : " + this.getRoles() + ")";
	}
}
