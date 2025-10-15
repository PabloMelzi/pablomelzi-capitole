package com.pablomelzi.prueba.unit.infrastructure.out.persistence;

import com.pablomelzi.prueba.infrastructure.out.persistence.JpaPriceRepository;
import com.pablomelzi.prueba.infrastructure.out.persistence.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JpaPriceRepositoryTest {

    @Autowired
    private JpaPriceRepository repository;

    @Test
    void shouldFindPriceForTest3() {
        List<PriceEntity> prices = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                35455L, 1L, LocalDateTime.parse("2020-06-14T21:00:00"), LocalDateTime.parse("2020-06-14T21:00:00"));
        assertThat(prices).isNotEmpty();
        assertThat(prices.get(0).getPriceList()).isEqualTo(1);
    }
}


