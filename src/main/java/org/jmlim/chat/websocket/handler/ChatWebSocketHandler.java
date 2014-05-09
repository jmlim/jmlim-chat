package org.jmlim.chat.websocket.handler;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jmlim.chat.model.User;
import org.jmlim.chat.security.UserPrincipal;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {

	// 현재 접속되어 있는 모든 WebSocketSession 정보를 답는다.
	private Map<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

	/*
	 * @Autowired private UserManager userManager;
	 */

	/**
	 * @param session
	 * @return userId 반환
	 */
	private String getUserId(WebSocketSession session) {
		User user = getUser(session);
		if (user != null) {
			String userId = user.getUid();
			return userId;
		}
		return null;
	}

	/**
	 * @param session
	 * @return userName 반환
	 */
	private String getUserName(WebSocketSession session) {
		User user = getUser(session);
		if (user != null) {
			String userName = user.getName();
			return userName;
		}

		return null;
	}

	/**
	 * @param session
	 * @return
	 * 
	 *         User 객체 반환.
	 */
	private User getUser(WebSocketSession session) {
		Map<String, Object> handshakeAttributes = session.getAttributes();
		SecurityContext context = (SecurityContext) handshakeAttributes
				.get("SPRING_SECURITY_CONTEXT");
		Authentication authentication = context.getAuthentication();
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

		if (principal != null) {
			User user = principal.getUser();
			return user;
		}

		return null;
	}

	/**
	 * @param userId
	 * @return
	 * 
	 *         sessionMap 안에 userId 가 있는지 여부를 알려줌.
	 */
	private boolean isUserIdInWebSocketSessionMap(String userId) {
		Set<Entry<String, WebSocketSession>> entrySet = sessionMap.entrySet();

		for (Entry<String, WebSocketSession> entry : entrySet) {
			WebSocketSession entrySession = entry.getValue();
			Principal principal = entrySession.getPrincipal();
			if (userId != null && userId.equals(principal.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return
	 */
	private Map<String, String> currentConnectionUsers() {
		Map<String, String> currentConnectionUsers = new HashMap<String, String>();
		Set<Entry<String, WebSocketSession>> entrySet = sessionMap.entrySet();

		for (Entry<String, WebSocketSession> entry : entrySet) {
			WebSocketSession entrySession = entry.getValue();
			Principal principal = entrySession.getPrincipal();
			String userId = principal.getName();

			currentConnectionUsers.put(userId, userId);
		}

		return currentConnectionUsers;
	}

	/**
	 * @param session
	 * @param message
	 * @throws IOException
	 * 
	 *             요청한 sessionId 를 제외한 모든 WebSocketSession 에게 메세지를 보냄.
	 */
	private void sendMessage(WebSocketSession session, String message)
			throws IOException {
		Set<Entry<String, WebSocketSession>> entrySet = sessionMap.entrySet();
		String sessionId = session.getId();
		for (Entry<String, WebSocketSession> entry : entrySet) {
			if (!sessionId.equals(entry.getKey())) {
				WebSocketSession entrySession = entry.getValue();
				entrySession.sendMessage(new TextMessage(message));
			}
		}
	}

	/**
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
	 * 
	 *      접속한 사용자의 웹 소켓 정보를 담는다.
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String userId = getUserId(session);
		boolean sendMessage = !isUserIdInWebSocketSessionMap(userId);

		sessionMap.put(session.getId(), session);

		JSONObject jsonObject = new JSONObject();
		if (sendMessage) {
			String userName = getUserName(session);
			/*
			 * String connectMessage = "{\"userName\": \"" + userName +
			 * "\",\"message\":\"" + userName + "이 대화방에 들어왔습니다.\"}";
			 */
			jsonObject.put("userName", userName);
			jsonObject.put("message", userName + "이 대화방에 들어왔습니다.");

			// System.out.println(connectMessage);

			// System.out.println(jsonObject.toString());

		}
		jsonObject.put("currentUsers", currentConnectionUsers());
		// System.out.println(jsonObject.toString());
		sendMessage(session, jsonObject.toString());
		session.sendMessage(new TextMessage(jsonObject.toString()));

	}

	/**
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#handleTextMessage(org.springframework.web.socket.WebSocketSession,
	 *      org.springframework.web.socket.TextMessage)
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		String userName = getUserName(session);
		String messagePayLoad = message.getPayload();

		messagePayLoad = StringEscapeUtils.escapeHtml4(messagePayLoad);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", userName);
		jsonObject.put("message", messagePayLoad);

		sendMessage(session, jsonObject.toString());
	}

	/**
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionClosed(org.springframework.web.socket.WebSocketSession,
	 *      org.springframework.web.socket.CloseStatus)
	 * 
	 *      접속한 사용자의 웹 소켓 정보를 제거한다.
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {

		String userId = getUserId(session);

		JSONObject jsonObject = new JSONObject();

		String sessionId = session.getId();
		if (sessionMap.containsKey(sessionId)) {
			sessionMap.remove(sessionId);
		}
		jsonObject.put("currentUsers", currentConnectionUsers());

		boolean sendMessage = !isUserIdInWebSocketSessionMap(userId);
		if (sendMessage) {
			String userName = getUserName(session);

			jsonObject.put("userName", userName);
			jsonObject.put("message", userName + "이 대화방에서 나갔습니다.");
		}
		sendMessage(session, jsonObject.toString());
	}

	public Map<String, WebSocketSession> getSessionMap() {
		return sessionMap;
	}
}
