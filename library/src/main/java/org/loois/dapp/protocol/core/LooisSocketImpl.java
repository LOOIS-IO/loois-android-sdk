package org.loois.dapp.protocol.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.loois.dapp.protocol.Config;
import org.loois.dapp.protocol.LooisSocketApi;
import org.loois.dapp.protocol.core.socket.SocketBalanceBody;
import org.loois.dapp.protocol.secure.SSLSocketClient;
import org.loois.dapp.protocol.secure.TrustAllManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.ObjectMapperFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.OkHttpClient;

public class LooisSocketImpl implements LooisSocketApi {

    private Socket socket;

    private ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    private Map<String, ArrayList<SocketListener>> eventListeners = new HashMap<>();

    private Logger logger;

    public LooisSocketImpl() {
        initDefaultSocket();
        logger = LoggerFactory.getLogger(LooisSocketImpl.class);
    }

    public LooisSocketImpl(Socket socket) {
        this.socket = socket;
        logger = LoggerFactory.getLogger(LooisSocketImpl.class);
    }

    private void initDefaultSocket() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), new TrustAllManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{WebSocket.NAME};
        opts.forceNew = true;
        opts.upgrade = false;
        opts.reconnection = false;
        opts.callFactory = okHttpClient;
        opts.webSocketFactory = okHttpClient;
        try {
            socket = IO.socket(Config.BASE_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBalance(String owner) {
        SocketBalanceBody body = new SocketBalanceBody(owner);
        try {
            String json = objectMapper.writeValueAsString(body);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> socket.emit(SocketMethod.balance_req, json));
            } else {
                socket.emit(SocketMethod.balance_req, json);
            }

            socket.on(SocketMethod.balance_res, args -> {
                String jsonString = (String) args[0];
                ArrayList<SocketListener> socketListeners = eventListeners.get(SocketMethod.balance_res);
                if (socketListeners != null) {
                    for (SocketListener listener: socketListeners) {
                        listener.onBalance(jsonString);
                    }
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void offBalance() {
        socket.off(SocketMethod.balance_res);
        socket.emit(SocketMethod.balance_end, "", (Ack) args -> {
            logger.debug("server got it");
        });
    }

    public void registerBalanceListener(SocketListener socketListener) {
        ArrayList<SocketListener> balanceListeners = eventListeners.get(SocketMethod.balance_res);
        if (balanceListeners == null) {
            eventListeners.put(SocketMethod.balance_res, new ArrayList<>());
        }
        eventListeners.get(SocketMethod.balance_res).add(socketListener);
    }

    public void removeBalanceListener(SocketListener socketListener) {
        ArrayList<SocketListener> balanceListeners = eventListeners.get(SocketMethod.balance_res);
        if (balanceListeners != null) {
            balanceListeners.remove(socketListener);
        }
    }


}
