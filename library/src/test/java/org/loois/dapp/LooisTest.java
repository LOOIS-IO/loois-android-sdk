package org.loois.dapp;

import org.junit.Test;
import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.LooisFactory;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.Token;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;


public class LooisTest {

    public static final String TEST_URL = "http://192.168.1.100:8083/";
    public static final String TEST_WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
    public static final String TEST_DELEGATE_ADDRESS = "0xc28766b782ad2873d7c39a725b88df6c0e753940";


    @Test
    public void testLoopringBalance() {
        Loois loois = LooisFactory.build(new HttpService(TEST_URL));
        try {
            BalanceParams params = new BalanceParams(TEST_WALLET_ADDRESS, TEST_DELEGATE_ADDRESS);
            List<Token> tokens = loois.loopringBalance(params).send().getTokens();
            System.out.println("testLoopringBalance " + tokens.size());

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
