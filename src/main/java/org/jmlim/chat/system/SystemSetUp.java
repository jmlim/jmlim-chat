package org.jmlim.chat.system;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * 
 */
@Service(value = "systemSetUp")
public class SystemSetUp {

	private final String CHAT_HOME_ENV = "JMLIM_CHAT_HOME";

	private final String FILE_SEPARATOR = System.getProperty("file.separator");

	private String jmlimChatHomeDir;

	private Log log = LogFactory.getLog(SystemSetUp.class);

	@PostConstruct
	public void initialize() {
		if (log.isInfoEnabled()) {
			log.info("jmlim chat System 초기화 시작");
		}

		makeHomeResourcesDirectories();

		if (StringUtils.isNotBlank(jmlimChatHomeDir)) {
			System.setProperty("jmlim.chat.home", jmlimChatHomeDir);
		}

		if (log.isInfoEnabled()) {
			log.info("jmlim chat System 초기화 완료");
		}
	}

	protected void makeHomeResourcesDirectories() {
		jmlimChatHomeDir = System.getenv(CHAT_HOME_ENV);
		if (log.isInfoEnabled()) {
			log.info(CHAT_HOME_ENV + " 설정이 되어 있지 않아 기본 설정으로 처리 합니다.");
		}
		if (StringUtils.isBlank(jmlimChatHomeDir)) {
			StringBuffer sb = new StringBuffer();
			sb.append(System.getProperty("user.home"));
			sb.append(FILE_SEPARATOR);
			sb.append(".jmlimChat");
			sb.append(FILE_SEPARATOR);

			jmlimChatHomeDir = sb.toString();
		}

		File homeDir = new File(jmlimChatHomeDir);
		if (!homeDir.exists()) {
			homeDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("jmlim chat home 디렉토리 생성 : " + jmlimChatHomeDir);
			}
		}

		makeUploadedImagesDir();
		makeUploadedFilesDir();
		makeTempDir();
	}

	private void makeTempDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(jmlimChatHomeDir);
		sb.append("temp");
		sb.append(FILE_SEPARATOR);

		File tempDir = new File(sb.toString());
		if (!tempDir.exists()) {
			tempDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Temp 디렉토리 생성 : " + sb.toString());
			}
		}
	}

	private void makeUploadedImagesDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(jmlimChatHomeDir);
		sb.append("upload");
		sb.append(FILE_SEPARATOR);
		sb.append("images");
		sb.append(FILE_SEPARATOR);

		File imagesDir = new File(sb.toString());
		if (!imagesDir.exists()) {
			imagesDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Images upload 디렉토리 생성 : " + sb.toString());
			}
		}
	}

	private void makeUploadedFilesDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(jmlimChatHomeDir);
		sb.append("upload");
		sb.append(FILE_SEPARATOR);
		sb.append("files");
		sb.append(FILE_SEPARATOR);

		File filesDir = new File(sb.toString());
		if (!filesDir.exists()) {
			filesDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Files upload 디렉토리 생성 : " + sb.toString());
			}
		}
	}
}
