package org.loois.dapp.protocol.core.params;

import org.loois.dapp.common.Constants;

import java.math.BigDecimal;

public class SubmitOrderParams {

    /**
     * Loopring contract owner
     */
    public String protocol;
    /**
     * The loopring TokenTransferDelegate Protocol.
     */
    public String delegateAddress;
    /**
     * user's wallet owner
     */
    public String owner;
    /**
     * The wallet margin owner.
     */
    public String walletAddress;
    /**
     * Assets to sell.
     */
    public String tokenS;
    /**
     * Assets to buy.
     */
    public String tokenB;
    /**
     * Maximum amount of tokenS to sell.
     */
    public String amountS;
    /**
     * Minimum amount of tokenB to buy if all amountS sold.
     */
    public String amountB;
    /**
     * Indicating when this order is created.
     */
    public String validSince;
    /**
     * How long, in seconds, will this order live.
     */
    public String validUntil;
    /**
     * Max amount of LRC to pay for miner. The real amount to pay is proportional to fill amount.
     */
    public String lrcFee;
    /**
     * If true, this order does not accept buying more than amountB.
     */
    public boolean buyNoMoreThanAmountB;
    /**
     * The percentage of savings paid to miner.
     */
    public int marginSplitPercentage;
    /**
     * ECDSA signature parameter v.
     */
    public int v;
    /**
     * ECDSA signature parameter r.
     */
    public String r;
    /**
     * ECDSA signature parameter s.
     */
    public String s;

    /**
     * Order submitting must be verified by our pow check logic. If orders submitted exceeded in certain team, we will increase pow difficult.
     */
    public int powNonce;

    public String authAddr;

    public String authPrivateKey;

    public SubmitOrderParams(
            String protocolAddress,
            String delegateAddress,
            String owner,
            String tokenS,
            String tokenB,
            String amountS,
            String amountB,
            long validSince,
            long validUntil,
            String lrcFee,
            boolean buyNoMoreThanAmountB,
            int marginSplitPercentage,
            String orderWalletAddress,
            int v,
            String r,
            String s,
            int powNonce,
            String authAddr,
            String authPrivateKey) {
        this.protocol = protocolAddress;
        this.delegateAddress = delegateAddress;
        this.owner = owner;
        this.tokenS = tokenS;
        this.tokenB = tokenB;
        this.amountS = amountS;
        this.amountB = amountB;
        this.validSince = Constants.PREFIX_16 + BigDecimal.valueOf(validSince).toBigInteger().toString(16);
        this.validUntil = Constants.PREFIX_16 + BigDecimal.valueOf(validSince + validUntil).toBigInteger().toString(16);
        this.lrcFee = lrcFee;
        this.buyNoMoreThanAmountB = buyNoMoreThanAmountB;
        this.marginSplitPercentage = marginSplitPercentage;
        this.walletAddress = orderWalletAddress;
        this.v = v;
        this.r = r;
        this.s = s;
        this.powNonce = powNonce;
        this.authAddr = authAddr;
        this.authPrivateKey = authPrivateKey;
    }

    @Override
    public String toString() {
        return "SubmitOrderParams{" +
                "protocol='" + protocol + '\'' +
                ", delegateAddress='" + delegateAddress + '\'' +
                ", owner='" + owner + '\'' +
                ", walletAddress='" + walletAddress + '\'' +
                ", tokenS='" + tokenS + '\'' +
                ", tokenB='" + tokenB + '\'' +
                ", amountS='" + amountS + '\'' +
                ", amountB='" + amountB + '\'' +
                ", validSince=" + validSince +
                ", validUntil=" + validUntil +
                ", lrcFee='" + lrcFee + '\'' +
                ", buyNoMoreThanAmountB=" + buyNoMoreThanAmountB +
                ", marginSplitPercentage=" + marginSplitPercentage +
                ", v=" + v +
                ", r='" + r + '\'' +
                ", s='" + s + '\'' +
                ", powNonce=" + powNonce +
                ", authAddr='" + authAddr + '\'' +
                ", authPrivateKey='" + authPrivateKey + '\'' +
                '}';
    }
}