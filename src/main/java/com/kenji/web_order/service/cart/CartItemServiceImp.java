package com.kenji.web_order.service.cart;

import com.kenji.web_order.dto.request.CartItemRequest;
import com.kenji.web_order.dto.response.CartItemResponse;
import com.kenji.web_order.dto.response.UserResponse;
import com.kenji.web_order.entity.Cart;
import com.kenji.web_order.entity.CartItem;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.enums.ProductSize;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.mapper.CartItemMapper;
import com.kenji.web_order.repository.CartItemRepository;
import com.kenji.web_order.repository.CartRepository;
import com.kenji.web_order.repository.ProductRepository;
import com.kenji.web_order.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Supplier;

@Service
public class CartItemServiceImp implements CartItemService {
    @Autowired
    CartItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemMapper itemMapper;
    @Autowired
    CartService cartService;

    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public CartItemResponse addCartItem(CartItemRequest request) {
        User user = getCurrentUser();

        Cart cart = cartService.createCartIfNotExist(user);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        BigDecimal price = product.getPrices().get(request.getSize().name());

        CartItem item = itemMapper.toCartItem(request);
        item.setTotalMoney(price.multiply(BigDecimal.valueOf(request.getQuantity())));
        item.setCart(cart);
        item.setProduct(product);

        CartItem itemSave = itemRepository.save(item);
        cart.getCartItems().add(itemSave);

        return itemMapper.toCartItemResponse(itemSave);
    }

    private CartItem getCurrentCartItem(CartItemRequest request) {
        User user = getCurrentUser();
        Cart cart = cartService.findCart(user);
        long itemId = request.getId();
        CartItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return item;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updateQuantity(CartItemRequest request) {
        CartItem item = getCurrentCartItem(request);
        int newQuantity = request.getQuantity();
        if (newQuantity <= 0) {
            itemRepository.deleteById(item.getId());
            return;
        }
        BigDecimal price = item.getProduct().getPrices().get(item.getSize().name());
        item.setQuantity(newQuantity);
        item.setTotalMoney(price.multiply(BigDecimal.valueOf(newQuantity)));
        itemRepository.save(item);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updateSize(CartItemRequest request) {
        CartItem item = getCurrentCartItem(request);
        ProductSize newSize = request.getSize();
        if (!item.getProduct().getPrices().containsKey(newSize.name())) {
            throw new AppException(ErrorCode.SIZE_NOT_VALID);
        }
        BigDecimal price = item.getProduct().getPrices().get(newSize.name());
        item.setSize(newSize);
        item.setTotalMoney(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        itemRepository.save(item);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void deleteCartItem(Long id) {
        CartItemRequest request = new CartItemRequest();
        request.setId(id);
        CartItem item = getCurrentCartItem(request);
        itemRepository.delete(item);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void updateNote(CartItemRequest request) {
        CartItem item = getCurrentCartItem(request);
        String note = request.getNote();
        item.setNote(note);
        itemRepository.save(item);
    }
}
