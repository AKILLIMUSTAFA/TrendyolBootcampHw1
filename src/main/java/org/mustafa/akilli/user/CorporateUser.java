package org.mustafa.akilli.user;

import org.mustafa.akilli.channels.Channel;
import org.mustafa.akilli.exceptions.DuplicateChannelException;
import org.mustafa.akilli.language.Language;

import java.util.ArrayList;

public class CorporateUser extends UserAbstract{
    private String companyName;
    private ArrayList<Channel> channels;
    private Language language;

    public CorporateUser(String email, String phone, String companyName, ArrayList<Channel> channels, Language language) {
        super(email, phone);
        this.companyName = companyName;
        this.channels = channels;
        this.language = language;
        checkDublicateChannel();
    }

    @Override
    public String getUserName() {
        return companyName;
    }

    void checkDublicateChannel(){
        for (int i = 0; i < channels.size(); i++) {
            for (int j = i + 1; j < channels.size(); j++) {
                if(channels.get(i).getClass().getSimpleName() == channels.get(j).getClass().getSimpleName()){
                    throw new DuplicateChannelException(this.language.getDuplicateChannelErrorString());
                }
            }
        }
    }

}
