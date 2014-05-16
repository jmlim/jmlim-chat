package org.jmlim.chat.service;

import java.util.List;

import org.jmlim.chat.model.Image;

public interface ImageManager {
	public Image getImage(Integer imageId);

	public void deleteImage(Image image);

	public void updateImage(Image image);

	public void createImage(Image image);

	public List<Image> getAllImages();

	public List<Image> getImages(String groupId);
}
