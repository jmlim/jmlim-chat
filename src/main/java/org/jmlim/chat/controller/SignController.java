package org.jmlim.chat.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jmlim.chat.model.User;
import org.jmlim.chat.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Administrator
 * 
 */
@Controller
@SessionAttributes(value = { "user" }, types = { User.class })
@RequestMapping("/sign")
public class SignController {

	// private Log log = LogFactory.getLog(getClass());
	private static final String USER = "user";

	@Autowired
	private UserManager ownerManager;

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

	@RequestMapping(value = "/processSubmit", method = RequestMethod.POST)
	// http://iluk.tistory.com/entry/valid-400-%EC%97%90%EB%9F%AC
	public String processSubmit(@ModelAttribute(USER) @Valid User newUser,
			BindingResult result, SessionStatus status, HttpSession session) {

		if (result.hasErrors()) {
			return "/sign/signup";
		}

		User currentUser = (User) session.getAttribute("currentUser");
		String userId = newUser.getUid();
		if (currentUser != null && userId.equals(currentUser.getUid())) {
			User user = ownerManager.getUser(userId);
			user.setEmail(newUser.getEmail());
			user.setName(newUser.getName());
			user.setPassword(newUser.getPassword());
			status.setComplete();
			ownerManager.updateUser(user);
		} else {
			status.setComplete();
			ownerManager.createUser(newUser);
			return "redirect:/sign/signin";
		}

		return "redirect:/main/main";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");

		ownerManager.deleteUser(currentUser);
		return "redirect:/j_spring_security_logout";
	}
}
