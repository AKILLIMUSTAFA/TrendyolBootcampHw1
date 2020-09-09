package org.mustafa.akilli.language;

import java.math.BigDecimal;

public class English implements Language{
    @Override
    public String getSmsSentSuccessfullyString() {
        return "Sms successfully sent";
    }

    @Override
    public String getMailSentSuccessfullyString() {
        return "E-mail successfully sent";
    }

    @Override
    public String getCouldNotSentBecauseYouAreOnTheBlacklistString(String companyName) {
        return "Could not sent because " + companyName + " company is on the blacklist";
    }

    @Override
    public String getNewCurrentMonthlyQuotaString(int newQuotaLimit) {
        return "New current monthly quota : " + newQuotaLimit ;
    }

    @Override
    public String getNewCurrentMonthlyFeeString(BigDecimal newCurrentMonthlyFee) {
        return "New current monthly fee : " + newCurrentMonthlyFee ;
    }

    @Override
    public String getDuplicateChannelErrorString() {
        return "Duplicate Channel Error";
    }
}
