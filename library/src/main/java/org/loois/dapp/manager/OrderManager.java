package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.common.Constants;
import org.loois.dapp.model.OrderInfo;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.response.LooisSubmitOrder;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;
import org.loois.dapp.utils.BigDecimalUtil;
import org.loois.dapp.utils.HexHandleUtil;
import org.loois.dapp.utils.IBan;
import org.loois.dapp.utils.StringUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class OrderManager {


    /**
     * Create submit order params.
     * https://github.com/Loopring/relay/blob/wallet_v2/LOOPRING_RELAY_API_SPEC_V2.md#loopring_submitorder
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public SubmitOrderParams createSubmitOrderParams(String protocolAddress,
                                                     String delegateAddress,
                                                     String privateKey,
                                                      OrderInfo orderInfo,
                                                      boolean buyNoMoreThanAmountB,
                                                      int marginSplitPercentage,
                                                      String orderWalletAddress) throws Exception {
        int powNonce = 100; // hard code for now . proof of work
        long validSince = System.currentTimeMillis() / 1000;
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
        WalletFile wallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
        String authAddress = Constants.PREFIX_16 + wallet.getAddress();
        String s1 = Numeric.toHexStringNoPrefix(privateKeyInDec);
        String authPrivateKey = IBan.padLeft(s1, 64, '0');
        String amountHexS = Constants.PREFIX_16 +
                new BigDecimal(orderInfo.decimalS).multiply(new BigDecimal(orderInfo.amountS)).toBigInteger().toString(16);
        String amountHexB = Constants.PREFIX_16 +
                new BigDecimal(orderInfo.decimalB).multiply(new BigDecimal(orderInfo.amountB)).toBigInteger().toString(16);

        String lrcFee = "0";
        if (orderInfo.lrcDecimals != null) {
            lrcFee = BigDecimalUtil.mul(orderInfo.lrcFee, orderInfo.lrcDecimals.toString(), 0);
        }
        String finalLrcFee = HexHandleUtil.hexEncodeFee(lrcFee);
        byte[] message = Hash.sha3(
                Numeric.hexStringToByteArray(
                        getSubmitOrderMessage(
                                delegateAddress,
                                authAddress, orderInfo.owner,
                                orderInfo.protocolS, orderInfo.protocolB, amountHexS, amountHexB, finalLrcFee,
                                validSince, validSince + orderInfo.validateDuration,
                                buyNoMoreThanAmountB, marginSplitPercentage,
                                orderWalletAddress
                        ).toLowerCase()
                )
        );
        byte[] prefix = "\u0019Ethereum Signed Message:\n32".getBytes();
        byte[] hash = StringUtils.unitByteArray(prefix, message);

        ECKeyPair keyPair = Credentials.create(privateKey).getEcKeyPair();
        Sign.SignatureData sign = Sign.signMessage(hash, keyPair);

        int v = StringUtils.byteToInt(sign.getV());
        String r = Numeric.toHexStringNoPrefix(sign.getR());
        String s = Numeric.toHexStringNoPrefix(sign.getS());
        String finalPrivate = Constants.PREFIX_16 + authPrivateKey;
        return new SubmitOrderParams(
                protocolAddress, delegateAddress,
                orderInfo.owner, orderInfo.protocolS, orderInfo.protocolB, amountHexS, amountHexB,
                validSince, orderInfo.validateDuration,
                finalLrcFee, buyNoMoreThanAmountB, marginSplitPercentage,orderWalletAddress, v, r, s, powNonce, authAddress, finalPrivate
        );
    }

    private String getSubmitOrderMessage(
            String delegateAddress,
            String authAddress,
            String owner,
            String protocolS,
            String protocolB,
            String amountHexS,
            String amountHexB,
            String lrcFee,
            long validSince,
            long validUntil,
            boolean buyNoMoreThanAmountB,
            int marginSplitPercentage,
            String orderWalletAddress) {
        int length = 64;
        char zero = '0';
        return Numeric.cleanHexPrefix(delegateAddress) +
                Numeric.cleanHexPrefix(owner) +
                Numeric.cleanHexPrefix(protocolS) +
                Numeric.cleanHexPrefix(protocolB) +
                Numeric.cleanHexPrefix(orderWalletAddress) +
                Numeric.cleanHexPrefix(authAddress) +
                IBan.padLeft(Numeric.cleanHexPrefix(amountHexS), length, zero) +
                IBan.padLeft(Numeric.cleanHexPrefix(amountHexB), length, zero) +
                IBan.padLeft(Numeric.cleanHexPrefix(BigDecimal.valueOf(validSince).toBigInteger().toString(16)), length, zero) +
                IBan.padLeft(Numeric.cleanHexPrefix(BigDecimal.valueOf(validUntil).toBigInteger().toString(16)), length, zero) +
                IBan.padLeft(Numeric.cleanHexPrefix(lrcFee), length, zero) +
                (buyNoMoreThanAmountB ? "01" : "00") +
                Numeric.toHexStringNoPrefix(BigInteger.valueOf(marginSplitPercentage));
    }




    /**
     * Submit an order.
     * @param orderInfo User order information
     */
    public void submitOrder(OrderInfo orderInfo, String protocolAddress, String delegateAddress, String privateKey, String orderWalletAddress, LooisListener<String> listener) {
        Flowable.just(privateKey)
                .flatMap((Function<String, Flowable<LooisSubmitOrder>>) s -> {
                    SubmitOrderParams submitOrderParams = createSubmitOrderParams(protocolAddress, delegateAddress, privateKey, orderInfo,
                            true, 50, orderWalletAddress);
                    LooisSubmitOrder looisSubmitOrder = Loois.client().looisSubmitOrder(submitOrderParams).sendAsync().get();
                    return Flowable.just(looisSubmitOrder);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });

    }

}
