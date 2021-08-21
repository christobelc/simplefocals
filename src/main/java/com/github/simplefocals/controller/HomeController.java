package com.github.simplefocals.controller;

import com.github.simplefocals.entity.*;
import com.github.simplefocals.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;

@Controller
public class HomeController {

//    @Autowired
//    private QuantityService quantityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    //the homepage of the web application
    @GetMapping({"/", "/home"})
    public String viewIndex(Model model){
//-----Code fragment to get the cart number---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;

            List<Role> userRoles = user.getRoles();
            for (Role role: userRoles) {
                if(role.getId() == 1){
                    return "adminHome";
                }
            }
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------
        return "index";
    }

    //render the shop page
    @GetMapping("/shop")
    public String viewShop(Model model){
        //-----Code fragment to get the cart number---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "shop";
    }

    //filter by category
    @GetMapping("/shop/category/{id}")
    public String viewByCategories(@PathVariable int id, Model model){

        //-----Code fragment to get the cart number---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "shop";
    }

    //customer view product page
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model){
        //-----Code fragment to get the cart number---------
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals("anonymousUser")){
            User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
            CustomerProfile customerProfile;
            if(user.getCustomerProfile() == null){
                return "redirect:/profile";}
            else {
                customerProfile = user.getCustomerProfile();
            }
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //-----------------------------------------------

        model.addAttribute("quantityObj", new Quantity());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }
}
