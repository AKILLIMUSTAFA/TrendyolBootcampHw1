package org.mustafa.akilli.user;

public abstract class UserAbstract implements User{

    protected String email;
    protected String phone;

    public UserAbstract(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}
