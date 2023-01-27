package com.system.Flatform.api;

import com.system.Flatform.user.dto.UserRequestDTO;
import com.system.Flatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserAPI {

    private final UserService userService;
    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok().body("OK!!!!!");
    }

    @PostMapping("/sign")
    public ResponseEntity signup (@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        return ResponseEntity.ok().body(userService.signUp(userRequestDTO));
    }
}
