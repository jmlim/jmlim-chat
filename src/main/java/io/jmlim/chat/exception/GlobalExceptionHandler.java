package io.jmlim.chat.exception;

import io.jmlim.chat.exception.chat.ChatRoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ChatRoomNotFoundException.class})
    public ModelAndView handleForbiddenException(WebRequest request, ChatRoomNotFoundException e) {
        ModelAndView mnv = new ModelAndView();
        mnv.addObject("message", "존재하지 않는 채팅방입니다.");

        mnv.setViewName("/error/room-not-found");
        return mnv;
    }
}
