package org.mustafa.akilli.pricing;

import org.mustafa.akilli.pricing.blacklist.BlackList;
import org.mustafa.akilli.pricing.blacklist.CompanyBlackList;
import org.mustafa.akilli.pricing.debt.Debt;
import org.mustafa.akilli.pricing.debt.DebtHistory;

import java.math.BigDecimal;


public abstract class PricingAbstract implements Pricing{
    protected BigDecimal currentMonthlyFee;
    protected BigDecimal initialMonthlyFee;
    protected int usedQuotaAmount;
    protected int currentQuotaLimit;
    protected int initialQuotaLimit;
    private BlackList companyBlackList;
    private Debt debtHistory = new DebtHistory();

    public PricingAbstract(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit) {
        this.initialMonthlyFee = initialMonthlyFee;
        this.initialQuotaLimit = initialQuotaLimit;
        this.companyBlackList = new CompanyBlackList(companyName);
    }

    @Override
    public boolean isTheQuotaFull() {
        return currentQuotaLimit > usedQuotaAmount;
    }

    @Override
    public void payCurrentMonthlyFee() {
        setcurrentMonthlyFeeToDefaultValue();
    }

    @Override
    public void dontPayCurrentMonthlyFee() {
        debtHistory.addNewDebt(currentMonthlyFee);
        if(companyBlackList.checkingWhetherTheElementShouldBeBlacklisted(debtHistory.getHowManyMonthsTheCompanyHasNotPaidItsDebt())){
            companyBlackList.addToBlackList();
        }
        setcurrentMonthlyFeeToDefaultValue();
    }

    private void setcurrentMonthlyFeeToDefaultValue(){
        currentMonthlyFee = initialMonthlyFee;
    }

    @Override
    public void payTotalDebt() {
        debtHistory.clearAllDebt();
        if(companyBlackList.isTheElementBlacklisted()){
            companyBlackList.removeFromBlackList();
        }
    }
}
