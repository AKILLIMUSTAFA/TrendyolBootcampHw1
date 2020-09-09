package org.mustafa.akilli.language;

import java.math.BigDecimal;

public class Turkish implements Language{

    @Override
    public String getSmsSentSuccessfullyString() {
        return "Sms başarıyla gönderildi";
    }

    @Override
    public String getMailSentSuccessfullyString() {
        return "Mail başarıyla gönderildi";
    }

    @Override
    public String getCouldNotSentBecauseYouAreOnTheBlacklistString(String companyName) {
        return companyName + " kara listede olduğu için gönderilemedi";
    }

    @Override
    public String getNewCurrentMonthlyQuotaString(int newQuotaLimit) {
        return "Bu ayki yeni kotanız : " + newQuotaLimit;
    }

    @Override
    public String getNewCurrentMonthlyFeeString(BigDecimal newCurrentMonthlyFee) {
        return "Bu ayki yeni ücretiniz : " + newCurrentMonthlyFee;
    }

    @Override
    public String getDuplicateChannelErrorString() {
        return "Yinelenen Kanal Hatası";
    }
}
