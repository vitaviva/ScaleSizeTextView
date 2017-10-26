package com.vitaviva.scalesizetextview;

public class TextSizeScaleChangedAction {

    private ScaleTextSizeUtil.ScaleInfo scaleInfo;

    public TextSizeScaleChangedAction() {

    }

    public TextSizeScaleChangedAction(ScaleTextSizeUtil.ScaleInfo scaleInfo) {
        this.scaleInfo = scaleInfo;
    }

    public ScaleTextSizeUtil.ScaleInfo getScaleInfo() {
        return scaleInfo;
    }

    public void setScaleInfo(ScaleTextSizeUtil.ScaleInfo scaleInfo) {
        this.scaleInfo = scaleInfo;
    }
}
