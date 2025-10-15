package com.pablomelzi.prueba.domain.usecase;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.in.GetPriceQuery;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import com.pablomelzi.prueba.domain.exception.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class GetPriceService implements GetPriceQuery {

    private final PriceRepositoryPort priceRepository;

    public GetPriceService(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getPrice(LocalDateTime applicationDate, long productId, long brandId) {
        List<Price> prices = priceRepository.findPrices(applicationDate, productId, brandId);

        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for productId %d, brandId %d, date %s",
                                productId, brandId, applicationDate)));
    }
}


