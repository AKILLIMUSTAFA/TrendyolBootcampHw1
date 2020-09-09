package org.mustafa.akilli.pricing;

import org.mustafa.akilli.pricing.debt.Debt;

import java.math.BigDecimal;

public interface Pricing {
    boolean isTheQuotaFull();
    void payCurrentMonthlyFee();
    void dontPayCurrentMonthlyFee();
    void payTotalDebt();
    void increaseTheUsedQuotaBy1();
    void updateCurrentMonthlyFee();
    int getUsedQuotaAmount();
    BigDecimal getCurrentMonthlyFee();
    Debt getDebtHistory();
    String getCompanyName();
}
