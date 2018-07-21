package org.loois.dapp.protocol.core.response;

public class Ring {


    /**
     * id : 1
     * protocol : 0xb1170dE31c7f72aB62535862C97F5209E356991b
     * delegateAddress : 0x5567ee920f7E62274284985D793344351A00142B
     * ringIndex : 0
     * ringHash : 0x3d58a550136668074648778c3ac6757df582c0df7c7af12c76f39d4a43be5054
     * txHash : 0x30940beef7acb033d10882861a469c19bb6c1aab9a15e6897d6bb0020d70ffa7
     * miner : 0x3ACDF3e3D8eC52a768083f718e763727b0210650
     * feeRecipient : 0x3ACDF3e3D8eC52a768083f718e763727b0210650
     * isRinghashReserved : false
     * blockNumber : 5469397
     * totalLrcFee : 323200000000000000
     * tradeAmount : 2
     * timestamp : 1524156383
     * Fork : false
     */

    public int id;
    public String protocol;
    public String delegateAddress;
    public String ringIndex;
    public String ringHash;
    public String txHash;
    public String miner;
    public String feeRecipient;
    public boolean isRinghashReserved;
    public int blockNumber;
    public String totalLrcFee;
    public int tradeAmount;
    public long timestamp;
    public boolean Fork;


    @Override
    public String toString() {
        return "Ring{" +
                "id=" + id +
                ", protocol='" + protocol + '\'' +
                ", delegateAddress='" + delegateAddress + '\'' +
                ", ringIndex='" + ringIndex + '\'' +
                ", ringHash='" + ringHash + '\'' +
                ", txHash='" + txHash + '\'' +
                ", miner='" + miner + '\'' +
                ", feeRecipient='" + feeRecipient + '\'' +
                ", isRinghashReserved=" + isRinghashReserved +
                ", blockNumber=" + blockNumber +
                ", totalLrcFee='" + totalLrcFee + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", timestamp=" + timestamp +
                ", Fork=" + Fork +
                '}';
    }
}
