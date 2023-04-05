package com.asma.inventoryservice.service;

import com.asma.inventoryservice.model.Inventory;
import com.asma.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> findAll(){
        return  inventoryRepository.findAll();
    }
    public Inventory save(Inventory inventory){
        return inventoryRepository.save(inventory);
    }
}
