package org.mustafa.akilli.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mustafa.akilli.channels.Channel;
import org.mustafa.akilli.channels.EmailDTO;
import org.mustafa.akilli.channels.SmsDTO;
import org.mustafa.akilli.exceptions.DuplicateChannelException;
import org.mustafa.akilli.language.Language;
import org.mustafa.akilli.language.Turkish;
import org.mustafa.akilli.pricing.FixedQuota;
import org.mustafa.akilli.pricing.FlexibleQuota;
import org.mustafa.akilli.pricing.blacklist.CompanyBlackList;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertTrue;

public class CorporateUserTest {

    private CorporateUser corporateUser;
    ArrayList<Channel> channels;
    private String companyName;
    private Language language;

    private EmailDTO emailDTOFlexibleQuota;
    private FlexibleQuota firstFlexibleQuota;

    private BigDecimal firstInitialMonthlyFee;
    private int firstInitialQuotaLimit;
    private BigDecimal firstExtraUsageFee;


    private EmailDTO emailDTOFixedQuota;
    private FixedQuota secondFixedQuota;

    private BigDecimal secondInitialMonthlyFee;
    private int secondInitialQuotaLimit;
    private int secondExtraQuotaLimit;
    private BigDecimal secondExtraQuotaFee;


    private SmsDTO smsDTOFixedQuota;
    private FixedQuota thirdFixedQuota;

    private BigDecimal thirdInitialMonthlyFee;
    private int thirdInitialQuotaLimit;
    private int thirdExtraQuotaLimit;
    private BigDecimal thirdExtraQuotaFee;


    private ArrayList<User> userCollection;

    @Before
    public void setUp(){
        companyName = "Kardeşler Gıda";
        channels = new ArrayList<>();

        // Email Esnek Kotali: 2000 adet - 7.5TL, 2000 adetten sonra ki her SMS 0.03TL dir
        firstInitialMonthlyFee = new BigDecimal(7.50);
        firstInitialQuotaLimit = 2000;
        firstExtraUsageFee = new BigDecimal(0.03);
        language = new Turkish();

        firstFlexibleQuota = new FlexibleQuota(companyName,firstInitialMonthlyFee,firstInitialQuotaLimit,firstExtraUsageFee,language);
        emailDTOFlexibleQuota = new EmailDTO(firstFlexibleQuota,language);


        // Email Sabit Kotali: 1000 adet - 10TL ile sinirlidir. 1000 adeti asan gonderimde 10TL karsiliginda 1000 adet kota tekrar tanimlanir
        secondInitialMonthlyFee = new BigDecimal(10.00);
        secondInitialQuotaLimit = 1000;
        secondExtraQuotaLimit = 1000;
        secondExtraQuotaFee = new BigDecimal(10.00);

        secondFixedQuota = new FixedQuota(companyName,secondInitialMonthlyFee,secondInitialQuotaLimit,secondExtraQuotaLimit,secondExtraQuotaFee,language);
        emailDTOFixedQuota = new EmailDTO(secondFixedQuota,language);

        // SMS Sabit Kotali: 1000 adet - 20TL ile sinirlidir. 1000 adeti asan gonderimde 20TL karsiliginda 1000 adet kota tekrar tanimlanir
        thirdInitialMonthlyFee = new BigDecimal(20.00);
        thirdInitialQuotaLimit = 1000;
        thirdExtraQuotaLimit = 1000;
        thirdExtraQuotaFee = new BigDecimal(20.00);

        thirdFixedQuota = new FixedQuota(companyName,thirdInitialMonthlyFee,thirdInitialQuotaLimit,thirdExtraQuotaLimit,thirdExtraQuotaFee,language);
        smsDTOFixedQuota = new SmsDTO(thirdFixedQuota,language);

        userCollection = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            userCollection.add(new IndividualUser("ignore@ignore.com","111-11-11", "ignore", "ignore oğlu"));
        }
    }

    @Test
    public void it_should_throw_DuplicateChannelException_when_the_company_chooses_more_than_one_from_the_same_channel(){

        // Given
        channels.add(emailDTOFlexibleQuota);
        channels.add(emailDTOFixedQuota);
        channels.add(smsDTOFixedQuota);

        // When
        Throwable throwable = catchThrowable(() -> corporateUser = new CorporateUser("ignore@kurumsal.com", "999-99-89", companyName,channels, language));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DuplicateChannelException.class);
        assertThat(throwable).hasMessageContaining(language.getDuplicateChannelErrorString());
    }

    @Test
    public void it_should_not_throw_DuplicateChannelException_and_show_when_the_company_dont_chooses_more_than_one_from_the_same_channel(){

        // Given
        channels.add(emailDTOFlexibleQuota);
        channels.add(smsDTOFixedQuota);

        // When
        corporateUser = new CorporateUser("ignore@kurumsal.com", "999-99-89", companyName,channels, language);

        // Then
        assertTrue( true );
    }

    @After
    public void tearDown(){
        corporateUser = null;
        firstFlexibleQuota = null;
        secondFixedQuota = null;
        thirdFixedQuota = null;
        emailDTOFlexibleQuota = null;
        emailDTOFlexibleQuota = null;
        smsDTOFixedQuota = null;
        userCollection = null;
        CompanyBlackList.getInstance().clearBlacklist();
    }

}
