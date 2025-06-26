package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping
    public Profile getProfile(Principal principal) {
        try {
            // Get current user's ID
            int userId = getCurrentUserId(principal);

            // Retrieve profile
            Profile profile = profileDao.getByUserId(userId);

            if (profile == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }

            return profile;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving profile", e);
        }
    }

    @PutMapping
    public Profile updateProfile(@RequestBody Profile updatedProfile, Principal principal) {
        try {
            // Get current user's ID
            int userId = getCurrentUserId(principal);
            updatedProfile.setUserId(userId);

            if (updatedProfile.getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to update this profile");
            }

            // Update the profile
            profileDao.update(userId, updatedProfile);

            // Return the updated profile
            return profileDao.getByUserId(userId);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), "Error updating profile: " + e.getReason(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating profile", e);
        }
    }

    private int getCurrentUserId(Principal principal) {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        return user.getId();
    }
}