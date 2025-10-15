package com.pablomelzi.prueba.application.service;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import com.pablomelzi.prueba.domain.usecase.GetPriceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GetPriceServiceImpl implements GetPriceQuery {

    private final GetPriceService getPriceService;

    public GetPriceServiceImpl(PriceRepositoryPort priceRepositoryPort) {
        this.getPriceService = new GetPriceService(priceRepositoryPort);
    }

    @Override
    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        return getPriceService.getPrice(applicationDate, productId, brandId);
    }
}

