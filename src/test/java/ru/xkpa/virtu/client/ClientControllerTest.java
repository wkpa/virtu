package ru.xkpa.virtu.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.xkpa.virtu.common.VirtuResponseStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    private static final String REST_URL = "/api/clients";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRepository clientRepository;


    private String clientNewJson;
    private String clientJsonInvalid;
    private String clientJsonWithoutRequiredField;
    private String clientNewJsonWithIncorrectPassportNumber;
    private Client client;
    
    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(999L);
        client.setName("Пупкин");
        client.setSurname("Пафнутий");
        client.setPatronymic("Иванович");
        client.setBirthday(LocalDate.of(2001, 11, 2));
        client.setPassportSeries("1111");
        client.setPassportNumber("222222");

        clientNewJson = "{\n" +
                "\"id\": null,\n" +
                "\"name\": \"Петров\",\n" +
                "\"surname\": \"Иван\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1982-06-17\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"222222\"\n" +
                "}";

        clientNewJsonWithIncorrectPassportNumber = "{\n" +
                "\"id\": null,\n" +
                "\"name\": \"Петров\",\n" +
                "\"surname\": \"Иван\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1982-06-17\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"22222a\"\n" +
                "}";

        clientJsonInvalid = "{\n" +
                "\"id\": null,\n" +
                "\"name\": \"Петров\",\n" +
                "\"surname\": \"Иван\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1982-06-17\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"222222\",\n" +
                "}";

        clientJsonWithoutRequiredField = "{\n" +
                "\"id\": null,\n" +
                "\"name\": \"Петров\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1982-06-17\",\n" +
                "\"passportNumber\": \"222222\"\n" +
                "}";
    }
    

    @Test
    void getClient() throws Exception {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(new Client()));
        mockMvc.perform(get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.OK.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getClientNotFound() throws Exception {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }


    @Test
    void addClient() throws Exception {
        when(clientRepository.saveAndFlush(any(Client.class))).thenReturn(client);
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(clientNewJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.OK.getCode()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(999L))
                .andExpect(jsonPath("$.data.name").value("Пупкин"))
                .andExpect(jsonPath("$.data.surname").value("Пафнутий"))
                .andExpect(jsonPath("$.data.patronymic").value("Иванович"))
                .andExpect(jsonPath("$.data.birthday").value("2001-11-02"))
                .andExpect(jsonPath("$.data.passportSeries").value("1111"))
                .andExpect(jsonPath("$.data.passportNumber").value("222222"));
    }

    @Test
    void addClientInvalidJson() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(clientJsonInvalid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addClientWithoutRequiredField() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(clientJsonWithoutRequiredField))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addClientWithIncorrectPassportNumber() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(clientNewJsonWithIncorrectPassportNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void updateClient() {
    }

    @Test
    void updateClientInvalidJson() throws Exception {
        mockMvc.perform(put(REST_URL + "/1").contentType(MediaType.APPLICATION_JSON).content(clientJsonInvalid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void updateClientWithoutRequiredField() throws Exception {
        mockMvc.perform(put(REST_URL + "/1").contentType(MediaType.APPLICATION_JSON).content(clientJsonWithoutRequiredField))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

}