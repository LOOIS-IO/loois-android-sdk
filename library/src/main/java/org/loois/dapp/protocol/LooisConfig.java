package org.loois.dapp.protocol;

import org.web3j.tx.ChainId;

public class LooisConfig {

    public static final String BASE_URL = "http://api.loois.io/";

    public static final String ETH_URL = "https://relay1.loopring.io/";

    /**
     * The loopring TokenTransferDelegate Protocol.
     */
    public static final String DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";

    /**
     * Loopring contract owner
     */
    public static final String PROTOCOL_ADDRESS = "0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78";

    public static final String BIND_CONTRACT_ADDRESS = "0xbf78B6E180ba2d1404c92Fc546cbc9233f616C42";

    public static final String ORDER_WALLET_ADDRESS = "0x1D3e0DDFdc3D597C4f42b8a0F17d4e8C866B3485";

    public static final String CONTRACT_VERSION = "v1.51";

    public static final byte CHAIN_ID = ChainId.MAINNET;

}
