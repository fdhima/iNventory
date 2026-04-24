package com.tutorial.inventory.stock;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/stock-levels")
public class StockLevelController {
    private final StockLevelRepository stockLevelRepo;

    public StockLevelController(StockLevelRepository stockLevelRepo) {
        this.stockLevelRepo = stockLevelRepo;
    }

    @GetMapping
    public List<StockLevel> getAllStockLevels() {
        return stockLevelRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<StockLevel> getStockLevelById(@PathVariable UUID id) {
        return stockLevelRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockLevel createStockLevel(@RequestBody StockLevel stockLevel) {
        return stockLevelRepo.save(stockLevel);
    }

    @PutMapping("/{id}")
    public StockLevel updateStockLevel(@PathVariable UUID id, @RequestBody StockLevel updatedStockLevel) {
        return stockLevelRepo.findById(id)
        .map(stockLevel -> {
            stockLevel.setProduct(updatedStockLevel.getProduct());
            stockLevel.setLocation(updatedStockLevel.getLocation());
            stockLevel.setLevel(updatedStockLevel.getLevel());
            stockLevel.setThreshold(updatedStockLevel.getThreshold());
            return stockLevelRepo.save(stockLevel);
        })
        .orElseThrow(() -> new RuntimeException("Stock level not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteStockLevel(@PathVariable UUID id) {
        stockLevelRepo.deleteById(id);
    }
}
