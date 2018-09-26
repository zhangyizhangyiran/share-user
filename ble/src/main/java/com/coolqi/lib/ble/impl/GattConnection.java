package com.coolqi.lib.ble.impl;

/**
 * Created by yangcai on 17-4-21.
 */

public interface GattConnection {

    void connectFailed(int connectStatus);

    void connectScuess();
}
