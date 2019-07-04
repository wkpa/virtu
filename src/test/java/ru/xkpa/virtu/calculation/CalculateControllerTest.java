package ru.xkpa.virtu.calculation;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculateControllerTest {

    private static final String REST_URL = "/api/calculate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CalculationService calculationService;


    private String calculateJson;
    private String calculateJsonInvalid;
    private String calculateJsonWithoutRequiredField;
    private String calculateJsonWithIncorrectPeriod;
    private String calculateJsonWithPeriodTooLong;
    private String calculateJsonIncorrectYearOfBuild;
    private String calculateJsonYearOfBuildMoreThanCurrentYear;
    private CalculationResult calculationResult;

    @BeforeEach
    void setUp() {
        calculationResult = new CalculationResult();
        calculationResult.setCalculationDate(LocalDate.now());
        calculationResult.setInsurancePremium(7245);

        calculateJson = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-29\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50\"\n" +
                "}";

        calculateJsonIncorrectYearOfBuild = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-29\",\n" +
                "\"yearOfBuild\": 199,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50\"\n" +
                "}";

        calculateJsonYearOfBuildMoreThanCurrentYear = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-29\",\n" +
                "\"yearOfBuild\": 2050,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50\"\n" +
                "}";

        calculateJsonInvalid = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-30\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50,\"\n" +
                "}";

        calculateJsonWithoutRequiredField = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-30\",\n" +
                "\"area\": \"50\"\n" +
                "}";

        calculateJsonWithIncorrectPeriod = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2018-06-30\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50\"\n" +
                "}";

        calculateJsonWithPeriodTooLong = "{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-06-30\",\n" +
                "\"periodTo\": \"2020-06-30\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\"\n" +
                "},\n" +
                "\"area\": \"50\"\n" +
                "}";
    }


    @Test
    void getInsurancePremium() throws Exception {
        when(calculationService.calculate(any(CalculationData.class))).thenReturn(calculationResult);
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.OK.getCode()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.calculationDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.data.insurancePremium").value(7245));
    }

    @Test
    void getInsurancePremiumInvalidJson() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonInvalid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getInsurancePremiumWithoutRequiredField() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonWithoutRequiredField))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getInsurancePremiumIncorrectYearOfBuild() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonIncorrectYearOfBuild))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getInsurancePremiumYearOfBuildMoreThanCurrentYear() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonYearOfBuildMoreThanCurrentYear))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getInsurancePremiumWithIncorrectPeriod() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonWithIncorrectPeriod))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getInsurancePremiumWithPeriodTooLong() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(calculateJsonWithPeriodTooLong))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

}
