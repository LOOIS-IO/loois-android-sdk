package org.loois.dapp.protocol.core.response;

import org.loois.dapp.model.OriginalOrder;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018-4-18
 * Brief Desc :
 * </pre>
 */
public class Order {


    /**
     * originalOrder : {"protocol":"0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78","delegateAddress":"0x17233e07c67d086464fD408148c3ABB56245FA64","owner":"0x2915A80711C808723a188C61b3C27F9e0387e9A2","hash":"0x9e73ff615d0d0898dd91cdc9505fb7d90d5fa99e67cad625e0fc1e1c9f5e7369","tokenS":"WETH","tokenB":"VITE","amountS":"0x377f8ecbc736000","amountB":"0x3aa7c433517ca80000","validSince":"0x5aea9a10","validUntil":"0x5aebeb90","lrcFee":"0x4fefa17b7240000","buyNoMoreThanAmountB":true,"marginSplitPercentage":"0x32","v":"0x1b","r":"0x39b21b3dde2cfcff38c145b75ecfe23a9df7cb661336d2f3201caec68d51e1c7","s":"0x69d56185a6d8184d568caec5a9e66444424ae0850514c16ab780a57cb0f2c8fe","walletAddress":"0xb94065482Ad64d4c2b9252358D746B39e820A582","authAddr":"0xfFc5Beb9e5A14b5FdEE17C2312A88775C6d77756","authPrivateKey":"0x562439de4737d94a72d759eaa45a2018f46bad3ad8b0a319d6bc7920c129ff90","market":"VITE-WETH","side":"buy","createTime":1525324316}
     * dealtAmountS : 0x377f4011bd520ea
     * dealtAmountB : 0x3aa7c433517ca80000
     * cancelledAmountS : 0x0
     * cancelledAmountB : 0x0
     * status : ORDER_FINISHED
     */

    public OriginalOrder originalOrder;
    public String dealtAmountS;
    public String dealtAmountB;
    public String cancelledAmountS;
    public String cancelledAmountB;
    public String status;

    public OriginalOrder getOriginalOrder() {
        return originalOrder;
    }

    public void setOriginalOrder(OriginalOrder originalOrder) {
        this.originalOrder = originalOrder;
    }

    public String getDealtAmountS() {
        return dealtAmountS;
    }

    public void setDealtAmountS(String dealtAmountS) {
        this.dealtAmountS = dealtAmountS;
    }

    public String getDealtAmountB() {
        return dealtAmountB;
    }

    public void setDealtAmountB(String dealtAmountB) {
        this.dealtAmountB = dealtAmountB;
    }

    public String getCancelledAmountS() {
        return cancelledAmountS;
    }

    public void setCancelledAmountS(String cancelledAmountS) {
        this.cancelledAmountS = cancelledAmountS;
    }

    public String getCancelledAmountB() {
        return cancelledAmountB;
    }

    public void setCancelledAmountB(String cancelledAmountB) {
        this.cancelledAmountB = cancelledAmountB;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
