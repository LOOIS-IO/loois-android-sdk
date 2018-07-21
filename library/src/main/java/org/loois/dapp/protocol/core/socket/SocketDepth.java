package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.Depth;

public class SocketDepth extends SocketResponse<Depth>{

    public Depth.DepthResult getDepth() {
        return data.depth;
    }
}
