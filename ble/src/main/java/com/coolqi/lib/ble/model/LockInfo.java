package com.coolqi.lib.ble.model;

/**
 * Created by yangcai on 17-6-19.
 */

public class LockInfo {

    private String macAddress;

    private String key;

    private String password;

    private String voiceText;

    private boolean needVoice;

    public LockInfo(String macAddress, String key, String password) {
        this.macAddress = macAddress;
        this.key = key;
        this.password = password;
    }


    public String getMacAddress() {
        return macAddress;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public String getVoiceText() {
        return voiceText;
    }

    public void setVoiceText(String voiceText) {
        this.voiceText = voiceText;
    }

    public boolean isNeedVoice() {
        return needVoice;
    }

    public void setNeedVoice(boolean needVoice) {
        this.needVoice = needVoice;
    }
}
