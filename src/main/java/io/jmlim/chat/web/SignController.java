package io.jmlim.chat.web;

import io.jmlim.chat.config.auth.AccountAdapter;
import io.jmlim.chat.config.auth.LoginUser;
import io.jmlim.chat.service.UserService;
import io.jmlim.chat.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@Controller
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;

    @GetMapping("/signin")
    public String signin(Model model, String error) {
        if (Objects.nonNull(error)) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        return "/sign/signin";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "/sign/signup";
    }

    @PostMapping(value = "/save")
    public @ResponseBody
    ResponseEntity<Long> save(@RequestBody UserSaveRequestDto requestDto) {
        // TODO: Validation 처리
        log.info("requestDto {} : ", requestDto);
        Long save = userService.save(requestDto);
        return ResponseEntity.ok(save);
    }
}
