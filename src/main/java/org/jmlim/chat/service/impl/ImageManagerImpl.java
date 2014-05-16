package org.jmlim.chat.service.impl;

import java.util.Date;
import java.util.List;

import org.jmlim.chat.model.Image;
import org.jmlim.chat.service.AbstractJpaDaoService;
import org.jmlim.chat.service.ImageManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("imageManager")
@Transactional
public class ImageManagerImpl extends AbstractJpaDaoService implements
		ImageManager {

	/**
	 * @see org.jmlim.chat.service.ImageManager#getImage(java.lang.Integer)
	 */
	@Override
	public Image getImage(Integer imageId) {
		return getEntityManager().find(Image.class, imageId);
	}

	/**
	 * @see org.jmlim.chat.service.ImageManager#deleteImage(org.jmlim.chat.model.Image)
	 */
	@Override
	public void deleteImage(Image image) {
		getEntityManager().remove(image);
	}

	/**
	 * @see org.jmlim.chat.service.ImageManager#updateImage(org.jmlim.chat.model.Image)
	 */
	@Override
	public void updateImage(Image image) {
		image.setModifiedDate(new Date());
		getEntityManager().merge(image);
	}

	/**
	 * @see org.jmlim.chat.service.ImageManager#createImage(org.jmlim.chat.model.Image)
	 */
	@Override
	public void createImage(Image image) {
		image.setCreatedDate(new Date());
		getEntityManager().persist(image);
	}

	/**
	 * @see org.jmlim.chat.service.ImageManager#getAllImages()
	 */
	@Override
	public List<Image> getAllImages() {
		List<Image> results = getEntityManager().createNamedQuery(
				"org.jmlim.chat.model.Image@getAllImages()", Image.class)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see org.jmlim.chat.service.ImageManager#getImages(java.lang.String)
	 */
	@Override
	public List<Image> getImages(String groupId) {
		// TODO Auto-generated method stub
		List<Image> results = getEntityManager()
				.createNamedQuery(
						"org.jmlim.chat.model.Image@getImages(groupId)",
						Image.class).setParameter("groupId", groupId)
				.getResultList();
		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}
}
