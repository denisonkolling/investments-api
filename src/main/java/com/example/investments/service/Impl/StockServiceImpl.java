package com.example.investments.service.Impl;

import com.example.investments.dto.StockRequestDTO;
import com.example.investments.model.Stock;
import com.example.investments.repository.StockRepository;
import com.example.investments.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Override
    public void createStock(StockRequestDTO stockRequestDTO) {

        var stock = new Stock(
                stockRequestDTO.stockId(),
                stockRequestDTO.description()
        );

        stockRepository.save(stock);

    }
}

