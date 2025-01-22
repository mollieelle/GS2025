// Test for MutualFundService
package com.example.demo.service;

import com.example.demo.model.MutualFund;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutualFundServiceTest {

    private final MutualFundService mutualFundService = new MutualFundService();

    @Test
    void testGetMutualFunds() {
        List<MutualFund> funds = mutualFundService.getMutualFunds();

        assertNotNull(funds);
        assertEquals(3, funds.size());
        assertEquals("FXAIX", funds.get(0).getTicker());
    }
}
