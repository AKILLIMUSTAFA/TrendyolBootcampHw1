package org.mustafa.akilli.pricing;

public interface Pricing {
    boolean isTheQuotaFull();
    void payCurrentMonthlyFee();
    void dontPayCurrentMonthlyFee();
    void payTotalDebt();
    void increase1usedQuotaAmount();
}
