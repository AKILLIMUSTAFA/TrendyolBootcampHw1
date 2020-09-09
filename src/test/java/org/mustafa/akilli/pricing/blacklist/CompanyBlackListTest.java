package org.mustafa.akilli.pricing.blacklist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CompanyBlackListTest {
    private CompanyBlackList companyBlackList;
    private String companyName;
    private String otherCompanyName;

    @Before
    public void setUp(){
        companyName = "Yılmazlar İnşaat";
        companyBlackList = CompanyBlackList.getInstance();
        otherCompanyName = "Kardeşler Matbaa";
    }

    @Test
    public void it_should_add_company_to_blacklist(){
        assertEquals(companyBlackList.getBlacklist().size(), 0);
        companyBlackList.addToBlackList(companyName);
        assertEquals(companyBlackList.getBlacklist().size(), 1);
    }

    @Test
    public void it_should_not_add_more_than_one_same_company_to_blacklist(){
        companyBlackList.addToBlackList(companyName);
        assertEquals(companyBlackList.getBlacklist().size(), 1);
        companyBlackList.addToBlackList(companyName);
        assertEquals(companyBlackList.getBlacklist().size(), 1);
    }

    @Test
    public void it_should_remove_company_from_blacklist(){
        companyBlackList.addToBlackList(companyName);
        assertEquals(companyBlackList.getBlacklist().size(), 1);
        companyBlackList.removeFromBlackList(companyName);
        assertEquals(companyBlackList.getBlacklist().size(), 0);
    }

    @Test
    public void it_should_not_remove_company_from_blacklist_when_company_is_not_in_blacklist(){
        companyBlackList.addToBlackList(companyName);
        companyBlackList.removeFromBlackList(otherCompanyName);
        assertEquals(companyBlackList.getBlacklist().size(), 1);
    }

    @Test
    public void it_should_return_true_when_company_is_in_blacklist(){
        companyBlackList.addToBlackList(companyName);
        assertEquals(companyBlackList.isTheElementBlacklisted(companyName), true);
    }

    @Test
    public void it_should_return_false_when_company_is_not_in_blacklist(){
        assertEquals(companyBlackList.isTheElementBlacklisted(companyName), false);
    }

    @Test
    public void it_should_return_true_when_the_company_needs_to_be_on_the_blacklist(){
        assertEquals(companyBlackList.checkingWhetherTheElementShouldBeBlacklisted(2), true);
    }

    @Test
    public void it_should_return_false_when_the_company_does_not_need_to_be_on_the_blacklist(){
        assertEquals(companyBlackList.checkingWhetherTheElementShouldBeBlacklisted(1), false);
    }

    @After
    public void tearDown(){
        companyName = null;
        otherCompanyName = null;
        companyBlackList.clearBlacklist();
    }
}