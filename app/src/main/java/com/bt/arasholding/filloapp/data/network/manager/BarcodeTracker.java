package com.bt.arasholding.filloapp.data.network.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BarcodeTracker {
    private static BarcodeTracker instance;
    private Map<String, Integer> totalPiecesMap = new HashMap<>();
    private Map<String, Set<String>> scannedPiecesMap = new HashMap<>();
    private Map<String, List<String>> atfToBarcodeMap = new HashMap<>();

    private BarcodeTracker() {}

    public static BarcodeTracker getInstance() {
        if (instance == null) {
            instance = new BarcodeTracker();
        }
        return instance;
    }

    public Map<String, Integer> getTotalPiecesMap() {
        return totalPiecesMap;
    }

    public Map<String, Set<String>> getScannedPiecesMap() {
        return scannedPiecesMap;
    }

    public void addBarcodeToATF(String atfNo, String barcode) {
        List<String> barcodeList;
        if (atfToBarcodeMap.containsKey(atfNo)) {
            barcodeList = atfToBarcodeMap.get(atfNo);
        } else {
            barcodeList = new ArrayList<>();
        }
        if (!barcodeList.contains(barcode)) { // Barkod tekrar edilmediyse ekle
            barcodeList.add(barcode);
            atfToBarcodeMap.put(atfNo, barcodeList);
        }
    }

    // ATFNO'ya göre barkod listesine ulaşmak için getter
    public List<String> getBarcodesForATF(String atfNo) {
        if (atfToBarcodeMap.containsKey(atfNo)) {
            return atfToBarcodeMap.get(atfNo);
        } else {
            return new ArrayList<>(); // Eğer yoksa boş bir liste döndür
        }
    }

    public void reset() {
        totalPiecesMap.clear();
        scannedPiecesMap.clear();
    }
}
