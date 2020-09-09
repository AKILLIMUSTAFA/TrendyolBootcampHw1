package org.mustafa.akilli.channels;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mustafa.akilli.exceptions.CompanyOnTheBlacklistException;
import org.mustafa.akilli.language.English;
import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.language.Turkish;
import org.mustafa.akilli.pricing.FixedQuota;
import org.mustafa.akilli.pricing.FlexibleQuota;
import org.mustafa.akilli.pricing.blacklist.CompanyBlackList;
import org.mustafa.akilli.user.IndividualUser;
import org.mustafa.akilli.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class EmailDTOTest {
    private EmailDTO emailDTOFlexibleQuota;
    private FlexibleQuota flexibleQuota;

    private String firstCompanyName;
    private BigDecimal firstInitialMonthlyFee;
    private int firstInitialQuotaLimit;
    private BigDecimal firstExtraUsageFee;
    private Language firstLanguage;


    private EmailDTO emailDTOFixedQuota;
    private FixedQuota fixedQuota;

    private String secondCompanyName;
    private BigDecimal secondInitialMonthlyFee;
    private int secondInitialQuotaLimit;
    private int secondExtraQuotaLimit;
    private BigDecimal secondExtraQuotaFee;
    private Language secondLanguage;

    private ArrayList<User> userCollection;
    private User individualUser;


    @Before
    public void setUp(){
        // Email Esnek Kotali: 2000 adet - 7.5TL, 2000 adetten sonra ki her SMS 0.03TL dir
        firstCompanyName = "Kardeşler Gıda";
        firstInitialMonthlyFee = new BigDecimal(7.50);
        firstInitialQuotaLimit = 2000;
        firstExtraUsageFee = new BigDecimal(0.03);
        firstLanguage = new Turkish();

        flexibleQuota = new FlexibleQuota(firstCompanyName,firstInitialMonthlyFee,firstInitialQuotaLimit,firstExtraUsageFee,firstLanguage);
        emailDTOFlexibleQuota = new EmailDTO(flexibleQuota,firstLanguage);


        // Email Sabit Kotali: 1000 adet - 10TL ile sinirlidir. 1000 adeti asan gonderimde 10TL karsiliginda 1000 adet kota tekrar tanimlanir
        secondCompanyName = "Haburaya Ticaret";
        secondInitialMonthlyFee = new BigDecimal(10.00);
        secondInitialQuotaLimit = 1000;
        secondExtraQuotaLimit = 1000;
        secondExtraQuotaFee = new BigDecimal(10.00);
        secondLanguage = new English();

        fixedQuota = new FixedQuota(secondCompanyName,secondInitialMonthlyFee,secondInitialQuotaLimit,secondExtraQuotaLimit,secondExtraQuotaFee,secondLanguage);
        emailDTOFixedQuota = new EmailDTO(fixedQuota,secondLanguage);

        userCollection = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            userCollection.add(new IndividualUser("ignore@ignore.com","111-11-11", "ignore", "ignore oğlu"));
        }

        individualUser = new IndividualUser("ahmet@gmail.com","111-11-11", "ahmet", "ahmetoğlu");
    }

    @Test
    public void it_should_send_collective_notification_for_all_given_users_with_flexible_quota(){

        emailDTOFlexibleQuota.sendCollectiveNotification(userCollection);

        assertEquals(emailDTOFlexibleQuota.getPricing().getUsedQuotaAmount(),userCollection.size());
    }

    @Test
    public void it_should_send_collective_notification_for_all_given_users_with_fixed_quota(){

        emailDTOFixedQuota.sendCollectiveNotification(userCollection);

        assertEquals(emailDTOFixedQuota.getPricing().getUsedQuotaAmount(),userCollection.size());
    }

    @Test
    public void it_should_send_individual_notification_for_given_users_with_flexible_quota(){

        emailDTOFlexibleQuota.sendIndividualNotification(individualUser);

        assertEquals(emailDTOFlexibleQuota.getPricing().getUsedQuotaAmount(),1);
    }

    @Test
    public void it_should_send_individual_notification_for_given_users_with_fixed_quota(){

        emailDTOFixedQuota.sendIndividualNotification(individualUser);

        assertEquals(emailDTOFixedQuota.getPricing().getUsedQuotaAmount(),1);
    }

    @Test
    public void it_should_throw_CompanyOnTheBlacklistException_and_show_turkish_message_when_company_is_in_blacklist(){

        // Given
        CompanyBlackList.getInstance().addToBlackList(firstCompanyName);

        // When
        Throwable throwable = catchThrowable(() -> emailDTOFlexibleQuota.sendIndividualNotification(individualUser));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(CompanyOnTheBlacklistException.class);
        assertThat(throwable).hasMessageContaining(emailDTOFlexibleQuota.getLanguage().getCouldNotSentBecauseYouAreOnTheBlacklistString(emailDTOFlexibleQuota.getPricing().getCompanyName()));
    }

    @Test
    public void it_should_throw_CompanyOnTheBlacklistException_and_show_english_message_when_company_is_in_blacklist(){

        // Given
        CompanyBlackList.getInstance().addToBlackList(secondCompanyName);

        // When
        Throwable throwable = catchThrowable(() -> emailDTOFixedQuota.sendIndividualNotification(individualUser));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(CompanyOnTheBlacklistException.class);
        assertThat(throwable).hasMessageContaining(emailDTOFixedQuota.getLanguage().getCouldNotSentBecauseYouAreOnTheBlacklistString(emailDTOFixedQuota.getPricing().getCompanyName()));
    }

    @After
    public void tearDown(){
        flexibleQuota = null;
        fixedQuota = null;
        emailDTOFlexibleQuota = null;
        emailDTOFixedQuota = null;
        userCollection = null;
        CompanyBlackList.getInstance().clearBlacklist();
    }
}
