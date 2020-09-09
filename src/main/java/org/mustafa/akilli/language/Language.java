package org.mustafa.akilli.language;

import java.math.BigDecimal;

public interface Language {
    String getSentSuccessfullyString();
    String getCouldNotSentBecauseYouAreOnTheBlacklistString(String companyName);
    String getNewCurrentMonthlyQuotaString(int newQuotaLimit);
    String getNewCurrentMonthlyFeeString(BigDecimal  newCurrentMonthlyFee);
    String getDuplicateChannelErrorString();
}
