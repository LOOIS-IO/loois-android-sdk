package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisRegisterERC20Token extends Response<Erc20Token>{

    public Erc20Token getErc20Token() {
        return getResult();
    }
}
