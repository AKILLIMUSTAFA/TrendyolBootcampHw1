package org.mustafa.akilli.pricing.blacklist;

public interface BlackList {
    void addToBlackList();
    void removeFromBlackList();
    boolean isTheElementBlacklisted();
    boolean checkingWhetherTheElementShouldBeBlacklisted(int numberOfMonthsUnpaidByTheCompany);
}
