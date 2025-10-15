package com.pablomelzi.prueba.infrastructure.out.persistence;

import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.domain.ports.out.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;

    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public List<Price> findPrices(LocalDateTime date, long productId, long brandId) {
        List<PriceEntity> entities = jpaPriceRepository
                .findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        productId, brandId, date, date);

        return entities.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Price mapToDomain(PriceEntity entity) {
        return Price.builder()
                .productId(entity.getProductId())
                .brandId(entity.getBrandId())
                .priceList(entity.getPriceList())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}

