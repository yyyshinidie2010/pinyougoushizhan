package pojogroup;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;

import java.io.Serializable;

public class OrderVO implements Serializable {
    private Order order;
    private OrderItem orderItem;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
