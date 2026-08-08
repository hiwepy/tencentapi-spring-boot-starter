package com.tencentcloud.spring.boot.tim;

import lombok.Data;

/**
 * Base option holder for Tencent Cloud IM (TIM) shared by the bound
 * {@link com.tencentcloud.spring.boot.TencentTimProperties} and the
 * {@link TimInfoProvider} SPI. Carries the SdkAppid, administrator identifier,
 * private key and signature / message-lifetime defaults required by the
 * {@code TLSSigAPIv2} user-signature generator.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Data
public class TencentTimOption {

    /** Default administrator identifier used when none is configured. */
    public static final String ADMINISTRATOR = "administrator";

    /** Default signature expiry: 30 days, in seconds. */
    private static final long EXPIRE = 86400 * 30;

    /**
     * Administrator account identifier used to sign server-side REST API
     * requests (default {@value #ADMINISTRATOR}).
     */
    private String identifier = ADMINISTRATOR;

    /** TIM application SdkAppid obtained from the IM console. */
    private Long sdkappid;

    /** Private key used by {@code TLSSigAPIv2} to generate user signatures. */
    private String privateKey;

    /** User-signature validity in seconds (default 30 days). */
    private long expire = EXPIRE;

    /**
     * Offline message retention in seconds. Maximum is 7 days ({@code 604800}).
     * <ul>
     *   <li>{@code 0} &mdash; messages are delivered to online users only and not retained offline;</li>
     *   <li>values greater than {@code 604800} are clamped to {@code 604800};</li>
     *   <li>the default is {@code 604800} (7 days).</li>
     * </ul>
     */
    private long msgLifeTime = 604800;

}
