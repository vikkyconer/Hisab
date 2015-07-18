package com.example.vikky.hisab;

/**
 * Created by vikky on 7/2/15.
 */
public class Color {
    private String colorName;
    private String colorHexValue;
    private String colorPosition;
    private Boolean status;

    public String getColorHexValue() {
        return colorHexValue;
    }

    public void setColorHexValue(String colorHexValue) {
        this.colorHexValue = colorHexValue;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorPosition() {
        return colorPosition;
    }

    public void setColorPosition(String colorPosition) {
        this.colorPosition = colorPosition;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
