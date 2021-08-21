package com.github.simplefocals.controller;

import com.github.simplefocals.exception.ShoppingCartException;
import com.github.simplefocals.entity.CartItem;
import com.github.simplefocals.entity.CustomerProfile;
import com.github.simplefocals.entity.Quantity;
import com.github.simplefocals.entity.User;
import com.github.simplefocals.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @Value("${stripe.public.key}")
    private String stripePublicKey;


    //Load the Stripe payment payment page
    @GetMapping("/payment")
    public String home(Model model){
        //get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //get the customer Profile
        CustomerProfile customerProfile = user.getCustomerProfile();
        //get the cartItems of the customer Profile
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //set the objects to the model and load the page
        model.addAttribute("email", customUserDetailsService.returnUsername());
        model.addAttribute("total", decimalFormat.format(shoppingCartService.totalCost(cartItemList)));
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "payment";
    }

    //Add to Cart
    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id,
                            @ModelAttribute("quantityObj") Quantity quantityObj,
                            Model model,
                            @ModelAttribute("value") Integer value)  {

        //we retrieve what user it is
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        //if the user has no customer profile
        if(user.getCustomerProfile() == null){
            //redirect to profile
            return "profile";
        }else {
            //set this customer to his customerprofile
            customerProfile = user.getCustomerProfile();
        }

        //get the value of quantity from the quantityObj attribute value
        model.addAttribute("value", value);

        //add the product to the database
        shoppingCartService.addProduct(value,id,customerProfile);

        //when added redirect to shop
        return "redirect:/shop";
    }

    //View the Cart
    @GetMapping("/cart")
    public String viewCart(Model model){

        //Get the Customer Profile info
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        if(user.getCustomerProfile() == null){
            return "profile";
        }else {
            customerProfile = user.getCustomerProfile();
        }

        //get the cart items for the Customer Profile
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        Integer totalQuantity = shoppingCartService.cartCount(cartItemList);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //Add to the view
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("cartCount", totalQuantity);
        model.addAttribute("total", decimalFormat.format(shoppingCartService.totalCost(cartItemList)));

        return "cart";
    }

    //Remove the cart item
    @GetMapping("/cart/removeItem/{index}")
    public String removeCartItem(@PathVariable int index){
        shoppingCartService.deleteItems(new CartItem(index));
        return "redirect:/cart";
    }

    //Checkout page
    @GetMapping("/checkout")
    public String checkout(Model model){
        //-------get the Customer Profile details -------------------------
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        if(user.getCustomerProfile() == null){
            return "profileAdd";
        }else {
            customerProfile = user.getCustomerProfile();
        }

        //-------Display to the view User details -------------------------
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());

        //cart item details.
        List<CartItem> cartItemList = shoppingCartService.listCartItems(customerProfile);
        Integer totalQuantity = shoppingCartService.cartCount(cartItemList);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //---------Display to the view ----------------------------------
        model.addAttribute("customerProfile", customerProfile);
        model.addAttribute("cartCount", totalQuantity);
        model.addAttribute("total", decimalFormat.format(shoppingCartService.totalCost(cartItemList)));

        return "checkout";
    }

}
