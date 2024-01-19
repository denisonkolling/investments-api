package com.example.investments.service.Impl;

import com.example.investments.dto.StockRequestDTO;
import com.example.investments.model.Stock;
import com.example.investments.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockServiceImpl;

    @Captor
    private ArgumentCaptor<Stock> stockArgumentCaptor;


    @Nested
    class createStock {

        @Test
        @DisplayName("Create stock with success")
        void createStockWithSuccess() {

            var stock = new Stock(
                    "STEST4",
                    "Stock test description."
            );

            doReturn(stock).when(stockRepository).save(stockArgumentCaptor.capture());

            var input = new StockRequestDTO(
                    "STEST4DTO",
                    "Stock test DTO description."
            );

            stockServiceImpl.createStock(input);

            var stockCaptured = stockArgumentCaptor.getValue();

            assertEquals(input.stockId(),stockCaptured.getStockId());
            assertEquals(input.description(),stockCaptured.getDescription());
        }


    }
}