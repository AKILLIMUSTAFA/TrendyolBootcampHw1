package org.mustafa.akilli.channels;

import org.mustafa.akilli.user.User;

import java.util.ArrayList;

public interface Channel {

    void sendNotification(ArrayList<User> userList);
}
