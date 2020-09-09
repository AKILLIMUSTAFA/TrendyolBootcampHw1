package org.mustafa.akilli.channels;

import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.pricing.Pricing;
import org.mustafa.akilli.user.User;

public class SmsDTO extends ChannelAbstract{

    public SmsDTO(Pricing pricing, Language language) {
        super(pricing, language);
    }

    @Override
    public void sendIndividualNotification(User user) {
        innerSendIndividualNotification();
        this.language.getSmsSentSuccessfullyString();
    }
}
