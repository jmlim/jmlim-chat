package org.jmlim.chat.controller;

import org.jmlim.chat.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator
 * 
 */
@Controller
public class ChatRoomController {

	@Autowired
	private UserManager userManager;

	@RequestMapping(value = { "/chat/chat-room" }, method = RequestMethod.GET)
	public String signinPageHandler(Model model) {
		return "/chat/chat-room";
	}
}
