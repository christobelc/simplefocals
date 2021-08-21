package com.github.simplefocals.controller;

import com.github.simplefocals.dto.ProductDTO;
import com.github.simplefocals.entity.Category;
import com.github.simplefocals.entity.Product;
import com.github.simplefocals.service.CategoryService;
import com.github.simplefocals.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // setting a variable for the directory of the product images
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";


    //admin home page
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }


    //categories page for CMS
    @GetMapping("/admin/categories")
    public String viewCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/categories";
    }

    //Add Category page for CMS
    @GetMapping("/admin/categories/add")
    public String getAddCategory(Model model){
        //thymeleaf is looking for a "category" obj in the front-end
        // we define a new Category obj with alias "category"
        Category category = new Category();
        model.addAttribute("category", category);
        return "categoriesAdd";
    }

    //Add Category
    @PostMapping("/admin/categories/add")
    public String postAddCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    //Delete Category
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    //Products page CMS
    @GetMapping("/admin/products")
    public String viewProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    //Add Products Page
    @GetMapping("/admin/products/add")
    public String getAddProducts(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "productsAdd";
    }

    //Add Product
    @PostMapping("/admin/products/add")
    public String postAddProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile multipartFile,
                                  @RequestParam("imgName") String imageName) throws IOException {

//       Use the productDTO obj to transfer the attributes to product object
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());

//        we need to pass the category to the product
//        the ProductDTO only has the category id
        Integer categoryId = productDTO.getCategoryId();
        String categoryName = productDTO.getName();

        // set the attributes for the product
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());

        //Name of the image
        String imageIdentifier;
        //if the multipartFile object is not empty in passing from the front-end
        if(!multipartFile.isEmpty()){
            imageIdentifier = multipartFile.getOriginalFilename();
            // this creates the path where the image is to be uploaded to the system
            Path fileNameAndPath = Paths.get(uploadDir, imageIdentifier);
            //this writes the actual file and uploads to the system
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }else {
            //the file is empty
            imageIdentifier = imageName;
        }
        //set the last attributes of the product
        product.setImageName(imageIdentifier);
        //save the object to db
        productService.addProduct(product);

        //redirect to products page
        return "redirect:/admin/products";
    }

    //Delete Product
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }


}
