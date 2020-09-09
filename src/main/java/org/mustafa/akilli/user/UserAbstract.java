package org.mustafa.akilli.user;

import org.mustafa.akilli.channels.Channel;

import java.util.ArrayList;

public abstract class UserAbstract implements User{

    protected String email;
    protected String phone;

    public UserAbstract(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}
