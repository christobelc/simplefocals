package com.github.simplefocals.controller;

import com.github.simplefocals.dto.CustomerProfileDTO;
import com.github.simplefocals.entity.CustomerProfile;
import com.github.simplefocals.entity.User;
import com.github.simplefocals.service.CustomUserDetailsService;
import com.github.simplefocals.service.CustomerProfileService;
import com.github.simplefocals.service.ShoppingCartService;
import com.github.simplefocals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProfileController {
    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    // setting a variable for the directory of the product images
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/prescriptionImages";


    @GetMapping("/profile")
    public String viewProfile(Model model){
        //output the existing details

        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());

        if(user.getCustomerProfile() != null){

            CustomerProfile customerProfile = customerProfileService.getCustomerProfile(user.getCustomerProfile().getId()).get();
            model.addAttribute("customerProfile", customerProfile);
            Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        return "profile";
    }

    @GetMapping("/profile/add")
    public String getAddCustomerProfile(Model model){
        //get the current user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        //output the existing details
        model.addAttribute("lastName", customUserDetailsService.returnLastName());
        model.addAttribute("firstName", customUserDetailsService.returnFirstName());
        model.addAttribute("email", customUserDetailsService.returnUsername());

        //the data transfer object will hold the attrbutes of the CustomerProfile
        model.addAttribute("customerProfileDTO", new CustomerProfileDTO());

        // if user already has a CustomerProfile, display the cart number
        if(user.getCustomerProfile() != null){
            CustomerProfile customerProfile = customerProfileService.getCustomerProfile(
                                                user.getCustomerProfile().getId()).get();
            Integer totalQuantity = shoppingCartService.cartCount(
                                                shoppingCartService.listCartItems(customerProfile));
            model.addAttribute("cartCount", totalQuantity);
        }
        //display the profile add page
        return "profileAdd";
    }

    @PostMapping("/profile/add")
    public String postAddCustomerProfile(@ModelAttribute("customerProfileDTO") CustomerProfileDTO customerProfileDTO,
                                  @RequestParam("prescriptionImage") MultipartFile multipartFile,
                                  @RequestParam("imgName") String prescriptionImgName) throws IOException{

        // get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        CustomerProfile customerProfile;
        //if customerProfile exists then assign this user to this customerProfile
        if(user.getCustomerProfile() != null){
            Integer customerId = user.getCustomerProfile().getId();
            customerProfile = customerProfileService.getCustomerProfile(customerId).get();
        }else{
            //initalise the customerProfile
            customerProfile = new CustomerProfile();
            //set and id
            customerProfile.setId(customerProfileDTO.getId());
        }
        //set all the attributes
        customerProfile.setEmail(user.getEmail());
        customerProfile.setAddress(customerProfileDTO.getAddress());
        customerProfile.setCounty(customerProfileDTO.getCounty());
        customerProfile.setPostcode(customerProfileDTO.getPostcode());
        customerProfile.setRightEye(customerProfileDTO.getRightEye());
        customerProfile.setLeftEye(customerProfileDTO.getLeftEye());

        //setting a variable for the name
        String imageIndenifier;

        //if the multipartFile object is not empty in passing from the front-end
        if(!multipartFile.isEmpty()){
            //assign the filename
            imageIndenifier = multipartFile.getOriginalFilename();
            // this creates the path where the image is to be uploaded to the system
            Path fileNameAndPath = Paths.get(uploadDir, imageIndenifier);
            //this writes the actual file and uploads to the system
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }else {
            //the file is empty
            imageIndenifier = prescriptionImgName;
        }
        customerProfile.setPrescriptionImgName(imageIndenifier);

        //this writes to the db
        customerProfileService.addCustomerProfile(customerProfile);
        //this sets the foreign key on the Users table linking the new entry
        user.setCustomerProfile(customerProfile);
        //set the CustomerProfile as the this user
        customerProfile.setUser(user);
        //save the suer
        userService.saveUser(user);
        //redirect back to the profile
        return "redirect:/profile";
    }

    @GetMapping("/profile/update")
    public String getUpdateCustomerProfile(Model model){
        //get the user
        User user = userService.getUserByUsername(customUserDetailsService.returnUsername());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("email", user.getEmail());
        //get the user's customer profile
        CustomerProfile customerProfile = customerProfileService.getCustomerProfile(user.getCustomerProfile().getId()).get();

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
        customerProfileDTO.setAddress(customerProfile.getAddress());
        customerProfileDTO.setCounty(customerProfile.getCounty());
        customerProfileDTO.setPostcode(customerProfile.getPostcode());
        customerProfileDTO.setLeftEye(customerProfile.getLeftEye());
        customerProfileDTO.setRightEye(customerProfile.getRightEye());
        customerProfileDTO.setPrescriptionImgName(customerProfile.getPrescriptionImgName());

        Integer totalQuantity = shoppingCartService.cartCount(shoppingCartService.listCartItems(customerProfile));
        model.addAttribute("cartCount", totalQuantity);
        model.addAttribute("customerProfileDTO", customerProfileDTO);

        return "profileAdd";
    }
}
