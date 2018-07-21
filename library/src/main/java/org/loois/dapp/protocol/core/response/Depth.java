package org.loois.dapp.protocol.core.response;

import java.util.List;

public class Depth {

    public DepthResult depth;
    public String market;
    public String delegateAddress;


    public static class DepthResult {
        public List<List<String>> buy;
        public List<List<String>> sell;
    }
}
