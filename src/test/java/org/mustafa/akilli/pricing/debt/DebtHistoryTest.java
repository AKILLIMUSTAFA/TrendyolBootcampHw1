package org.mustafa.akilli.pricing.debt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class DebtHistoryTest {
    private DebtHistory debtHistory;

    @Before
    public void setUp(){
        debtHistory = new DebtHistory();
    }

    @Test
    public void it_should_return_n_when_n_debts_are_added_to_the_list(){
        int n = 3;
        BigDecimal currentMonthDept = new BigDecimal(38.05);

        for (int i = 0; i < n; i++) {
            debtHistory.addNewDebt(currentMonthDept);
        }

        assertEquals(debtHistory.getHowManyMonthsTheCompanyHasNotPaidItsDebt(), n);
    }

    @Test
    public void it_should_return_0_when_all_debts_are_cleared(){
        int n = 3;
        BigDecimal currentMonthDept = new BigDecimal(38.05);

        for (int i = 0; i < n; i++) {
            debtHistory.addNewDebt(currentMonthDept);
        }

        debtHistory.clearAllDebt();

        assertEquals(debtHistory.getHowManyMonthsTheCompanyHasNotPaidItsDebt(), 0);
    }

    @After
    public void tearDown(){
        debtHistory = null;
    }
}