package org.mustafa.akilli.pricing.debt;

import java.math.BigDecimal;

public interface Debt {
    void addNewDebt(BigDecimal fee);
    void clearAllDebt();
    int getHowManyMonthsTheCompanyHasNotPaidItsDebt();
}
