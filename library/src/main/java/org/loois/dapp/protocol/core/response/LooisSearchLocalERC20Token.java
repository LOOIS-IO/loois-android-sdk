package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisSearchLocalERC20Token extends Response<SearchToken>{

    public boolean isOK() {
        return getResult().isOk;
    }

    public List<SearchToken.TokensBean> getSearchTokens() {
        return getResult().tokens;
    }
}
