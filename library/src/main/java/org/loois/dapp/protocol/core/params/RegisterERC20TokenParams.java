package org.loois.dapp.protocol.core.params;

public class RegisterERC20TokenParams {
    public String symbol;
    public String protocol;
    public String owner;

    public RegisterERC20TokenParams(String symbol, String protocol, String owner) {
        this.symbol = symbol;
        this.protocol = protocol;
        this.owner = owner;
    }
}