package com.batchproject.jobs.services;

import com.batchproject.jobs.models.user.UserProfile;
import com.batchproject.jobs.models.user.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Async
    public CompletableFuture<UserProfile> registerUserProfile(UserProfile payload) {
        // Check if the user already exists based on Keycloak User ID
        Optional<UserProfile> existingUser = userProfileRepository.findByKeycloakUserId(payload.getKeycloakUserId());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already registered with the given Keycloak ID");
        }

        payload.setId(null);
        userProfileRepository.save(payload);



        return CompletableFuture.completedFuture(payload);
    }

    @Async
    public CompletableFuture<UserProfile> updateUserProfile(String keycloakUserId, UserProfile payload) {
        // Find the existing user profile
        Optional<UserProfile> existingUserProfile = userProfileRepository.findByKeycloakUserId(keycloakUserId);
        if (!existingUserProfile.isPresent()) {
            throw new RuntimeException("User profile not found");
        }


        // Save the updated user profile
        userProfileRepository.save(payload);

        return CompletableFuture.completedFuture(payload);
    }

    @Async
    public CompletableFuture<UserProfile> deleteUserProfile(String keycloakUserId) {
        // Find the user profile by Keycloak User ID
        Optional<UserProfile> userProfile = userProfileRepository.findByKeycloakUserId(keycloakUserId);
        if (!userProfile.isPresent()) {
            throw new RuntimeException("User profile not found");
        }

        // Delete the user profile
//        userProfileRepository.delete(userProfile.get());
       // TODO make the userprofile expire

        return CompletableFuture.completedFuture(userProfile.get());
    }


}
