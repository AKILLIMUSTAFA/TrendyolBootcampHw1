package org.mustafa.akilli.pricing.quota;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.language.Turkish;
import org.mustafa.akilli.pricing.FixedQuota;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class FixedQuotaTest {
    private FixedQuota smsFixedQuota;
    private String companyName;
    private BigDecimal initialMonthlyFee;
    private int initialQuotaLimit;
    private int extraQuotaLimit;
    private BigDecimal extraQuotaFee;
    private Language language;

    @Before
    public void setUp(){
        //  SMS Sabit Kotali: 1000 adet - 20TL ile sinirlidir. 1000 adeti asan gonderimde 20TL karsiliginda 1000 adet kota tekrar tanimlanir
        companyName = "Kardeşler Gıda";
        initialMonthlyFee = new BigDecimal(20.00);
        initialQuotaLimit = 1000;
        extraQuotaLimit = 1000;
        extraQuotaFee = new BigDecimal(20.00);
        language = new Turkish();

        smsFixedQuota = new FixedQuota(companyName,initialMonthlyFee,initialQuotaLimit,extraQuotaLimit,extraQuotaFee,language);
    }

    @Test
    public void it_should_increase_the_used_quota_by_1(){

        int currentUsedQuotaAmount = smsFixedQuota.getUsedQuotaAmount();

        smsFixedQuota.increaseTheUsedQuotaBy1();

        assertEquals(currentUsedQuotaAmount + 1, smsFixedQuota.getUsedQuotaAmount());
    }

    @Test
    public void it_should_return_updated_current_monthly_fee_when_the_company_exceeds_the_limit(){

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            smsFixedQuota.increaseTheUsedQuotaBy1();
            smsFixedQuota.updateCurrentMonthlyFee();
        }

        assertEquals(smsFixedQuota.getCurrentMonthlyFee(), new BigDecimal(40.00));

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            smsFixedQuota.increaseTheUsedQuotaBy1();
            smsFixedQuota.updateCurrentMonthlyFee();
        }

        assertEquals(smsFixedQuota.getCurrentMonthlyFee(), new BigDecimal(60.00));
    }

    @Test
    public void it_should_return_default_current_monthly_fee_when_paid_current_monthly_fee(){

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            smsFixedQuota.increaseTheUsedQuotaBy1();
            smsFixedQuota.updateCurrentMonthlyFee();
        }

        assertEquals(smsFixedQuota.getCurrentMonthlyFee() != initialMonthlyFee, true);

        smsFixedQuota.payCurrentMonthlyFee();

        assertEquals(smsFixedQuota.getCurrentMonthlyFee() == initialMonthlyFee, true);
    }

    @Test
    public void it_should_return_default_current_monthly_fee_when_unpaid_current_monthly_fee(){

        for (int i = 0; i < initialQuotaLimit + 1; i++) {
            smsFixedQuota.increaseTheUsedQuotaBy1();
            smsFixedQuota.updateCurrentMonthlyFee();
        }

        assertEquals(smsFixedQuota.getCurrentMonthlyFee() != initialMonthlyFee, true);

        smsFixedQuota.dontPayCurrentMonthlyFee();

        assertEquals(smsFixedQuota.getCurrentMonthlyFee() == initialMonthlyFee, true);
    }

    @Test
    public void it_should_increase_the_unpaid_months_by_1_when_unpaid_current_monthly_fee(){

        assertEquals(smsFixedQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 0);

        smsFixedQuota.dontPayCurrentMonthlyFee();

        assertEquals(smsFixedQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 1);

        smsFixedQuota.dontPayCurrentMonthlyFee();

        assertEquals(smsFixedQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 2);
    }

    @Test
    public void it_should_clear_the_unpaid_months_when_paid_total_debt(){

        smsFixedQuota.dontPayCurrentMonthlyFee();
        smsFixedQuota.dontPayCurrentMonthlyFee();

        assertEquals(smsFixedQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 2);

        smsFixedQuota.payTotalDebt();

        assertEquals(smsFixedQuota.getDebtHistory().getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 0);
    }

    @After
    public void tearDown(){
        smsFixedQuota = null;
    }
}