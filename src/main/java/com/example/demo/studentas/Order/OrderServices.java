package com.example.demo.studentas.Order;

import com.example.demo.studentas.Customer;
import com.example.demo.studentas.CustomerRepo;
import com.example.demo.studentas.MenuItems.Menu;
import com.example.demo.studentas.MenuItems.MenuRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final MenuRepo menuRepo;
    private final CustomerRepo customerRepo;

    public OrderServices(CustomerRepo customerRepo,OrderRepo orderRepo, OrderItemRepo orderItemRepo, MenuRepo menuRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.menuRepo = menuRepo;
        this.customerRepo=customerRepo;
    }


    public void addItemToCart(Long itemId, Cart cart) {
        Menu menuItem = menuRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"));

        OrderItem item = new OrderItem();
        item.setItemName(menuItem.getName());
        item.setPrice(menuItem.getPrice());
        item.setQuantity(1);


        cart.addItem(item);
    }
    public Long getCustomerIdByLogin(String login) {
        return customerRepo.findByLogin(login)
                .map(Customer::getId)
                .orElse(null);
    }

    @Transactional
    public void placeOrder(Cart cart, Long customerId) {
        OrderEntity order = new OrderEntity();
        order.setCustomerId(customerId);
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus("SENT");

        String itemName = cart.getItems().get(0).getItemName();
        Optional<Menu> menuInfo = menuRepo.findByName(itemName);
        menuInfo.ifPresent(menu -> order.setRestaurantId(menu.getRestaurantId()));

        OrderEntity savedOrder = orderRepo.save(order);

        for (OrderItem item : cart.getItems()) {
            item.setOrderId(savedOrder.getId());
            orderItemRepo.save(item);
        }
    }
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        return orderRepo.findAllByCustomerId(customerId);
    }
    @Transactional
    public void markAsDelivered(Long orderId) {
        orderRepo.findById(orderId).ifPresent(order -> {
            order.setStatus("DELIVERED");
            orderRepo.save(order);
        });
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order has not found"));
    }

    @Transactional
    public void saveOrder(OrderEntity order) {
        orderRepo.save(order);
    }



    @Transactional
    public void deleteOrderItem(Long itemId) {
        orderItemRepo.findById(itemId).ifPresent(item -> {
            Long orderId = item.getOrderId();
            double priceOfDeleted = item.getPrice() * item.getQuantity();


            orderItemRepo.delete(item);


            orderRepo.findById(orderId).ifPresent(order -> {
                order.setTotalPrice(order.getTotalPrice() - priceOfDeleted);
                orderRepo.save(order);
            });
        });
    }


    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepo.findAllByOrderId(orderId);
    }



    @Transactional
    public void updateOrderWithRealPrice(OrderEntity incomingOrder) {

        List<OrderItem> items = orderItemRepo.findAllByOrderId(incomingOrder.getId());


        double actualTotal = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();


        OrderEntity orderToUpdate = orderRepo.findById(incomingOrder.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));


        orderToUpdate.setStatus(incomingOrder.getStatus());
        orderToUpdate.setTotalPrice(actualTotal);

        orderRepo.save(orderToUpdate);
    }
    @Transactional
    public void decreaseItemQuantity(Long itemId) {
        orderItemRepo.findById(itemId).ifPresent(item -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                orderItemRepo.save(item);
            } else {

                orderItemRepo.delete(item);
            }

            updateOrderTotalPrice(item.getOrderId());
        });
    }

    private void updateOrderTotalPrice(Long orderId) {
        List<OrderItem> items = orderItemRepo.findAllByOrderId(orderId);
        double newTotal = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        orderRepo.findById(orderId).ifPresent(order -> {
            order.setTotalPrice(newTotal);
            orderRepo.save(order);
        });
    }

    @Transactional
    public void deleteOrderWithItems(Long orderId) {

        orderItemRepo.deleteByOrderId(orderId);

        orderRepo.deleteById(orderId);
    }

    @Transactional
    public void assignDriverAndDeliver(Long orderId, String driverLogin) {
        Long driverId = getCustomerIdByLogin(driverLogin);

        orderRepo.findById(orderId).ifPresent(order -> {
            order.setDriverId(driverId);
            order.setStatus("DELIVERED");
            orderRepo.save(order);
        });
    }
}