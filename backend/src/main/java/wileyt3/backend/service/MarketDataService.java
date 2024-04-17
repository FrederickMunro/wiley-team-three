package wileyt3.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MarketDataService<T> {

    T fetchMarketData(String identifier);

    T findById(Integer id);

    Map<String, T> fetchAllMarketData();

    T saveMarketData(T data);

    T updateMarketData(Integer id);

    void deleteMarketData(Integer id);

    Page<T> findAll(Pageable pageable);

    List<T> findAll();
}
