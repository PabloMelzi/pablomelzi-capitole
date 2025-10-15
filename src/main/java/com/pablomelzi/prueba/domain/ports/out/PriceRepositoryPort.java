package com.pablomelzi.prueba.domain.ports.out;

import com.pablomelzi.prueba.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findPrices(LocalDateTime date, long productId, long brandId);
}
