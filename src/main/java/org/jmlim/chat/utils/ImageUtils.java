package org.jmlim.chat.utils;

import org.apache.commons.lang3.StringUtils;
import org.jmlim.chat.model.Image;

public class ImageUtils {

	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	/**
	 * @param image
	 * @return
	 */
	public static String getImageUrl(Image image) {
		StringBuilder sb = new StringBuilder();
		sb.append("/jmlim-chat/sign/uploaded/");
		sb.append("?");
		sb.append("imageId");
		sb.append("=");
		sb.append(image.getId());
		return sb.toString();
	}

	/**
	 * 저장할 파일의 이름을 반환
	 * 
	 * @param originalFilename
	 * @return
	 */
	public static String getStoreFileName(String originalFilename) {
		if (StringUtils.isNotBlank(originalFilename)) {
			String fileType = StringUtils.substringAfterLast(originalFilename,
					".");
			String fileName = StringUtils.substringBeforeLast(originalFilename,
					".");

			StringBuilder storeFile = new StringBuilder();
			storeFile.append(fileName);
			storeFile.append("_");
			storeFile.append(System.currentTimeMillis());
			storeFile.append(".");
			storeFile.append(fileType);

			return storeFile.toString();
		}
		return null;
	}

	/**
	 * 파일의 저장할 위치를 반환
	 * 
	 * @return
	 */
	public static String getFileStorePath() {
		String jmlimChatHomePath = System.getProperty("jmlim.chat.home");

		StringBuilder filePath = new StringBuilder();
		filePath.append(jmlimChatHomePath);
		filePath.append("upload");
		filePath.append(FILE_SEPARATOR);
		filePath.append("images");
		filePath.append(FILE_SEPARATOR);

		return filePath.toString();
	}
}
