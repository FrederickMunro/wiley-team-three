package wileyt3.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerWithJwt {

    @GetMapping("/getMessageWithToken")
    public ResponseEntity<List<String>> getMessageWithToken() {
        return ResponseEntity.ok(List.of("first", "second", "third"));
    }
}
