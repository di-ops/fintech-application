package ru.example.fintech;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.example.fintech.dto.AccountDto;
import ru.example.fintech.dto.CustomerDto;
import ru.example.fintech.dto.DepositDto;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrateControllersTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @Order(1)
    @Test
    public void shouldCreateCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto("Ivan",
                "Ivanov", "1111", "6511 11111");
        this.mockMvc.perform(post("/api/v1/customers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void shouldCreateCustomerAndThenReturnHim() throws Exception {
        CustomerDto customerDto = new CustomerDto("Petr",
                "Petrov", "1234", "6515 141311");
        this.mockMvc.perform(post("/api/v1/customers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/v1/customers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(3)
    @Test
    public void shouldCreateCustomerAndThenDeleteHim() throws Exception {
        CustomerDto customerDto = new CustomerDto("Sergey",
                "Sergeev", "1212", "6511 101011");
        this.mockMvc.perform(post("/api/v1/customers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/api/v1/customers/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    public void shouldReturnBadRequestWhenTryToCreateNewCustomerWithEmptyRequestBody() throws Exception {
        this.mockMvc.perform(post("/api/v1/customers")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    public void shouldReturnArrayOfCustomers() throws Exception {

        this.mockMvc.perform(get("/api/v1/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(6)
    @Test
    public void shouldUpdateCustomerInfo() throws Exception {
        CustomerDto customerDto = new CustomerDto("Petr",
                "Petrov", "1235", "6515 141311");
        this.mockMvc.perform(get("/api/v1/customers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
        this.mockMvc.perform(patch("/api/v1/customers/2")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(customerDto)))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/v1/customers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(7)
    @Test
    public void shouldCreateNewAccountSuccess() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/2/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(8)
    @Test
    public void shouldCreateNewAccountAndReturnIt() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/2/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/v1/customers/2/accounts/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(9)
    @Test
    public void shouldThrowExceptionWhenTryCreateNewAccountWithIdThatDoesntExist() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/20/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(10)
    @Test
    public void shouldThrowExceptionWhenTryToGetAccountThatBelongAnotherCustomer() throws Exception {
        this.mockMvc.perform(get("/api/v1/customers/1/accounts/5"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(11)
    @Test
    public void shouldReturnAllAccountsByCustomer() throws Exception {
        this.mockMvc.perform(get("/api/v1/customers/2/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

    }

    @Order(12)
    @Test
    public void shouldCreateNewCard() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/2/accounts/2/cards")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(13)
    @Test
    public void shouldThrowExceptionWhenToTryCreateNewCardAndAccountFromPathBelongAnotherCustomer() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/3/accounts/1/cards")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(14)
    @Test
    public void shouldMakeDepositOnCard() throws Exception {
        DepositDto depositDto = new DepositDto(new BigDecimal(1000));
        this.mockMvc.perform(post("/api/v1/customers/2/accounts/2/cards/1")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(depositDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(15)
    @Test
    public void shouldCreateAnotherCardAndThenReturnArraysOfCardJson() throws Exception {
        AccountDto accountDto = new AccountDto();
        this.mockMvc.perform(post("/api/v1/customers/2/accounts/2/cards")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
        this.mockMvc.perform(get("/api/v1/customers/{id}/accounts/{accountId}/cards", 2, 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(16)
    @Test
    public void shouldReturnOneCardForCheckingBalance() throws Exception {
        this.mockMvc.perform(get("/api/v1/customers/2/accounts/2/cards/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Order(17)
    @Test
    public void shouldThrowExceptionWhenMakeDepositWithWrongSum() throws Exception {
        DepositDto depositDto = new DepositDto(new BigDecimal(-100));
        this.mockMvc.perform(post("/api/v1/customers/2/accounts/2/cards/1")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(depositDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}