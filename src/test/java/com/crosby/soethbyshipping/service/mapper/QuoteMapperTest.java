package com.crosby.soethbyshipping.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class QuoteMapperTest {

    private QuoteMapper quoteMapper;

    @BeforeEach
    public void setUp() {
        quoteMapper = new QuoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(quoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(quoteMapper.fromId(null)).isNull();
    }
}
