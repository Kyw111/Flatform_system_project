package com.system.Flatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserAPI {

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok().body("OK!!!!!");
    }
}
