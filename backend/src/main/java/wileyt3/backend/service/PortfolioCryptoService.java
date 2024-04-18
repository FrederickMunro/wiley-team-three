package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wileyt3.backend.dto.PortfolioCryptoDto;
import wileyt3.backend.entity.Crypto;
import wileyt3.backend.entity.PortfolioCrypto;
import wileyt3.backend.entity.User;
import wileyt3.backend.mapper.PortfolioCryptoMapper;
import wileyt3.backend.repository.CryptoRepository;
import wileyt3.backend.repository.PortfolioCryptoRepository;
import wileyt3.backend.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PortfolioCryptoService {

    @Autowired
    private PortfolioCryptoRepository portfolioCryptoRepository;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioCryptoMapper portfolioCryptoMapper;

    public PortfolioCrypto addCryptoToPortfolio(PortfolioCryptoDto portfolioCryptoDto) {
        User user = userRepository.findById(portfolioCryptoDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Crypto crypto = cryptoRepository.findByTicker(portfolioCryptoDto.getTicker()).orElseThrow(() -> new IllegalArgumentException("Crypto not found"));

        PortfolioCrypto portfolioCrypto = PortfolioCrypto.builder()
                .user(user)
                .crypto(crypto)
                .quantityOwned(portfolioCryptoDto.getQuantity())
                .purchasePrice(portfolioCryptoDto.getPurchasePrice())
                .purchaseDate(new Timestamp(System.currentTimeMillis()))
                .build();

        return portfolioCryptoRepository.save(portfolioCrypto);
    }

    public List<PortfolioCrypto> findByUserId(Integer userId) {
        return portfolioCryptoRepository.findByUser_Id(userId);
    }

    @Transactional
    public void deleteCryptoFromPortfolio(Integer portfolioCryptoId) {
        portfolioCryptoRepository.deleteById(portfolioCryptoId);
    }

    @Transactional
    public PortfolioCrypto updateCryptoInPortfolio(PortfolioCryptoDto portfolioCryptoDto) {
        PortfolioCrypto existingCrypto = portfolioCryptoRepository.findById(portfolioCryptoDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Crypto not found in portfolio"));
        portfolioCryptoMapper.dtoToPortfolioCrypto(portfolioCryptoDto).updateFrom(existingCrypto);
        return portfolioCryptoRepository.save(existingCrypto);
    }

    @Transactional
    public void deleteAllCryptosFromPortfolio(Integer userId) {
        portfolioCryptoRepository.deleteByUser_Id(userId);
    }
}
