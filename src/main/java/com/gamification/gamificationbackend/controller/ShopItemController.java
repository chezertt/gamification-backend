package com.gamification.gamificationbackend.controller;

import com.gamification.gamificationbackend.dto.request.ShopItemRequestDto;
import com.gamification.gamificationbackend.dto.response.ShopItemResponseDto;
import com.gamification.gamificationbackend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-items")
@RequiredArgsConstructor
public class ShopItemController {

    private final ShopService shopService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ShopItemResponseDto>> getShopItems() {
        return ResponseEntity.ok(shopService.getShopItems());
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity<Void> addShopItem(@RequestBody ShopItemRequestDto shopItemRequestDto) {
        shopService.addShopItem(shopItemRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @DeleteMapping("/{shopItemId}")
    public ResponseEntity<Void> deleteShopItem(@PathVariable Long shopItemId) {
        shopService.deleteById(shopItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PLAYER')")
    @PostMapping("/{shopItemId}/buy")
    public ResponseEntity<Void> buyShopItem(@PathVariable Long shopItemId) {
        shopService.buyShopItem(shopItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
