package ru.xkpa.virtu.asset;

import ru.xkpa.virtu.common.BaseObjectId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Pavel Kurakin
 */
@Entity
@Table(name = "asset_kind")
public class AssetKind extends BaseObjectId {

    private String name;
    private float ratio;

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "ratio", nullable = false)
    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
