package org.mustafa.akilli.pricing;

import org.mustafa.akilli.language.Language;

import java.math.BigDecimal;

public class FlexibleQuota extends PricingAbstract {

    private BigDecimal extraUsageFee;

    public FlexibleQuota(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit, BigDecimal extraUsageFee, Language language) {
        super(companyName, initialMonthlyFee, initialQuotaLimit, language);
        this.extraUsageFee = extraUsageFee;
    }

    @Override
    public void updateCurrentMonthlyFee() {
        if(isTheQuotaFull()){
            this.currentMonthlyFee = this.currentMonthlyFee.add(this.extraUsageFee).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
    }
}
