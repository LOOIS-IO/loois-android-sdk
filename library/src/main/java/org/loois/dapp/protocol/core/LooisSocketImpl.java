package org.loois.dapp.protocol.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;
import org.loois.dapp.protocol.LooisSocketApi;
import org.loois.dapp.protocol.core.socket.DepthBody;
import org.loois.dapp.protocol.core.socket.MarketCapBody;
import org.loois.dapp.protocol.core.socket.OwnerBody;
import org.loois.dapp.protocol.core.socket.SocketBalance;
import org.loois.dapp.protocol.core.socket.BalanceBody;
import org.loois.dapp.protocol.core.socket.SocketDepth;
import org.loois.dapp.protocol.core.socket.SocketLooisTickers;
import org.loois.dapp.protocol.core.socket.SocketMarketCap;
import org.loois.dapp.protocol.core.socket.SocketPendingTx;
import org.loois.dapp.protocol.core.socket.SocketTickers;
import org.loois.dapp.protocol.core.socket.TickersBody;
import org.loois.dapp.protocol.secure.SSLSocketClient;
import org.loois.dapp.protocol.secure.TrustAllManager;
import org.web3j.protocol.ObjectMapperFactory;

import java.io.IOException;
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

    private static final String TAG = "LooisSocketImpl";


    private Socket socket;

    private ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    private Map<String, ArrayList<SocketListener>> eventListeners = new HashMap<>();


    public LooisSocketImpl() {
        initDefaultSocket();
    }

    public LooisSocketImpl(Socket socket) {
        this.socket = socket;
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
            socket = IO.socket(Loois.getOptions().getBaseUrl(), opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBalance(String owner) {
        BalanceBody body = new BalanceBody(owner);
        try {
            String json = objectMapper.writeValueAsString(body);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> {
                    socket.emit(SocketMethod.balance_req, json);
                });
            } else {
                socket.emit(SocketMethod.balance_req, json);
            }

            socket.on(SocketMethod.balance_res, args -> {
                try {
                    String jsonString = (String) args[0];
                    SocketBalance socketBalance = objectMapper.readValue(jsonString, SocketBalance.class);
                    ArrayList<SocketListener> socketListeners = eventListeners.get(SocketMethod.balance_res);
                    if (socketListeners != null) {
                        for (SocketListener listener : socketListeners) {
                            listener.onBalance(socketBalance);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void offBalance() {
        socket.off(SocketMethod.balance_res);
        socket.emit(SocketMethod.balance_end, "", (Ack) args -> {
        });
    }

    @Override
    public void registerBalanceListener(SocketListener socketListener) {
        addListener(SocketMethod.balance_res, socketListener);
    }

    @Override
    public void removeBalanceListener(SocketListener socketListener) {
        removeListener(SocketMethod.balance_res, socketListener);
    }

    @Override
    public void onPendingTx(String owner) {
        OwnerBody body = new OwnerBody(owner);
        try {
            String json = objectMapper.writeValueAsString(body);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> {
                    socket.emit(SocketMethod.pendingTx_req, json);
                });
            } else {
                socket.emit(SocketMethod.pendingTx_req, json);
            }
            socket.on(SocketMethod.pendingTx_res, args -> {
                String jsonString = (String) args[0];
                try {
                    SocketPendingTx socketTransaction = objectMapper.readValue(jsonString, SocketPendingTx.class);
                    ArrayList<SocketListener> listeners = eventListeners.get(SocketMethod.pendingTx_res);
                    if (listeners != null) {
                        for (SocketListener listener : listeners) {
                            listener.onPendingTx(socketTransaction);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offPendingTx() {
        socket.off(SocketMethod.pendingTx_res);
        socket.emit(SocketMethod.pendingTx_end);
    }

    @Override
    public void registerPendingTxListener(SocketListener listener) {
        addListener(SocketMethod.pendingTx_res, listener);
    }

    @Override
    public void removePendingTxListener(SocketListener listener) {
        removeListener(SocketMethod.pendingTx_res, listener);
    }


    @Override
    public void onMarketCap(String currency) {
        MarketCapBody marketCapBody = new MarketCapBody(currency);
        try {
            String json = objectMapper.writeValueAsString(marketCapBody);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> {
                    socket.emit(SocketMethod.marketcap_req, json);
                });
            } else {
                socket.emit(SocketMethod.marketcap_req, json);
            }
            socket.on(SocketMethod.marketcap_res, args -> {
                String jsonString = (String) args[0];
                try {
                    SocketMarketCap socketMarketCap = objectMapper.readValue(jsonString, SocketMarketCap.class);
                    ArrayList<SocketListener> listeners = eventListeners.get(SocketMethod.marketcap_res);
                    if (listeners != null) {
                        for (SocketListener listener : listeners) {
                            listener.onMarketCap(socketMarketCap);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offMarketCap() {
        socket.off(SocketMethod.marketcap_res);
        socket.emit(SocketMethod.marketcap_end);
    }

    @Override
    public void registerMarketCapListener(SocketListener listener) {
        addListener(SocketMethod.marketcap_res, listener);
    }

    @Override
    public void removeMarketCapListener(SocketListener listener) {
        removeListener(SocketMethod.marketcap_res, listener);
    }

    @Override
    public void onDepth(String market) {
        DepthBody depthBody = new DepthBody(market);
        try {
            String json = objectMapper.writeValueAsString(depthBody);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> {
                    socket.emit(SocketMethod.depth_req, json);
                });
            } else {
                socket.emit(SocketMethod.depth_req, json);
            }
            socket.on(SocketMethod.depth_res, args -> {
                String jsonString = (String) args[0];
                try {
                    SocketDepth socketDepth = objectMapper.readValue(jsonString, SocketDepth.class);
                    ArrayList<SocketListener> listeners = eventListeners.get(SocketMethod.depth_res);
                    if (listeners != null) {
                        for (SocketListener listener : listeners) {
                            listener.onDepth(socketDepth);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offDepth() {
        socket.off(SocketMethod.depth_res);
        socket.emit(SocketMethod.depth_end);
    }


    @Override
    public void registerDepthListener(SocketListener listener) {
        addListener(SocketMethod.depth_res, listener);
    }

    @Override
    public void removeDepthListener(SocketListener listener) {
        removeListener(SocketMethod.depth_res, listener);
    }

    @Override
    public void onTickers(String market) {
        TickersBody tickersBody = new TickersBody(market);
        try {
            String json = objectMapper.writeValueAsString(tickersBody);
            if (!socket.connected()) {
                socket.connect();
                socket.on(Socket.EVENT_CONNECT, args -> {
                    socket.emit(SocketMethod.tickers_req, json);
                });
            } else {
                socket.emit(SocketMethod.tickers_req, json);
            }
            socket.on(SocketMethod.tickers_res, args -> {
                try {
                    String jsonString = (String) args[0];
                    SocketTickers socketTickersResponse = objectMapper.readValue(jsonString, SocketTickers.class);
                    ArrayList<SocketListener> listeners = eventListeners.get(SocketMethod.tickers_res);
                    if (listeners != null) {
                        for(SocketListener listener: listeners) {
                            listener.onTickers(socketTickersResponse);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void offTickers() {
        socket.off(SocketMethod.tickers_res);
        socket.emit(SocketMethod.tickers_end);
    }

    @Override
    public void registerTickersListener(SocketListener listener) {
       addListener(SocketMethod.tickers_res, listener);
    }

    @Override
    public void removeTickersListener(SocketListener listener) {
        removeListener(SocketMethod.tickers_res, listener);
    }

    @Override
    public void onLooisTickers() {
        if (!socket.connected()) {
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, args -> socket.emit(SocketMethod.loopringTickers_req, "{}"));
        } else {
            socket.emit(SocketMethod.loopringTickers_req, "{}");
        }
        socket.on(SocketMethod.loopringTickers_res, args -> {
            String jsonString = (String) args[0];
            try {
                SocketLooisTickers socketLooisTickers = objectMapper.readValue(jsonString, SocketLooisTickers.class);
                ArrayList<SocketListener> listeners = eventListeners.get(SocketMethod.loopringTickers_res);
                Loois.log("onLooisTickers res " + jsonString);
                if (listeners != null) {
                    for (SocketListener listener: listeners) {
                        listener.onLooisTickers(socketLooisTickers);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void offLooisTickers() {
        socket.off(SocketMethod.loopringTickers_res);
        socket.emit(SocketMethod.loopringTickers_end);
    }

    @Override
    public void registerLooisTickersListener(SocketListener listener) {
        addListener(SocketMethod.loopringTickers_res, listener);
    }

    @Override
    public void removeLooisTickersListener(SocketListener listener) {
        removeListener(SocketMethod.loopringTickers_res, listener);
    }

    private void addListener(String key, SocketListener listener) {
        ArrayList<SocketListener> listeners = eventListeners.get(key);
        if (listeners == null) {
            eventListeners.put(key, new ArrayList<>());
        }
        eventListeners.get(key).add(listener);
    }

    private void removeListener(String key, SocketListener listener) {
        ArrayList<SocketListener> listeners = eventListeners.get(key);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }
}
