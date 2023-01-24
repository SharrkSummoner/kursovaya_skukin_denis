package com.example.kursovaya;

import javafx.scene.paint.LinearGradient;

public class Credit {
    private String name;
    private Float percent;
    private int maxsum;
    private int minsum;
    private String description;
    private String maturityDate;
    private int type;

    private LinearGradient gradient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public int getMaxsum() {
        return maxsum;
    }

    public void setMaxsum(int maxsum) {
        this.maxsum = maxsum;
    }

    public int getMinsum() {
        return minsum;
    }

    public void setMinsum(int minsum) {
        this.minsum = minsum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LinearGradient getGradient() {
        return gradient;
    }

    public void setGradient(LinearGradient gradient) {
        this.gradient = gradient;
    }
}
