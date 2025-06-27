package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    // Added methods to get and update profiles by user ID
    Profile getByUserId(int userId);
    void update(int userId, Profile profile);

}
