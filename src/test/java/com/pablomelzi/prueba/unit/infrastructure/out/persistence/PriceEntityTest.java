package com.pablomelzi.prueba.unit.infrastructure.out.persistence;

import com.pablomelzi.prueba.infrastructure.out.persistence.PriceEntity;
import com.pablomelzi.prueba.infrastructure.out.persistence.JpaPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PriceEntityTest {

    @Autowired
    private JpaPriceRepository repository;

    @Test
    void persisteYRecuperaEntidadCorrectamente() {
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1L);
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        entity.setPriceList(1);
        entity.setProductId(35455L);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        PriceEntity saved = repository.save(entity);
        PriceEntity found = repository.findById(saved.getId()).orElseThrow();

        assertThat(found.getProductId()).isEqualTo(35455L);
        assertThat(found.getBrandId()).isEqualTo(1L);
        assertThat(found.getPriceList()).isEqualTo(1);
        assertThat(found.getPriority()).isEqualTo(0);
        assertThat(found.getPrice()).isEqualByComparingTo("35.50");
        assertThat(found.getCurrency()).isEqualTo("EUR");
        assertThat(found.getStartDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(found.getEndDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
    }
}

