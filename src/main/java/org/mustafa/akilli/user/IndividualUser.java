package org.mustafa.akilli.user;

public class IndividualUser extends UserAbstract{
    private String name;
    private String surname;

    public IndividualUser(String email, String phone, String name, String surname) {
        super(email, phone);
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String getUserName() {
        return name + surname;
    }
}
