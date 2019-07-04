package ru.xkpa.virtu.agreement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.xkpa.virtu.address.Address;
import ru.xkpa.virtu.asset.AssetKind;
import ru.xkpa.virtu.asset.AssetKindRepository;
import ru.xkpa.virtu.calculation.CalculationData;
import ru.xkpa.virtu.calculation.CalculationResult;
import ru.xkpa.virtu.client.Client;
import ru.xkpa.virtu.client.ClientRepository;
import ru.xkpa.virtu.common.VirtuResponseStatus;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgreementControllerTest {

    private static final String REST_URL = "/api/agreements";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AgreementRepository agreementRepository;

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    AssetKindRepository assetKindRepository;

    private String agreementDateNow = LocalDate.now().toString();
    private String agreementNewJson;
    private String agreementJsonInvalid;
    private String agreementJsonWithoutRequiredField;
    private String agreementJsonWithIncorrectPeriod;
    private String agreementNewJsonWithIncorrectYearOfBuild;
    private Agreement agreement;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Иван");
        client.setSurname("Иванов");

        AssetKind assetKind = new AssetKind();
        assetKind.setId(1L);
        assetKind.setRatio(1.7F);

        Address address = new Address();
        address.setCountry("Россия");
        address.setRegion("Московская обл.");

        CalculationData calculationData = new CalculationData();
        calculationData.setInsurancePayment(1_000_000);
        calculationData.setPeriodFrom(LocalDate.of(2019, 8, 30));
        calculationData.setPeriodTo(LocalDate.of(2020, 8, 29));
        calculationData.setYearOfBuild((short) 1990);
        calculationData.setAssetKind(assetKind);
        calculationData.setArea(72.5F);

        CalculationResult calculationResult= new CalculationResult();
        calculationResult.setCalculationDate(LocalDate.now());
        calculationResult.setInsurancePremium(9082.19F);

        agreement = new Agreement();
        agreement.setId(999L);
        agreement.setAgreementNumber(560000);
        agreement.setAgreementDate(LocalDate.now());
        agreement.setClient(client);
        agreement.setAddress(address);
        agreement.setCalculationData(calculationData);
        agreement.setCalculationResult(calculationResult);

        agreementNewJson = "{\n" +
                "\"id\": null,\n" +
                "\"agreementNumber\": 100000,\n" +
                "\"agreementDate\": \"" + agreementDateNow +"\",\n" +
                "\"client\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Иван\",\n" +
                "\"surname\": \"Иванов\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1959-01-21\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"111111\"\n" +
                "},\n" +
                "\"address\":{\n" +
                "\"country\": \"Россия\",\n" +
                "\"region\": \"г. Москва\",\n" +
                "\"zone\": null,\n" +
                "\"postIndex\": null,\n" +
                "\"city\": null,\n" +
                "\"street\": null,\n" +
                "\"building\": null,\n" +
                "\"apartment\": null\n" +
                "},\n" +
                "\"calculationData\":{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-08-30\",\n" +
                "\"periodTo\": \"2020-08-29\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"area\": 72.5,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\",\n" +
                "\"ratio\": 1.7\n" +
                "}\n" +
                "},\n" +
                "\"calculationResult\":{\n" +
                "\"calculationDate\": \"" + agreementDateNow +"\",\n" +
                "\"insurancePremium\": 9082.19\n" +
                "}\n" +
                "}";

        agreementNewJsonWithIncorrectYearOfBuild = "{\n" +
                "\"id\": null,\n" +
                "\"agreementNumber\": 100000,\n" +
                "\"agreementDate\": \"" + agreementDateNow +"\",\n" +
                "\"client\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Иван\",\n" +
                "\"surname\": \"Иванов\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1959-01-21\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"111111\"\n" +
                "},\n" +
                "\"address\":{\n" +
                "\"country\": \"Россия\",\n" +
                "\"region\": \"г. Москва\",\n" +
                "\"zone\": null,\n" +
                "\"postIndex\": null,\n" +
                "\"city\": null,\n" +
                "\"street\": null,\n" +
                "\"building\": null,\n" +
                "\"apartment\": null\n" +
                "},\n" +
                "\"calculationData\":{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2019-08-30\",\n" +
                "\"periodTo\": \"2020-08-29\",\n" +
                "\"yearOfBuild\": 2022,\n" +
                "\"area\": 72.5,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\",\n" +
                "\"ratio\": 1.7\n" +
                "}\n" +
                "},\n" +
                "\"calculationResult\":{\n" +
                "\"calculationDate\": \"" + agreementDateNow +"\",\n" +
                "\"insurancePremium\": 9082.19\n" +
                "}\n" +
                "}";

        agreementJsonInvalid = "{\n" +
                "\"id\": null,\n" +
                "\"agreementNumber\": 100006,\n";

        agreementJsonWithoutRequiredField = "{\n" +
                "\"id\": null,\n" +
                "\"agreementNumber\": 100000,\n" +
                "\"agreementDate\": \"" + agreementDateNow +"\",\n" +
                "\"client\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Иван\",\n" +
                "\"surname\": \"Иванов\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1959-01-21\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"111111\"\n" +
                "},\n" +
                "\"address\":{\n" +
                "\"country\": \"Россия\",\n" +
                "\"region\": \"г. Москва\",\n" +
                "\"zone\": null,\n" +
                "\"postIndex\": null,\n" +
                "\"city\": null,\n" +
                "\"street\": null,\n" +
                "\"building\": null,\n" +
                "\"apartment\": null\n" +
                "},\n" +
                "\"calculationData\":{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"area\": 72.5,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\",\n" +
                "\"ratio\": 1.7\n" +
                "}\n" +
                "},\n" +
                "\"calculationResult\":{\n" +
                "\"calculationDate\": \"" + agreementDateNow +"\",\n" +
                "\"insurancePremium\": 9082.19\n" +
                "}\n" +
                "}";

        agreementJsonWithIncorrectPeriod = "{\n" +
                "\"id\": null,\n" +
                "\"agreementNumber\": 100000,\n" +
                "\"agreementDate\": \"" + agreementDateNow +"\",\n" +
                "\"client\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Иван\",\n" +
                "\"surname\": \"Иванов\",\n" +
                "\"patronymic\": \"Иванович\",\n" +
                "\"birthday\": \"1959-01-21\",\n" +
                "\"passportSeries\": \"1234\",\n" +
                "\"passportNumber\": \"111111\"\n" +
                "},\n" +
                "\"address\":{\n" +
                "\"country\": \"Россия\",\n" +
                "\"region\": \"г. Москва\",\n" +
                "\"zone\": null,\n" +
                "\"postIndex\": null,\n" +
                "\"city\": null,\n" +
                "\"street\": null,\n" +
                "\"building\": null,\n" +
                "\"apartment\": null\n" +
                "},\n" +
                "\"calculationData\":{\n" +
                "\"insurancePayment\": 1000000,\n" +
                "\"periodFrom\": \"2020-08-30\",\n" +
                "\"periodTo\": \"2019-08-29\",\n" +
                "\"yearOfBuild\": 1990,\n" +
                "\"area\": 72.5,\n" +
                "\"assetKind\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Квартира\",\n" +
                "\"ratio\": 1.7\n" +
                "}\n" +
                "},\n" +
                "\"calculationResult\":{\n" +
                "\"calculationDate\": \"" + agreementDateNow +"\",\n" +
                "\"insurancePremium\": 9082.19\n" +
                "}\n" +
                "}";
    }

    @Test
    void getAgreement() throws Exception {
        when(agreementRepository.findById(anyLong())).thenReturn(Optional.of(agreement));
        mockMvc.perform(get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.OK.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getAgreementNotFound() throws Exception {
        when(agreementRepository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get(REST_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addAgreement() throws Exception {
        when(agreementRepository.saveAndFlush(any(Agreement.class))).thenReturn(agreement);
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(agreementNewJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.OK.getCode()))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(999L))
                .andExpect(jsonPath("$.data.agreementNumber").value(560000))
                .andExpect(jsonPath("$.data.agreementDate").value(agreementDateNow))

                .andExpect(jsonPath("$.data.calculationData.insurancePayment").value(1000000))
                .andExpect(jsonPath("$.data.calculationData.periodFrom").value("2019-08-30"))
                .andExpect(jsonPath("$.data.calculationData.periodTo").value("2020-08-29"))
                .andExpect(jsonPath("$.data.calculationData.yearOfBuild").value(1990))
                .andExpect(jsonPath("$.data.calculationData.assetKind.id").value(1))
                .andExpect(jsonPath("$.data.calculationData.area").value(72.5))
                .andExpect(jsonPath("$.data.calculationResult.calculationDate").value(agreementDateNow))
                .andExpect(jsonPath("$.data.calculationResult.insurancePremium").value(9082.19))
                .andExpect(jsonPath("$.data.client.id").value(1))
                .andExpect(jsonPath("$.data.address.country").value("Россия"))
                .andExpect(jsonPath("$.data.address.region").value("Московская обл."));
    }

    @Test
    void addAgreementInvalidJson() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(agreementJsonInvalid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addAgreementWithIncorrectYearOfBuild() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(agreementNewJsonWithIncorrectYearOfBuild))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }


    @Test
    void addAgreementWithoutRequiredField() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(agreementJsonWithoutRequiredField))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addAgreementWithIncorrectPeriod() throws Exception {
        mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(agreementJsonWithIncorrectPeriod))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(VirtuResponseStatus.ERROR.getCode()))
                .andExpect(jsonPath("$.data").exists());
    }

}