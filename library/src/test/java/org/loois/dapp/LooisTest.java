package org.loois.dapp;

import org.junit.Test;
import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.LooisFactory;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.response.LoopringDepth;
import org.loois.dapp.protocol.core.response.Token;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LooisTest {

    public static final String TEST_URL = "http://192.168.1.100:8083/";
    public static final String TEST_WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
    public static final String TEST_DELEGATE_ADDRESS = "0xc28766b782ad2873d7c39a725b88df6c0e753940";

    public static final String LOOPRING_URL = "https://relay1.loopring.io/rpc/v2/";
    public static final String LOOPRING_DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";


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

    @Test
    public void testLoopringDepth() {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        DepthParams params = new DepthParams("LRC-WETH", 20, LOOPRING_DELEGATE_ADDRESS);
        try {
            List<List<String>> sellList = loois.loopringDepth(params).sendAsync().get().getSellList();
            String market = loois.loopringDepth(params).sendAsync().get().getMarket();
            System.out.println(market);
            System.out.println(sellList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
