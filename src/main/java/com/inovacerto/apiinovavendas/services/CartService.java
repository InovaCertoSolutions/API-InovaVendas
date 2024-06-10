package com.inovacerto.apiinovavendas.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.CartModel;
import com.inovacerto.apiinovavendas.models.CartProductsModel;
import com.inovacerto.apiinovavendas.respositories.CartProductRepository;
// import com.inovacerto.apiinovavendas.requests.CartRequest;
import com.inovacerto.apiinovavendas.respositories.CartRepository;

import jakarta.transaction.Transactional;
// import jakarta.validation.Valid;

@Service
public class CartService {

    final CartRepository repository;
    final CartProductRepository cartProductRepository;

    public CartService(CartRepository repository, CartProductRepository cartProductRepository) {
        this.repository = repository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public Object saveCart(CartModel model) {
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return repository.save(model);
    }

    @Transactional
    public Object updateCart(CartModel model) {
        model.setTotalPrice(this.calcTotalPriceValue(model));
        model.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return repository.save(model);
    }

    public Float calcTotalPriceValue(CartModel cartModel) {
        Float totalPrice = (float) 0;
        for (CartProductsModel cartProductsModel : cartModel.getProducts()) {
            totalPrice += cartProductsModel.getProduct().getSalePrice() * cartProductsModel.getQuantity();
        }

        return totalPrice;
    }

    public Page<CartModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<CartModel> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<CartProductsModel> findCartProductById(UUID id) {
        return cartProductRepository.findById(id);
    }

    @Transactional
    public void delete(CartModel model) {
        repository.delete(model);
    }

    @Transactional
    public Object saveCartProduct(CartProductsModel model) {
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return cartProductRepository.save(model);
    }

    @Transactional
    public Object updateCartProduct(CartProductsModel model) {
        model.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return cartProductRepository.save(model);
    }

    @Transactional
    public void deleteCartProduct(CartProductsModel model) {
        cartProductRepository.delete(model);
    }


}
