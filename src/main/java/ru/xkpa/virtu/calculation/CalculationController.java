package ru.xkpa.virtu.calculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xkpa.virtu.common.VirtuResponse;
import javax.validation.Valid;

/**
 * @author Pavel Kurakin
 */
@RestController
@RequestMapping(value = "/api/calculate")
public class CalculationController {

    private CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public VirtuResponse<CalculationResult> getInsurancePremium(@Valid @RequestBody CalculationData calculationData) {
        return new VirtuResponse<>(calculationService.calculate(calculationData));
    }
}
