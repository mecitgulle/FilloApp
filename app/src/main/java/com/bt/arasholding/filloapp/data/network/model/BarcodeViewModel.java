package com.bt.arasholding.filloapp.data.network.model;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BarcodeViewModel extends ViewModel {
    private Map<String, Integer> totalPiecesMap = new HashMap<>();
    private Map<String, Set<String>> scannedPiecesMap = new HashMap<>();

    public Map<String, Integer> getTotalPiecesMap() {
        return totalPiecesMap;
    }

    public Map<String, Set<String>> getScannedPiecesMap() {
        return scannedPiecesMap;
    }

    public void reset() {
        totalPiecesMap.clear();
        scannedPiecesMap.clear();
    }
}
