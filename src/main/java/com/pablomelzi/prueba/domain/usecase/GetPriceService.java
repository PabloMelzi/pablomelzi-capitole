package com.pablomelzi.prueba.domain.usecase;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;

import java.time.LocalDateTime;

public class GetPriceService implements GetPriceQuery {

    private final PriceRepositoryPort priceRepository;

    public GetPriceService(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        return null;
    }

}

