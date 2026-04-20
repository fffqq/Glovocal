package com.example.demo.studentas.Order;

import com.example.demo.studentas.Admins.AdminServices;
import com.example.demo.studentas.CustServ;
import com.example.demo.studentas.Customer;
import com.example.demo.studentas.CustomerRepo;
import com.example.demo.studentas.MenuItems.Menu;
import com.example.demo.studentas.MenuItems.MenuRepo;
import com.example.demo.studentas.Restaurants.RestaurantEntity;
import com.example.demo.studentas.Restaurants.RestaurantInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderControl {
    private final CustomerRepo repo;
    private final CustServ custServ;
    private final RestaurantInterface restaurantInterface;
    private final MenuRepo Mrepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderRepo orderRepo;

    private final OrderServices orderServices;

    public OrderControl(OrderRepo orderRepo,OrderItemRepo orderItemRepo, CustomerRepo repo, CustServ custServ,
                        RestaurantInterface restaurantInterface, MenuRepo Mrepo,OrderServices orderServices) {
        this.orderItemRepo = orderItemRepo;
        this.repo = repo;
        this.custServ = custServ;
        this.restaurantInterface = restaurantInterface;
        this.Mrepo = Mrepo;
        this.orderRepo=orderRepo;
        this.orderServices=orderServices;
    }

    @PostMapping("/cart/add/{itemId}")
    public String addToCart(@PathVariable Long itemId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

         orderServices.addItemToCart(itemId, cart);

        Long restaurantId = Mrepo.findById(itemId)
                .map(Menu::getRestaurantId)
                .orElse(1L);

        return "redirect:/Restaurants/" + restaurantId;
    }
    @GetMapping("/Cart")
    public String showCart(HttpSession session, org.springframework.ui.Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        model.addAttribute("cart", cart);
        return "Cart";
    }

    @PostMapping("/order/confirm")
    public String confirmOrder(HttpSession session,
            @CookieValue(value = "user_login", required = false) String userLogin)
    {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            return "redirect:/Restaurants";
        }

        Long customerId = null;
        if (userLogin != null) {
            customerId = orderServices.getCustomerIdByLogin(userLogin);
        }

        if (customerId == null) {
            return "redirect:/login";
        }

        orderServices.placeOrder(cart, customerId);

        session.removeAttribute("cart");
        return "redirect:/my-orders";
    }

    @GetMapping("/my-orders")
    public String showMyOrders(
            HttpSession session,
            @CookieValue(value = "user_login", required = false) String userLogin,
            org.springframework.ui.Model model
    ) {
        Long customerId = null;

        if (userLogin != null) {
            customerId = orderServices.getCustomerIdByLogin(userLogin);
        }

        if (customerId == null) {
            return "redirect:/login";
        }


        List<OrderEntity> orders = orderServices.getOrdersByCustomerId(customerId);

        for (OrderEntity order : orders) {
            if (order.getRestaurantId() != null) {

                String name = restaurantInterface.findById(order.getRestaurantId())
                        .map(r -> r.getName())
                        .orElse("Unknown Restaurant");

                order.setRestaurantName(name);
            }
        }

        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @GetMapping("/my-orders/delivered/{id}")
    public String setDelivered(@PathVariable Long id) {
        orderServices.markAsDelivered(id);
        return "redirect:/my-orders";
    }


    @GetMapping("/my-orders/edit/{id}")
    public String editOrderPage(@PathVariable Long id, org.springframework.ui.Model model) {
        OrderEntity order = orderServices.getOrderById(id);
        List<OrderItem> items = orderServices.getOrderItems(id);

        model.addAttribute("order", order);
        model.addAttribute("items", items);


        return "edit-order";
    }

    @GetMapping("/my-orders/item/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {

        Long orderId = orderItemRepo.findById(itemId).get().getOrderId();
        orderServices.deleteOrderItem(itemId);
        return "redirect:/my-orders/edit/" + orderId;
    }

    @PostMapping("/my-orders/update")
    public String updateOrder(@ModelAttribute OrderEntity order) {
        orderServices.updateOrderWithRealPrice(order);

        return "redirect:/my-orders";
    }
    @GetMapping("/my-orders/item/decrease/{itemId}")
    public String decreaseItem(@PathVariable Long itemId) {
        Long orderId = orderItemRepo.findById(itemId)
                .map(OrderItem::getOrderId)
                .orElse(null);

        orderServices.decreaseItemQuantity(itemId);

        return (orderId != null) ? "redirect:/my-orders/edit/" + orderId : "redirect:/my-orders";
    }
}