package com.example.myheroapp.models.viewholder_models;

public class ButtonObject {

    private String mBtnLabel;
    private int mClientId;

    public ButtonObject(String btnLabel, int clientId) {
        mBtnLabel = btnLabel;
        mClientId = clientId;
    }

    public String getBtnLabel() {
        return mBtnLabel;
    }

    public void setBtnLabel(String label) {
        mBtnLabel = label;
    }


    public int getClientId() {
        return mClientId;
    }

    public void setClientId(int clientId) {
        mClientId = clientId;
    }
}
