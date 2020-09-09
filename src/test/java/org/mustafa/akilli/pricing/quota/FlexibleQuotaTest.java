package org.mustafa.akilli.pricing.quota;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.language.Turkish;
import org.mustafa.akilli.pricing.FixedQuota;
import org.mustafa.akilli.pricing.FlexibleQuota;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class FlexibleQuotaTest {
    private FlexibleQuota emailFlexibleQuota;
    private String companyName;
    private BigDecimal initialMonthlyFee;
    private int initialQuotaLimit;
    private BigDecimal extraUsageFee;
    private Language language;

    @Before
    public void setUp(){
        //  Email Esnek Kotali: 2000 adet - 7.5TL, 2000 adetten sonra ki her SMS 0.03TL dir
        companyName = "Kardeşler Gıda";
        initialMonthlyFee = new BigDecimal(7.50);
        initialQuotaLimit = 2000;
        extraUsageFee = new BigDecimal(0.03);
        language = new Turkish();

        emailFlexibleQuota = new FlexibleQuota(companyName,initialMonthlyFee,initialQuotaLimit,extraUsageFee,language);
    }

    @Test
    public void it_should_increase_the_used_quota_by_1(){

        int currentUsedQuotaAmount = emailFlexibleQuota.getUsedQuotaAmount();

        emailFlexibleQuota.increaseTheUsedQuotaBy1();

        assertEquals(currentUsedQuotaAmount + 1, emailFlexibleQuota.getUsedQuotaAmount());
    }

    @Test
    public void it_should_return_updated_current_monthly_fee_when_the_company_exceeds_the_limit(){

        for (int i = 0; i < initialQuotaLimit; i++) {
            emailFlexibleQuota.increaseTheUsedQuotaBy1();
            emailFlexibleQuota.updateCurrentMonthlyFee();
        }

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee(), new BigDecimal(7.53).setScale(2, BigDecimal.ROUND_HALF_EVEN));

        for (int i = 0; i < initialQuotaLimit; i++) {
            emailFlexibleQuota.increaseTheUsedQuotaBy1();
            emailFlexibleQuota.updateCurrentMonthlyFee();
        }

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee(), new BigDecimal(7.50 + ((initialQuotaLimit + 1) * 0.03)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

    @Test
    public void it_should_return_default_current_monthly_fee_when_paid_current_monthly_fee(){

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            emailFlexibleQuota.increaseTheUsedQuotaBy1();
            emailFlexibleQuota.updateCurrentMonthlyFee();
        }

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee() != initialMonthlyFee, true);

        emailFlexibleQuota.payCurrentMonthlyFee();

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee() == initialMonthlyFee, true);
    }

    @Test
    public void it_should_return_default_current_monthly_fee_when_unpaid_current_monthly_fee(){

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            emailFlexibleQuota.increaseTheUsedQuotaBy1();
            emailFlexibleQuota.updateCurrentMonthlyFee();
        }

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee() != initialMonthlyFee, true);

        emailFlexibleQuota.dontPayCurrentMonthlyFee();

        assertEquals(emailFlexibleQuota.getCurrentMonthlyFee() == initialMonthlyFee, true);
    }

    @Test
    public void it_should_increase_the_unpaid_months_by_1_when_unpaid_current_monthly_fee(){

        assertEquals(emailFlexibleQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 0);

        emailFlexibleQuota.dontPayCurrentMonthlyFee();

        assertEquals(emailFlexibleQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 1);

        emailFlexibleQuota.dontPayCurrentMonthlyFee();

        assertEquals(emailFlexibleQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 2);
    }

    @Test
    public void it_should_clear_the_unpaid_months_when_paid_total_debt(){

        emailFlexibleQuota.dontPayCurrentMonthlyFee();
        emailFlexibleQuota.dontPayCurrentMonthlyFee();

        assertEquals(emailFlexibleQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 2);

        emailFlexibleQuota.payTotalDebt();

        assertEquals(emailFlexibleQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 0);
    }

    @After
    public void tearDown(){
        emailFlexibleQuota = null;
    }
}