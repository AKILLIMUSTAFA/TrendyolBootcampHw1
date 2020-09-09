package org.mustafa.akilli.pricing.blacklist;

import java.util.HashMap;
import java.util.Map;

public class CompanyBlackList implements BlackList{
    private static Map<String, Integer> companyBlacklist = new HashMap<>();
    private static final int IGNORE_VALUE = 0;
    private static final int UNPAID_MONTH_LIMIT_TO_ENTER_BLACKLIST = 2;
    private String currentCompanyName;

    public CompanyBlackList(String currentCompanyName) {
        this.currentCompanyName = currentCompanyName;
    }

    @Override
    public void addToBlackList() {
        companyBlacklist.put(this.currentCompanyName,IGNORE_VALUE);
    }

    @Override
    public void removeFromBlackList() {
        companyBlacklist.remove(this.currentCompanyName);
    }

    @Override
    public boolean isTheElementBlacklisted() {
        return (companyBlacklist.get(this.currentCompanyName) != null);
    }

    @Override
    public boolean checkingWhetherTheElementShouldBeBlacklisted(int numberOfMonthsUnpaidByTheCompany) {
        return numberOfMonthsUnpaidByTheCompany >= UNPAID_MONTH_LIMIT_TO_ENTER_BLACKLIST;
    }

}
