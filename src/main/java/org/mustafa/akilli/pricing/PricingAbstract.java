package org.mustafa.akilli.pricing;

import org.mustafa.akilli.exceptions.CompanyOnTheBlacklistException;
import org.mustafa.akilli.language.Language;
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
    protected String companyName;
    protected BlackList companyBlackList;
    protected Debt debtHistory = new DebtHistory();
    protected Language language;

    public PricingAbstract(String companyName, BigDecimal initialMonthlyFee, int initialQuotaLimit, Language language) {
        this.language = language;
        this.companyName = companyName;
        this.initialMonthlyFee = initialMonthlyFee;
        setcurrentMonthlyFeeToDefaultValue();
        this.initialQuotaLimit = initialQuotaLimit;
        this.currentQuotaLimit = initialQuotaLimit;
        this.companyBlackList = CompanyBlackList.getInstance();
    }

    @Override
    public boolean isTheQuotaFull() {
        return currentQuotaLimit <= usedQuotaAmount;
    }

    @Override
    public void payCurrentMonthlyFee() {
        setcurrentMonthlyFeeToDefaultValue();
    }

    @Override
    public void dontPayCurrentMonthlyFee() {
        debtHistory.addNewDebt(currentMonthlyFee);
        if(companyBlackList.checkingWhetherTheElementShouldBeBlacklisted(debtHistory.getHowManyMonthsTheCompanyHasNotPaidItsDebt())){
            companyBlackList.addToBlackList(this.companyName);
        }
        setcurrentMonthlyFeeToDefaultValue();
    }

    private void setcurrentMonthlyFeeToDefaultValue(){
        currentMonthlyFee = initialMonthlyFee;
    }

    @Override
    public void payTotalDebt() {
        debtHistory.clearAllDebt();
        if(companyBlackList.isTheElementBlacklisted(this.companyName)){
            companyBlackList.removeFromBlackList(this.companyName);
        }
    }

    @Override
    public void increaseTheUsedQuotaBy1() {
        this.usedQuotaAmount += 1;
    }

    @Override
    public int getUsedQuotaAmount() {
        return usedQuotaAmount;
    }

    @Override
    public BigDecimal getCurrentMonthlyFee() {
        return currentMonthlyFee;
    }

    @Override
    public Debt getDebtHistory() {
        return this.debtHistory;
    }

    @Override
    public String getCompanyName() {
        return this.companyName;
    }
}
