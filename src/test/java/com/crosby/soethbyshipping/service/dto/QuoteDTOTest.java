package com.crosby.soethbyshipping.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.crosby.soethbyshipping.web.rest.TestUtil;

public class QuoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteDTO.class);
        QuoteDTO quoteDTO1 = new QuoteDTO();
        quoteDTO1.setId(1L);
        QuoteDTO quoteDTO2 = new QuoteDTO();
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
        quoteDTO2.setId(quoteDTO1.getId());
        assertThat(quoteDTO1).isEqualTo(quoteDTO2);
        quoteDTO2.setId(2L);
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
        quoteDTO1.setId(null);
        assertThat(quoteDTO1).isNotEqualTo(quoteDTO2);
    }
}
