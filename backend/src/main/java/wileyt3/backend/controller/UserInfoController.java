package wileyt3.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.dto.DecodedTokenDto;
import wileyt3.backend.util.JwtUtil;

@RestController
@CrossOrigin
public class UserInfoController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user-info")
    public DecodedTokenDto getUserInfo(@RequestParam String token) {
        return jwtUtil.decodeJWT(token);
    }
}
