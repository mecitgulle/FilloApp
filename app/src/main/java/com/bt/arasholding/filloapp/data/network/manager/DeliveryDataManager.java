package com.bt.arasholding.filloapp.data.network.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DeliveryDataManager {
    private static DeliveryDataManager instance;
    private Map<String, HashSet<String>> scannedATFMap = new HashMap<>();
    private Map<String, Integer> totalPiecesMap = new HashMap<>();

    private DeliveryDataManager() {}

    public static synchronized DeliveryDataManager getInstance() {
        if (instance == null) {
            instance = new DeliveryDataManager();
        }
        return instance;
    }

    public Map<String, HashSet<String>> getScannedATFMap() {
        return scannedATFMap;
    }

    public Map<String, Integer> getTotalPiecesMap() {
        return totalPiecesMap;
    }
    public void reset() {
        totalPiecesMap.clear();
        scannedATFMap.clear();
    }
}
