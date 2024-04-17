package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wileyt3.backend.dto.PortfolioStockDto;
import wileyt3.backend.entity.PortfolioStock;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.entity.User;
import wileyt3.backend.mapper.PortfolioStockMapper;
import wileyt3.backend.repository.PortfolioStockRepository;
import wileyt3.backend.repository.StockRepository;
import wileyt3.backend.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioStockMapper portfolioStockMapper;

    public PortfolioStock addStockToPortfolio(PortfolioStockDto portfolioStockDto) {
        User user = userRepository.findById(portfolioStockDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Stock stock = stockRepository.findBySymbol(portfolioStockDto.getSymbol()).orElseThrow(() -> new IllegalArgumentException("Stock not found"));

        PortfolioStock portfolioStock = PortfolioStock.builder()
                .user(user)
                .stock(stock)
                .quantityOwned(portfolioStockDto.getQuantity())
                .purchasePrice(portfolioStockDto.getPurchasePrice())
                .purchaseDate(new Timestamp(System.currentTimeMillis()))
                .build();

        return portfolioStockRepository.save(portfolioStock);
    }

    public List<PortfolioStock> findByUserId(Integer userId) {
        return portfolioStockRepository.findByUser_Id(userId);
    }

    @Transactional
    public void deleteStockFromPortfolio(Integer portfolioStockId) {
        portfolioStockRepository.deleteById(portfolioStockId);
    }
    @Transactional
    public PortfolioStock updateStockInPortfolio(PortfolioStockDto portfolioStockDto) {
        PortfolioStock existingStock = portfolioStockRepository.findById(portfolioStockDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Stock not found in portfolio"));
        PortfolioStock updatedStock = portfolioStockMapper.dtoToPortfolioStock(portfolioStockDto);
        updatedStock.setId(existingStock.getId());
        return portfolioStockRepository.save(updatedStock);
    }

    @Transactional
    public void deleteAllStocksFromPortfolio(Integer userId) {
        portfolioStockRepository.deleteByUser_Id(userId);
    }
}
