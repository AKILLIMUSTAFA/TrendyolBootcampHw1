package org.mustafa.akilli.channels;

import org.mustafa.akilli.exceptions.CompanyOnTheBlacklistException;
import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.pricing.Pricing;
import org.mustafa.akilli.pricing.blacklist.CompanyBlackList;
import org.mustafa.akilli.user.User;

import java.util.ArrayList;

public abstract class ChannelAbstract implements Channel{
    protected Pricing pricing;
    protected Language language;

    public ChannelAbstract(Pricing pricing, Language language) {
        this.pricing = pricing;
        this.language = language;
    }

    @Override
    public void sendCollectiveNotification(ArrayList<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            sendIndividualNotification(userList.get(i));
        }
    }

    @Override
    public Pricing getPricing() {
        return pricing;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    public void updateTheCurrentMontlyFee() {
        pricing.increaseTheUsedQuotaBy1();
        pricing.updateCurrentMonthlyFee();
    }

    protected void innerSendIndividualNotification() {
        if(CompanyBlackList.getInstance().isTheElementBlacklisted(this.pricing.getCompanyName())){
            throw new CompanyOnTheBlacklistException(this.language.getCouldNotSentBecauseYouAreOnTheBlacklistString(this.pricing.getCompanyName()));
        }
        updateTheCurrentMontlyFee();
    }

}
