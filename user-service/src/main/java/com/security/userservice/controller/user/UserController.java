package com.security.userservice.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/mypage")
    private String myPage() throws Exception {
        return "user/mypage";
    }
}
