package com.batchproject.jobs.controllers;


import com.batchproject.jobs.models.user.UserProfile;
import com.batchproject.jobs.services.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user-profiles")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<UserProfile>> registerUserProfile(@RequestBody UserProfile payload) {
        return userProfileService.registerUserProfile(payload)
                .thenApply(savedProfile -> ResponseEntity.status(HttpStatus.CREATED).body(savedProfile));
    }

    @PutMapping("/{keycloakUserId}")
    public CompletableFuture<ResponseEntity<UserProfile>> updateUserProfile(@PathVariable String keycloakUserId, @RequestBody UserProfile payload) {
        return userProfileService.updateUserProfile(keycloakUserId, payload)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{keycloakUserId}")
    public CompletableFuture<ResponseEntity<UserProfile>> deleteUserProfile(@PathVariable String keycloakUserId) {
        return userProfileService.deleteUserProfile(keycloakUserId)
                .thenApply(ResponseEntity::ok);
    }
}
