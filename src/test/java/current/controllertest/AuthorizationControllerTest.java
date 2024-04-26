package current.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import current.controller.AuthorizationController;
import current.model.*;
import current.service.AuthorizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTest {

    @InjectMocks
    private AuthorizationController authorizationController;

    @Mock
    private AuthorizationService authorizationService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper(); // for JSON conversion




    @Test
    public void testProcessAuthorizationSuccess() throws Exception {
        String messageId = "1234";
        BigDecimal amountValue = new BigDecimal(100.00);
        Amount amount = new Amount(amountValue.toString(), "USD", DebitCredit.CREDIT);
        AuthorizationRequest request = new AuthorizationRequest("user1", messageId, amount);

        // Assuming success code
        AuthorizationResponse expectedResponse = new AuthorizationResponse("user1", messageId, ResponseCode.APPROVED, new Amount(new BigDecimal(500.00).toString(), "USD", DebitCredit.CREDIT));
        Mockito.when(authorizationService.processAuthorization(request)).thenReturn(expectedResponse);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/{messageId}", messageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageId").value(messageId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(ResponseCode.APPROVED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance.amount").value(expectedResponse.getBalance().getAmount()));
    }
}
