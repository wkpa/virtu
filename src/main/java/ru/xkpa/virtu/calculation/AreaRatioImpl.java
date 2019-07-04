package ru.xkpa.virtu.calculation;

/**
 * @author Pavel Kurakin
 */
public class AreaRatioImpl implements AreaRatio {

    @Override
    public float getRatio(float area) {

        float ratio;

        if (area < 50) {
            ratio = 1.2f;
        } else  if (area > 100) {
            ratio = 2.0f;
        } else {
            ratio = 1.5f;
        }

        return ratio;
    }
}
