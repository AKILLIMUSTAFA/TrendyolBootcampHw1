package org.mustafa.akilli.channels;

import org.mustafa.akilli.pricing.Pricing;
import org.mustafa.akilli.user.User;

import java.util.ArrayList;

public class SmsDTO extends ChannelAbstract{

    public SmsDTO(Pricing pricing) {
        super(pricing);
    }

    @Override
    public void sendNotification(ArrayList<User> userList) {

    }
}
