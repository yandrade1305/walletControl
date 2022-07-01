package br.com.tracefinance.walletControl.controller;

import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String ownerNameValid = "{"
            + "\"ownerName\":  \"Yan Andrade\""
            + "}";

    private final String paymentGreaterThanOneThousand = "{"
            + "\"amount\":  1001.00,"
            + "\"paymentDate\":  \"13/05/2022 16:52\""
            + "}";

    private final String paymentLessThanOneThousand = "{"
            + "\"amount\":  999.00,"
            + "\"paymentDate\":  \"13/05/2022 16:52\""
            + "}";

    private final String paymentEqualOneThousandDaytimeSameDay = "{"
            + "\"amount\":  1000.00,"
            + "\"paymentDate\":  \"13/05/2022 16:52\""
            + "}";

    private final String paymentEqualOne= "{"
            + "\"amount\":  1.00,"
            + "\"paymentDate\":  \"13/05/2022 16:52\""
            + "}";

    private final String paymentEqualOneThousandNocturnalSameDay = "{"
            + "\"amount\":  1000.00,"
            + "\"paymentDate\":  \"13/05/2022 18:52\""
            + "}";

    private final String paymentEqualOneThousandDaytimeDifferentDay = "{"
            + "\"amount\":  1000.00,"
            + "\"paymentDate\":  \"16/05/2022 16:52\""
            + "}";

    private final String paymentEqualOneThousandNocturnalDifferentDay = "{"
            + "\"amount\":  1000.00,"
            + "\"paymentDate\":  \"16/05/2022 18:52\""
            + "}";

    private final String paymentEqualOneThousandWeekend = "{"
            + "\"amount\":  1000.00,"
            + "\"paymentDate\":  \"14/05/2022 16:52\""
            + "}";


    private Integer existingId = 1;

    private Integer nonExistingId = 2;

    @BeforeAll
    public void setup() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .content(ownerNameValid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    @Rollback(false)
    @Order(1)
    public void shouldntPayBecauseWalletdontExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + nonExistingId + "/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void shouldntPayBecausePaymentIsGreaterThanOneThousand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentGreaterThanOneThousand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void shouldPayBecausePaymentIsLessThanOneThousand() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentLessThanOneThousand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Rollback(false)
    @Order(4)
    public void shouldPayDuringDaytimeBecauseDaytimeLimitWasntExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Rollback(false)
    @Order(5)
    public void shouldntPayDuringDaytimeBecauseDaytimeLimitExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOne)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(6)
    public void shouldPayDuringNocturnalBecauseNocturnalLimitWasntExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandNocturnalSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback(false)
    @Order(7)
    public void shouldntPayDuringNocturnalBecauseNocturnalLimitWasExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandNocturnalSameDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @Order(8)
    public void shouldPayFiveThousandInADay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandNocturnalDifferentDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeDifferentDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeDifferentDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeDifferentDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandDaytimeDifferentDay)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback(false)
    @Order(9)
    public void shouldPayWeekendBecauseLimitWasntExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandWeekend)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback(false)
    @Order(10)
    public void shouldntPayWeekendBecauseLimitWasExceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets/" + existingId + "/payments")
                        .content(paymentEqualOneThousandWeekend)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
