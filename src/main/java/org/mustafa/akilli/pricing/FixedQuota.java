package org.mustafa.akilli.pricing;

import org.mustafa.akilli.exceptions.CompanyOnTheBlacklistException;
import org.mustafa.akilli.language.Language;

import java.math.BigDecimal;

public class FixedQuota extends PricingAbstract {

    private int extraQuotaLimit;
    private BigDecimal extraQuotaFee;

    public FixedQuota(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit, int extraQuotaLimit, BigDecimal extraQuotaFee, Language language) {
        super(companyName, initialMonthlyFee, initialQuotaLimit, language);
        this.extraQuotaLimit = extraQuotaLimit;
        this.extraQuotaFee = extraQuotaFee;
        this.extraQuotaLimit = extraQuotaLimit;
    }

    @Override
    public void updateCurrentMonthlyFee() {
        if(isTheQuotaFull()){
            this.currentQuotaLimit += this.extraQuotaLimit;
            this.currentMonthlyFee = this.currentMonthlyFee.add(this.extraQuotaFee);
        }
    }
}
