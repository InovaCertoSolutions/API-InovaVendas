package com.inovacerto.apiinovavendas.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovacerto.apiinovavendas.models.CartModel;
import com.inovacerto.apiinovavendas.models.CartProductsModel;
import com.inovacerto.apiinovavendas.models.ProductModel;
import com.inovacerto.apiinovavendas.requests.AddToCartRequest;
import com.inovacerto.apiinovavendas.services.CartService;
import com.inovacerto.apiinovavendas.services.ProductService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/carts")
public class CartController {

    final CartService service;
    final ProductService productService;

    public CartController(CartService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<CartModel>> search (@PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> searchById (@PathVariable(value = "uuid") UUID id) {
        Optional<CartModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(search.get());
    }

    @PostMapping
    public ResponseEntity<Object> store() {
        var model = new CartModel();
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveCart(model));
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<Object> addToCart(@PathVariable(value = "uuid") UUID cartId, @RequestBody @Valid AddToCartRequest request) {
        Optional<CartModel> cart = this.service.findById(cartId);
        if (!cart.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho não encontrado");
        }

        Optional<ProductModel> product = this.productService.findById(request.getProduct());
        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        var cartModel = cart.get();
        var cartProductModel = new CartProductsModel();

        cartProductModel.setCart(cartModel);
        cartProductModel.setProduct(product.get());
        cartProductModel.setQuantity(request.getQuantity());
        cartProductModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        
        service.saveCartProduct(cartProductModel);
        service.updateCart(cartModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartProductModel);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> removeFromCart(@PathVariable(value = "uuid") UUID cartProductId) {
        Optional<CartProductsModel> search = this.service.findCartProductById(cartProductId);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado no carrinho");
        }

        service.deleteCartProduct(search.get());
        this.service.updateCart(search.get().getCart());
        
        return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso!");
    }

    @PatchMapping
    public ResponseEntity<Object> updateProductFromCart(@RequestBody @Valid AddToCartRequest request) {
        Optional<CartProductsModel> search = this.service.findCartProductById(request.getProduct());
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado no carrinho");
        }

        CartProductsModel cartProduct = search.get();
        cartProduct.setQuantity(request.getQuantity());
        
        this.service.updateCartProduct(cartProduct);
        this.service.updateCart(search.get().getCart());
        
        return ResponseEntity.status(HttpStatus.OK).body("Alterado com sucesso!");
    }

}
