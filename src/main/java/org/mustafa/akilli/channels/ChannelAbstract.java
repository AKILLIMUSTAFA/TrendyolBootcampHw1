package org.mustafa.akilli.channels;

import org.mustafa.akilli.pricing.Pricing;

public abstract class ChannelAbstract implements Channel{
    protected Pricing pricing;

    public ChannelAbstract(Pricing pricing) {
        this.pricing = pricing;
    }
}
