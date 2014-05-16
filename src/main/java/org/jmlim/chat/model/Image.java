package org.jmlim.chat.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGES")
@NamedQueries({
		@NamedQuery(name = "org.jmlim.chat.model.Image@getImage(imageId)", query = "from Image as image where ID = :imageId"),
		@NamedQuery(name = "org.jmlim.chat.model.Image@getImages(groupId)", query = "from Image as image where GROUP_ID = :groupId order by ID desc") })
public class Image extends BaseEntity {

	private static final long serialVersionUID = -460077152999290070L;

	@Id
	@GeneratedValue
	@Column
	private Integer id;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "owner", nullable = false)
	private User owner;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "REAL_NAME", nullable = false)
	private String realName;

	@Column(name = "IMAGE_SIZE", nullable = false)
	private Long size;

	@Column(name = "IMAGE_URL")
	private String url;

	@Column(name = "GROUP_ID")
	private String groupId;

	@Column(name = "IMAGE_PATH")
	private String path;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	public Image() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
