package org.loois.dapp.manager;

import org.loois.dapp.model.OrderInfo;

public class OrderManager {

    private static OrderManager sOrderManager;

    private OrderStatusChangeListener mOrderStatusChangeListener;


    private OrderManager() {
    }

    public static OrderManager getInstance() {
        if (sOrderManager == null) {
            synchronized (OrderManager.class) {
                if (sOrderManager == null) {
                    sOrderManager = new OrderManager();
                }
            }
        }
        return sOrderManager;
    }


    /**
     * Submit an order.
     * @param orderInfo User order information
     */
    public void submitOrder(OrderInfo orderInfo) {

    }


    /**
     * Cancel an order
     * @param orderInfo User order information
     */
    public void cancelOrder(OrderInfo orderInfo) {

    }

    /**
     * Check if token is authenticated.
     */
    public void  authenticate() {

    }



    public void ethExchangeWeth() {

    }

    public void wethExchangeEth() {

    }

    public interface OrderStatusChangeListener {

        void onInsufficientLrcFee(String lrcAmount, String lastFee);

        void onAuthenticateSuccess(OrderInfo orderInfo);

        void onAuthenticateFailed();

        void onSubmitSuccess();

        void onSubmitFailed();
    }

    public void setOrderStatusChangeListener(OrderStatusChangeListener listener) {
        mOrderStatusChangeListener = listener;
    }




}
