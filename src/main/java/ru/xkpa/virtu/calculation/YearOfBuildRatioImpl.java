package ru.xkpa.virtu.calculation;

/**
 * @author Pavel Kurakin
 */
public class YearOfBuildRatioImpl implements YearOfBuildRatio {

    @Override
    public float getRatio(int yearOfBuild) {

        float ratio;

        if (yearOfBuild < 2000) {
            ratio =  1.3f;
        } else if (yearOfBuild >= 2015) {
            ratio = 2.0f;
        } else {
            ratio = 1.6f;
        }

        return ratio;
    }
}
