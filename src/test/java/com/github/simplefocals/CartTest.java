package com.github.simplefocals;

import com.github.simplefocals.entity.CartItem;
import com.github.simplefocals.entity.CustomerProfile;
import com.github.simplefocals.entity.Product;
import com.github.simplefocals.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CartTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveItem(){
        //set the customer Profile and the Product Id
        Integer customerProfileId = 1;
        Integer productId = 3;

        //use the entityManager to get the customer Profile
        CustomerProfile customerProfile = entityManager.find(CustomerProfile.class, customerProfileId);
        //use the entityManager to get the product
        Product product = entityManager.find(Product.class, productId);

        //make new item and set the attributes
        CartItem newItem = new CartItem();
        newItem.setCustomerProfile(customerProfile);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        //save the item
        CartItem savedItem = cartItemRepository.save(newItem);
        //assert that it exists
        assertThat(savedItem.getId()).isGreaterThan(0);
    }

    @Test
    public void testSave2Items(){
        Integer customerProfileId = 18;
        Integer customerProfileId2 = 19;
        Integer productId = 2;

        CustomerProfile customerProfile = entityManager.find(CustomerProfile.class, customerProfileId);
        Product product = entityManager.find(Product.class, productId);

        CartItem newItem1 = new CartItem();
        newItem1.setCustomerProfile(customerProfile);
        newItem1.setProduct(product);
        newItem1.setQuantity(2);

        CartItem newItem2 = new CartItem();
        newItem2.setCustomerProfile(new CustomerProfile(customerProfileId2));
        newItem2.setProduct(product);
        newItem2.setQuantity(3);

        Iterable<CartItem> iterable = cartItemRepository.saveAll(List.of(newItem1, newItem2));
    }

    @Test
    public void testFindByCustomer(){
        Integer customerProfileId = 18;
        List<CartItem> listItems = cartItemRepository.findByCustomerProfile(new CustomerProfile(customerProfileId));

        listItems.forEach(System.out::println);
        assertThat(listItems.size()).isEqualTo(2);
    }

    @Test
    public void testFindByCustomerProfileAndProduct(){
        Integer customerProfileId = 18;
        Integer productId = 2;

        CartItem cartItem = cartItemRepository.findByCustomerProfileAndProduct(new CustomerProfile(customerProfileId), new Product(productId));

        assertThat(cartItem).isNotNull();

        System.out.println(cartItem);
    }

    @Test
    public void testUpdateQuantity(){
        Integer customerProfileId = 18;
        Integer productId = 2;
        Integer quantity = 4;

        cartItemRepository.updateQuantity(quantity,customerProfileId, productId);

        CartItem cartItem = cartItemRepository.findByCustomerProfileAndProduct(new CustomerProfile(customerProfileId), new Product(productId));
        assertThat(cartItem.getQuantity()).isEqualTo(4);
    }

    @Test
    public void testDeleteByCustomerAndProduct(){
        Integer customerProfileId = 18;
        Integer productId = 2;

        cartItemRepository.deleteByCustomerProfileAndProduct(customerProfileId, productId);
        CartItem cartItem = cartItemRepository.findByCustomerProfileAndProduct(new CustomerProfile(customerProfileId), new Product(productId));
        assertThat(cartItem).isNull();

    }

}
