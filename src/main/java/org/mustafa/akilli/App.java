package org.mustafa.akilli;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        BigDecimal a = new BigDecimal(38.05);
        BigDecimal b = new BigDecimal(0.10);
        BigDecimal sum = a.add(b);
        NumberFormat.getInstance(new Locale("tr-TR"));
        System.out.println(NumberFormat.getCurrencyInstance().format(sum));
    }
}
