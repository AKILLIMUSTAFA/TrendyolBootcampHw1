package org.mustafa.akilli.channels;

import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.pricing.Pricing;
import org.mustafa.akilli.user.User;

import java.util.ArrayList;

public interface Channel {
    void sendCollectiveNotification(ArrayList<User> userList);
    void sendIndividualNotification(User user);
    Pricing getPricing();
    Language getLanguage();
}
