package org.mustafa.akilli.pricing.blacklist;

import java.util.HashMap;
import java.util.Map;

public class CompanyBlackList implements BlackList{
    private static CompanyBlackList companyBlackListInstance = null;
    private static Map<String, Integer> companyBlacklist;
    private static final int IGNORE_VALUE = 0;
    private static final int UNPAID_MONTH_LIMIT_TO_ENTER_BLACKLIST = 2;

    private CompanyBlackList() {
        this.companyBlacklist = new HashMap<>();
    }

    public static CompanyBlackList getInstance()
    {
        if (companyBlackListInstance == null)
            companyBlackListInstance = new CompanyBlackList();

        return companyBlackListInstance;
    }

    @Override
    public void addToBlackList(String elementName) {
        companyBlacklist.put(elementName,IGNORE_VALUE);
    }

    @Override
    public void removeFromBlackList(String elementName) {
        companyBlacklist.remove(elementName);
    }

    @Override
    public boolean isTheElementBlacklisted(String elementName) {
        return (companyBlacklist.get(elementName) != null);
    }

    @Override
    public boolean checkingWhetherTheElementShouldBeBlacklisted(int numberOfMonthsUnpaidByTheCompany) {
        return numberOfMonthsUnpaidByTheCompany >= UNPAID_MONTH_LIMIT_TO_ENTER_BLACKLIST;
    }

    @Override
    public void clearBlacklist() {
        companyBlackListInstance = null;
    }

    @Override
    public Map<String, Integer> getBlacklist() {
        return this.companyBlacklist;
    }
}
