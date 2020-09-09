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

public class SmsDTOTest {

    private SmsDTO smsDTOFlexibleQuota;
    private FlexibleQuota flexibleQuota;

    private String firstCompanyName;
    private BigDecimal firstInitialMonthlyFee;
    private int firstInitialQuotaLimit;
    private BigDecimal firstExtraUsageFee;
    private Language firstLanguage;


    private SmsDTO smsDTOFixedQuota;
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
        // SMS Esnek Kotali: 2000 adet - 30TL, 2000 adetten sonra ki her SMS 0.10TL dir
        firstCompanyName = "Kardeşler Gıda";
        firstInitialMonthlyFee = new BigDecimal(30.00);
        firstInitialQuotaLimit = 2000;
        firstExtraUsageFee = new BigDecimal(0.10);
        firstLanguage = new Turkish();

        flexibleQuota = new FlexibleQuota(firstCompanyName,firstInitialMonthlyFee,firstInitialQuotaLimit,firstExtraUsageFee,firstLanguage);
        smsDTOFlexibleQuota = new SmsDTO(flexibleQuota,firstLanguage);


        // SMS Sabit Kotali: 1000 adet - 20TL ile sinirlidir. 1000 adeti asan gonderimde 20TL karsiliginda 1000 adet kota tekrar tanimlanir
        secondCompanyName = "Haburaya Ticaret";
        secondInitialMonthlyFee = new BigDecimal(20.00);
        secondInitialQuotaLimit = 1000;
        secondExtraQuotaLimit = 1000;
        secondExtraQuotaFee = new BigDecimal(20.00);
        secondLanguage = new English();

        fixedQuota = new FixedQuota(secondCompanyName,secondInitialMonthlyFee,secondInitialQuotaLimit,secondExtraQuotaLimit,secondExtraQuotaFee,secondLanguage);
        smsDTOFixedQuota = new SmsDTO(fixedQuota,secondLanguage);

        userCollection = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            userCollection.add(new IndividualUser("ignore@ignore.com","111-11-11", "ignore", "ignore oğlu"));
        }

        individualUser = new IndividualUser("ahmet@gmail.com","111-11-11", "ahmet", "ahmetoğlu");
    }

    @Test
    public void it_should_send_collective_notification_for_all_given_users_with_flexible_quota(){

        smsDTOFlexibleQuota.sendCollectiveNotification(userCollection);

        assertEquals(smsDTOFlexibleQuota.getPricing().getUsedQuotaAmount(),userCollection.size());
    }

    @Test
    public void it_should_send_collective_notification_for_all_given_users_with_fixed_quota(){

        smsDTOFixedQuota.sendCollectiveNotification(userCollection);

        assertEquals(smsDTOFixedQuota.getPricing().getUsedQuotaAmount(),userCollection.size());
    }

    @Test
    public void it_should_send_individual_notification_for_given_users_with_flexible_quota(){

        smsDTOFlexibleQuota.sendIndividualNotification(individualUser);

        assertEquals(smsDTOFlexibleQuota.getPricing().getUsedQuotaAmount(),1);
    }

    @Test
    public void it_should_send_individual_notification_for_given_users_with_fixed_quota(){

        smsDTOFixedQuota.sendIndividualNotification(individualUser);

        assertEquals(smsDTOFixedQuota.getPricing().getUsedQuotaAmount(),1);
    }

    @Test
    public void it_should_throw_CompanyOnTheBlacklistException_and_show_turkish_message_when_company_is_in_blacklist(){

        // Given
        CompanyBlackList.getInstance().addToBlackList(firstCompanyName);

        // When
        Throwable throwable = catchThrowable(() -> smsDTOFlexibleQuota.sendIndividualNotification(individualUser));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(CompanyOnTheBlacklistException.class);
        assertThat(throwable).hasMessageContaining(smsDTOFlexibleQuota.getLanguage().getCouldNotSentBecauseYouAreOnTheBlacklistString(smsDTOFlexibleQuota.getPricing().getCompanyName()));
    }

    @Test
    public void it_should_throw_CompanyOnTheBlacklistException_and_show_english_message_when_company_is_in_blacklist(){

        // Given
        CompanyBlackList.getInstance().addToBlackList(secondCompanyName);

        // When
        Throwable throwable = catchThrowable(() -> smsDTOFixedQuota.sendIndividualNotification(individualUser));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(CompanyOnTheBlacklistException.class);
        assertThat(throwable).hasMessageContaining(smsDTOFixedQuota.getLanguage().getCouldNotSentBecauseYouAreOnTheBlacklistString(smsDTOFixedQuota.getPricing().getCompanyName()));
    }

    @After
    public void tearDown(){
        flexibleQuota = null;
        fixedQuota = null;
        smsDTOFlexibleQuota = null;
        smsDTOFixedQuota = null;
        userCollection = null;
        CompanyBlackList.getInstance().clearBlacklist();
    }
}
