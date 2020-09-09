package org.mustafa.akilli.pricing;

import java.math.BigDecimal;

public class FlexibleQuota extends PricingAbstract {

    private BigDecimal extraUsageFee;

    public FlexibleQuota(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit, BigDecimal extraUsageFee) {
        super(companyName, initialMonthlyFee, initialQuotaLimit);
        this.extraUsageFee = extraUsageFee;
    }

    @Override
    public void increase1usedQuotaAmount() {
        this.usedQuotaAmount += 1;
        if(isTheQuotaFull()){
            this.currentMonthlyFee = this.currentMonthlyFee.add(this.extraUsageFee);
        }

    }
}
