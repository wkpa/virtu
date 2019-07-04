package ru.xkpa.virtu.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xkpa.virtu.common.VirtuResponse;
import java.util.List;

/**
 * @author Pavel Kurakin
 */
@RestController
@RequestMapping(value = "/api/assetkinds")
public class AssetKindController {

    private AssetKindRepository assetKindRepository;

    @Autowired
    public AssetKindController(AssetKindRepository assetKindRepository) {
        this.assetKindRepository = assetKindRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public VirtuResponse<List<AssetKind>> getAssetKinds() {
        return new VirtuResponse<>(assetKindRepository.findAll());
    }

}
