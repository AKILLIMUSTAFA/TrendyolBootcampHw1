package org.mustafa.akilli.pricing.debt;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DebtHistory implements Debt {
    ArrayList<BigDecimal> monthlyDebtAmountList = new ArrayList<>();

    @Override
    public void addNewDebt(BigDecimal fee) {
        monthlyDebtAmountList.add(fee);
    }

    @Override
    public void clearAllDebt() {
        monthlyDebtAmountList.clear();
    }

    @Override
    public int getHowManyMonthsTheCompanyHasNotPaidItsDebt() {
        return monthlyDebtAmountList.size();
    }
}
