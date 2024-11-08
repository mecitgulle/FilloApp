package com.bt.arasholding.filloapp.data.network.model;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MultiDeliveryViewModel extends ViewModel {
    private Map<String, HashSet<String>> scannedATFMap = new HashMap<>();
    private Map<String, Integer> totalPiecesMap = new HashMap<>();

    public Map<String, HashSet<String>> getScannedATFMap() {
        return scannedATFMap;
    }

    public Map<String, Integer> getTotalPiecesMap() {
        return totalPiecesMap;
    }

    // Verileri temizlemek için bir metot
    public void clearData() {
        scannedATFMap.clear();
        totalPiecesMap.clear();
    }
}
