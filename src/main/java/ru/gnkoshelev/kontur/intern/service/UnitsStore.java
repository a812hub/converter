/*
 * UnitsStore
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UnitsStore {

    private Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = new HashMap<>();
    private Map<String, String> unitToBaseUnit = new HashMap<>();

    public UnitsStore() {
    }

    @Autowired
    public UnitsStore(ApplicationArguments args) throws IOException {
        String file = args.getNonOptionArgs().get(0);
        DataLoaderUtil.loadDataFromFile(file, this);
    }

    public UnitsStore(String file) throws IOException {
        DataLoaderUtil.loadDataFromFile(file, this);
    }

    public Map<String, Map<String, UnitOfMeasure>> getBaseUnitToUnitsMap() {
        return baseUnitToUnitsMap;
    }

    public void setBaseUnitToUnitsMap(Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap) {
        this.baseUnitToUnitsMap = baseUnitToUnitsMap;
    }

    public Map<String, String> getUnitToBaseUnit() {
        return unitToBaseUnit;
    }

    public void setUnitToBaseUnit(Map<String, String> unitToBaseUnit) {
        this.unitToBaseUnit = unitToBaseUnit;
    }
}
