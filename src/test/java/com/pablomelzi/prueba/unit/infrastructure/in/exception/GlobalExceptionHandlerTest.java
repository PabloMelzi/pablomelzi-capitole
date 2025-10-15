package com.pablomelzi.prueba.unit.infrastructure.in.exception;

import com.pablomelzi.prueba.domain.exception.PriceNotFoundException;
import com.pablomelzi.prueba.infrastructure.in.exception.GlobalExceptionHandler;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.Min;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    @RestController
    static class TestController {
        @GetMapping("/test-exception")
        public void throwException() {
            throw new PriceNotFoundException("No price found for test");
        }
    }

    @Test
    void devuelve404YMensajeCorrectoCuandoNoHayPrecio() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(get("/test-exception").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No price found for test"));
    }

}

