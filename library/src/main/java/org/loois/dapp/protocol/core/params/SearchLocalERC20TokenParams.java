package org.loois.dapp.protocol.core.params;

public class SearchLocalERC20TokenParams {
    public String key;
    public int pageIndex;

    public SearchLocalERC20TokenParams(String key, int pageIndex) {
        this.key = key;
        this.pageIndex = pageIndex;
    }
}