package com.bt.arasholding.filloapp.data.network.model;

public class CustomSpinnerItem {
    private String text;
    private String value;

    public CustomSpinnerItem(String text, String value) {
        this.text = text;
        this.value = value;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getValue() {
        return value;
    }
}