package org.mustafa.akilli.pricing.blacklist;

import java.util.Map;

public interface BlackList {
    void addToBlackList(String elementName);
    void removeFromBlackList(String elementName);
    boolean isTheElementBlacklisted(String elementName);
    boolean checkingWhetherTheElementShouldBeBlacklisted(int numberOfMonthsUnpaidByTheCompany);
    void clearBlacklist();
    Map<String, Integer> getBlacklist();
}
