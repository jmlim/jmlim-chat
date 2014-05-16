package org.jmlim.chat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.jmlim.chat.model.Image;
import org.jmlim.chat.model.User;
import org.jmlim.chat.service.ImageManager;
import org.jmlim.chat.service.UserManager;
import org.jmlim.chat.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * 
 */
@Controller
@SessionAttributes(value = { "user" }, types = { User.class })
@RequestMapping("/sign")
public class SignController {

	private static final String USER = "user";

	@Autowired
	private UserManager ownerManager;

	@Autowired
	private ImageManager imageManager;

	@ModelAttribute("user")
	public User createModel() {
		return new User();
	}

	@RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
	public String signinPage(Model model) {
		return "/sign/signin";
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signupPage(Model model, HttpSession session) {
		return "/sign/signup";
	}

	// http://iluk.tistory.com/entry/valid-400-%EC%97%90%EB%9F%AC
	@RequestMapping(value = "/processSubmit", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute(USER) @Valid User newUser,
			BindingResult result, SessionStatus status,
			@RequestParam("file") MultipartFile file, HttpSession session)
			throws IllegalStateException, IOException {

		if (result.hasErrors()) {
			return "/sign/signup";
		}

		String originalFilename = file.getOriginalFilename();
		String storeFileName = ImageUtils.getStoreFileName(originalFilename);
		String fileStorePath = ImageUtils.getFileStorePath();

		String redirectUrl = "redirect:/main/main";

		Image image = null;

		User currentUser = (User) session.getAttribute("currentUser");
		String userId = newUser.getUid();
		if (currentUser != null && userId.equals(currentUser.getUid())) {
			User user = ownerManager.getUser(userId);

			user.setEmail(newUser.getEmail());
			user.setName(newUser.getName());
			user.setPassword(newUser.getPassword());

			image = user.getImage();

			image.setRealName(originalFilename);
			image.setName(storeFileName);
			image.setSize(file.getSize());
			image.setPath(fileStorePath);
			image.setContentType(file.getContentType());
			image.setUrl(ImageUtils.getImageUrl(image));

			user.setImage(image);
			status.setComplete();
			ownerManager.updateUser(user);
		} else {
			status.setComplete();

			image = new Image();
			image.setRealName(originalFilename);
			image.setName(storeFileName);
			image.setSize(file.getSize());
			image.setPath(fileStorePath);
			image.setContentType(file.getContentType());
			image.setUrl(ImageUtils.getImageUrl(image));

			newUser.setImage(image);
			image.setOwner(newUser);

			ownerManager.createUser(newUser);
			redirectUrl = "redirect:/sign/signin";
		}

		// 파일 업로드
		file.transferTo(new File(fileStorePath + storeFileName));

		return redirectUrl;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");

		ownerManager.deleteUser(currentUser);
		return "redirect:/j_spring_security_logout";
	}

	/**
	 * @param imageId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploaded", method = RequestMethod.GET)
	@ResponseBody
	public void imageShowHandler(@RequestParam("imageId") Integer imageId,
			HttpServletResponse response) throws IOException {
		Image image = imageManager.getImage(imageId);
		String path = image.getPath() + image.getName();
		InputStream in = new FileInputStream(path);
		response.setContentType(image.getContentType());
		IOUtils.copyLarge(in, response.getOutputStream());
	}
}
