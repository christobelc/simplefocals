package com.github.simplefocals;

import com.github.simplefocals.entity.Product;
import com.github.simplefocals.repository.ProductRepository;
import com.github.simplefocals.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ViewProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testAllProductsWithCategoryId(){

        //find all by the categoryId = 4, category womens glasses
        List<Product> productsByCategory = productRepository.findAllByCategory_Id(4);

        for (Product product: productsByCategory) {
          System.out.println(product.toString());
        }
        //there is only two items for womens glasses
        assertThat(productsByCategory.size()).isEqualTo(2);
    }
}
