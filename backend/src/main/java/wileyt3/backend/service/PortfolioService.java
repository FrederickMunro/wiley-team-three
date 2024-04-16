package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wileyt3.backend.entity.PortfolioStock;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.entity.User;
import wileyt3.backend.repository.PortfolioStockRepository;
import wileyt3.backend.repository.StockRepository;
import wileyt3.backend.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    public PortfolioStock addStockToPortfolio(Integer userId, String symbol, Integer quantity, BigDecimal purchasePrice) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() -> new IllegalArgumentException("Stock not found"));

        PortfolioStock portfolioStock = PortfolioStock.builder()
                .user(user)
                .stock(stock)
                .quantityOwned(quantity)
                .purchasePrice(purchasePrice)
                .purchaseDate(new Timestamp(System.currentTimeMillis()))
                .build();

        return portfolioStockRepository.save(portfolioStock);
    }
}
