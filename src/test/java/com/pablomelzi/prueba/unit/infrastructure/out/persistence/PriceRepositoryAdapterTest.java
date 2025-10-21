package com.pablomelzi.prueba.unit.infrastructure.out.persistence;


import com.pablomelzi.prueba.domain.model.Price;
import com.pablomelzi.prueba.infrastructure.out.persistence.JpaPriceRepository;
import com.pablomelzi.prueba.infrastructure.out.persistence.PriceEntity;
import com.pablomelzi.prueba.infrastructure.out.persistence.PriceRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRepositoryAdapterTest {

    @Test
    void mapCorrectlyEntityToDomain() {
        JpaPriceRepository jpaRepo = Mockito.mock(JpaPriceRepository.class);
        PriceEntity entity = new PriceEntity();
        entity.setProductId(35455L);
        entity.setBrandId(1L);
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));

        Mockito.when(jpaRepo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        35455L, 1L, LocalDateTime.parse("2020-06-14T10:00:00"), LocalDateTime.parse("2020-06-14T10:00:00")))
                .thenReturn(List.of(entity));

        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(jpaRepo);

        List<Price> prices = adapter.findPrices(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1L);

        assertThat(prices).hasSize(1);
        Price price = prices.get(0);
        assertThat(price.getProductId()).isEqualTo(35455L);
        assertThat(price.getBrandId()).isEqualTo(1L);
        assertThat(price.getPriceList()).isEqualTo(1);
        assertThat(price.getPriority()).isEqualTo(0);
        assertThat(price.getPrice()).isEqualByComparingTo("35.50");
        assertThat(price.getCurrency()).isEqualTo("EUR");
        assertThat(price.getStartDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(price.getEndDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
    }

    @Test
    void throwAEmptyListIfThereIsNoResults() {
        JpaPriceRepository jpaRepo = Mockito.mock(JpaPriceRepository.class);
        Mockito.when(jpaRepo.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        99999L, 1L, LocalDateTime.parse("2020-06-14T10:00:00"), LocalDateTime.parse("2020-06-14T10:00:00")))
                .thenReturn(List.of());

        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(jpaRepo);

        List<Price> prices = adapter.findPrices(LocalDateTime.parse("2020-06-14T10:00:00"), 99999L, 1L);

        assertThat(prices).isEmpty();
    }
}

