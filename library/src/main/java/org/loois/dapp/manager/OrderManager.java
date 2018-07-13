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

    public void order(OrderInfo orderInfo) {

    }

    /**
     *
     * @param orderInfo Order details
     * @return True if order token amount is higher than 1 dollar.
     */
    private boolean checkOrderAmount(OrderInfo orderInfo) {
        return true;
    }


    public interface OrderStatusChangeListener {

        void onOrderAmountLow();

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
