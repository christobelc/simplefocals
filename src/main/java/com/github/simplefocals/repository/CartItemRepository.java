package com.github.simplefocals.repository;

import com.github.simplefocals.entity.CartItem;
import com.github.simplefocals.entity.CustomerProfile;
import com.github.simplefocals.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    public CartItem findByCustomerProfileAndProduct(CustomerProfile customerProfile, Product product);

    public List<CartItem> findByCustomerProfile(CustomerProfile customerProfile);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.customerProfile.id = ?2 AND c.product.id = ?3")
    public void updateQuantity(Integer quantity, Integer customerProfileId, Integer productId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customerProfile.id =?1 AND c.product.id = ?2")
    public void deleteByCustomerProfileAndProduct(Integer customerProfile, Integer product);
}
