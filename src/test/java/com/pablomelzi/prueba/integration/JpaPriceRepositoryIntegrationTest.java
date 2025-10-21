package com.pablomelzi.prueba.integration;

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
class JpaPriceRepositoryIntegrationTest {

    @Autowired
    private JpaPriceRepository repository;

    @Test
    void findPriceWithParamsAndDate() {
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                35455L, 1L, LocalDateTime.parse("2020-06-14T16:00:00"), LocalDateTime.parse("2020-06-14T16:00:00"));

        assertThat(results).hasSize(2);
        assertThat(results).anyMatch(e -> e.getPriceList() == 1);
        assertThat(results).anyMatch(e -> e.getPriceList() == 2);
    }
}

