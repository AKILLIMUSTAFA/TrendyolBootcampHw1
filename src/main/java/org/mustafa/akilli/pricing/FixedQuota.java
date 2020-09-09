package org.mustafa.akilli.pricing;

import java.math.BigDecimal;

public class FixedQuota extends PricingAbstract {

    private int extraQuotaLimit;
    private BigDecimal extraQuotaFee;

    public FixedQuota(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit, int extraQuotaLimit, BigDecimal extraQuotaFee) {
        super(companyName, initialMonthlyFee, initialQuotaLimit);
        this.extraQuotaLimit = extraQuotaLimit;
        this.extraQuotaFee = extraQuotaFee;
        this.extraQuotaLimit = extraQuotaLimit;
    }

    @Override
    public void increase1usedQuotaAmount() {
        this.usedQuotaAmount += 1;
        if(isTheQuotaFull()){
            this.currentQuotaLimit += this.extraQuotaLimit;
            this.currentMonthlyFee = this.currentMonthlyFee.add(this.extraQuotaFee);
        }
    }
}
