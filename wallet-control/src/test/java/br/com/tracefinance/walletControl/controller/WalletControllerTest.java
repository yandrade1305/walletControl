package br.com.tracefinance.walletControl.controller;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private Integer existingId = 1;
    private Integer nonExistingId = 2;

    private static final String ownerNameNull = "{"
            + "\"ownerName\": null"
            + "}";

    private final String ownerNameEmpty = "{"
            + "\"ownerName\": \"\""
            + "}";

    private static final String ownerNameValid = "{"
            + "\"ownerName\":  \"Yan Andrade\""
            + "}";

    @Test
    @Rollback(false)
    @Order(1)
    public void shouldntRegisterBecauseOwnerNameisNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .content(ownerNameNull)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void shouldntRegisterBecauseOwnerNameisEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .content(ownerNameEmpty)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).toString();
        System.out.println();
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void shouldRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .content(ownerNameValid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void shouldntRegisterBecauseOwnerNameAlreadyExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .content(ownerNameValid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void shouldntSeeLimitBecausaWalletdontExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallets/" + nonExistingId + "/limits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(6)
    public void shouldSeeLimit() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallets/" + existingId + "/limits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
