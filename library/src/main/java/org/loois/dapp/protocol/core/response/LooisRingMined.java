package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisRingMined extends Response<Page<Ring>> {

    public List<Ring> getMinedRings() {
        return getResult().data;
    }
}
