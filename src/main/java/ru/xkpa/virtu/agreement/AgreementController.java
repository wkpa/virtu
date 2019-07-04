package ru.xkpa.virtu.agreement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xkpa.virtu.calculation.CalculationService;
import ru.xkpa.virtu.common.VirtuException;
import ru.xkpa.virtu.common.VirtuResponse;
import ru.xkpa.virtu.common.VirtuResponseStatus;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static java.lang.Math.abs;

/**
 * @author Pavel Kurakin
 */
@RestController
@RequestMapping(value = "/api/agreements")
public class AgreementController {

    private static final Logger LOG = LoggerFactory.getLogger(AgreementController.class);

    private AgreementRepository agreementRepository;
    private CalculationService calculationService;

    @Autowired
    public AgreementController(AgreementRepository agreementRepository, CalculationService calculationService) {
        this.agreementRepository = agreementRepository;
        this.calculationService = calculationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public VirtuResponse<List<Agreement>> getAgreements() {
        return new VirtuResponse<>(agreementRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public VirtuResponse getAgreement(@PathVariable Long id) {
        LOG.debug("Request agreement: ID={}", id);
        Optional<Agreement> optionalAgreement = agreementRepository.findById(id);
        if (!optionalAgreement.isPresent()) {
            LOG.debug("Request agreement: ID={} is not found", id);
            return new VirtuResponse<>(VirtuResponseStatus.ERROR, "Agreement not found");
        }
        return new VirtuResponse<>(optionalAgreement.get());
    }

    @RequestMapping(method = RequestMethod.POST)
    public VirtuResponse addAgreement(@Valid @RequestBody Agreement agreement) {
        validateAgreement(agreement);
        try {
            agreement = agreementRepository.saveAndFlush(agreement);
        } catch (Exception e) {
            LOG.debug("Error of add agreement={}", agreement.getAgreementNumber());
            throw new VirtuException(VirtuResponseStatus.ERROR, e.getMessage());
        }
        LOG.debug("New agreement: NUMBER={} has been added", agreement.getAgreementNumber());
        return new VirtuResponse<>(agreement);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public VirtuResponse updateAgreement(@Valid @RequestBody Agreement agreement) {
        if (agreement.getId() == null) {
            LOG.error("Id of agreement is null for update");
            throw new VirtuException(VirtuResponseStatus.ERROR,"Id of agreement is null for update");
        }
        validateAgreement(agreement);
        try {
            agreement = agreementRepository.saveAndFlush(agreement);
        } catch (Exception e) {
            LOG.debug("Error of update agreement={}", agreement.getAgreementNumber());
            throw new VirtuException(VirtuResponseStatus.ERROR, e.getMessage());
        }
        LOG.debug("Agreement: NUMBER={} has been updated", agreement.getAgreementNumber());
        return new VirtuResponse<>(agreement);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public VirtuResponse deleteAgreement(@PathVariable Long id) {
        if (!agreementRepository.existsById(id)) {
            return new VirtuResponse<>(VirtuResponseStatus.ERROR, "Agreement not found");
        }
        agreementRepository.deleteById(id);
        LOG.debug("Agreement: ID={} has been deleted", id);
        return new VirtuResponse<>("Agreement has been deleted");
    }

    private void validateAgreement(Agreement agreement) {

        if (agreement.getId() == null && !agreement.getAgreementDate().isEqual(LocalDate.now())) {
            throw new VirtuException(VirtuResponseStatus.ERROR,"Date of agreement should be equals now date");
        }

        if (agreement.getId() != null) {
            Optional<Agreement> optionalAgreement = agreementRepository.findById(agreement.getId());

            if (!optionalAgreement.isPresent()) {
                LOG.debug("Request agreement: ID={} is not found", agreement.getId());
                throw new VirtuException(VirtuResponseStatus.ERROR,"Agreement not found");
            }

            if (!optionalAgreement.get().getAgreementDate().isEqual(agreement.getAgreementDate()) ) {
                LOG.warn("Attempt to change the date of the agreement. ID:{}", agreement.getId());
                throw new VirtuException(VirtuResponseStatus.ERROR,"Date of agreement cannot be changed");
            }
        }

        double insurance = calculationService.calculate(agreement.getCalculationData()).getInsurancePremium();
        if (abs(agreement.getCalculationResult().getInsurancePremium() - insurance) > 0.01) {
            LOG.warn("Attempt to change the data of the calculation. ID:{}", agreement.getId());
            throw new VirtuException(VirtuResponseStatus.ERROR, "Data does not match the calculated premium");
        }
    }

}
