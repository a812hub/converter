/*
 * DataLoaderUtil
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataLoaderUtil {
    public static void loadDataFromFile(String pathToFile,
                                        UnitsStore store) throws IOException {

        Map<String, String> unitToBaseUnit = store.getUnitToBaseUnit();

        File file = getFile(pathToFile);
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            String firstUnit = line.substring(0, line.indexOf(','));
            String secondUnit = line.substring(line.indexOf(',') + 1, line.lastIndexOf(','));
            double value = Double.parseDouble(line.substring(line.lastIndexOf(',') + 1));
            if (value < 1) {
                String tmp = firstUnit;
                firstUnit = secondUnit;
                secondUnit = tmp;
                value = 1 / value;
            }

            if (!unitToBaseUnit.containsKey(firstUnit) && !unitToBaseUnit.containsKey(secondUnit)) {
                addTwoUnits(firstUnit, secondUnit, value, store);
            } else if ((unitToBaseUnit.containsKey(firstUnit) && !unitToBaseUnit.containsKey(secondUnit))
                    || (!unitToBaseUnit.containsKey(firstUnit) && unitToBaseUnit.containsKey(secondUnit))) {
                addOneUnit(firstUnit, secondUnit, value, store);
            } else if (!unitToBaseUnit.get(firstUnit).equals(unitToBaseUnit.get(secondUnit))) {
                rebuildData(firstUnit, secondUnit, value, store);
            }
        }
        reader.close();
    }

    private static void addTwoUnits(String baseUnit, String unit, double value, UnitsStore store) {
        Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = store.getBaseUnitToUnitsMap();
        Map<String, String> unitToBaseUnit = store.getUnitToBaseUnit();

        Map<String, UnitOfMeasure> units = new HashMap<>();
        units.put(baseUnit, new UnitOfMeasure(baseUnit, 1d));
        units.put(unit, new UnitOfMeasure(unit, value));
        baseUnitToUnitsMap.put(baseUnit, units);
        unitToBaseUnit.put(baseUnit, baseUnit);
        unitToBaseUnit.put(unit, baseUnit);
    }

    private static void addOneUnit(String firstUnit, String secondUnit, double value, UnitsStore store) {
        Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = store.getBaseUnitToUnitsMap();
        Map<String, String> unitToBaseUnit = store.getUnitToBaseUnit();

        if (unitToBaseUnit.containsKey(firstUnit) && !unitToBaseUnit.containsKey(secondUnit)) {
            String baseUnit = unitToBaseUnit.get(firstUnit);
            Map<String, UnitOfMeasure> units = baseUnitToUnitsMap.get(baseUnit);
            if (baseUnitToUnitsMap.containsKey(firstUnit)) {
                units.put(secondUnit, new UnitOfMeasure(secondUnit, value));
            } else {
                double diff = units.get(firstUnit).getValue();
                units.put(secondUnit, new UnitOfMeasure(secondUnit, value * diff));
            }
            unitToBaseUnit.put(secondUnit, baseUnit);
        } else if (!unitToBaseUnit.containsKey(firstUnit) && unitToBaseUnit.containsKey(secondUnit)) {
            String baseUnit = unitToBaseUnit.get(secondUnit);
            Map<String, UnitOfMeasure> units = baseUnitToUnitsMap.get(baseUnit);
            if (!baseUnitToUnitsMap.containsKey(secondUnit) && units.get(secondUnit).getValue() > value) {
                double diff = units.get(secondUnit).getValue() / value;
                units.put(firstUnit, new UnitOfMeasure(firstUnit, diff));
                unitToBaseUnit.put(firstUnit, baseUnit);
            } else {
                double diff = value / units.get(secondUnit).getValue();
                for (Map.Entry<String, UnitOfMeasure> entry : units.entrySet()) {
                    double oldValue = entry.getValue().getValue();
                    entry.getValue().setValue(oldValue * diff);
                }
                units.put(firstUnit, new UnitOfMeasure(firstUnit, 1d));
                baseUnitToUnitsMap.remove(baseUnit);
                baseUnitToUnitsMap.put(firstUnit, units);
                changeBaseUnit(baseUnit, firstUnit, store);
                unitToBaseUnit.put(firstUnit, firstUnit);
            }
        }
    }

    private static void rebuildData(String firstUnit, String secondUnit, double value, UnitsStore store) {
        Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = store.getBaseUnitToUnitsMap();
        Map<String, String> unitToBaseUnit = store.getUnitToBaseUnit();

        String firstBaseUnit = unitToBaseUnit.get(firstUnit);
        String secondBaseUnit = unitToBaseUnit.get(secondUnit);
        Map<String, UnitOfMeasure> units1 = baseUnitToUnitsMap.get(firstBaseUnit);
        Map<String, UnitOfMeasure> units2 = baseUnitToUnitsMap.get(secondBaseUnit);

        if (!baseUnitToUnitsMap.containsKey(firstUnit) && !baseUnitToUnitsMap.containsKey(secondUnit)) {
            double diff = units1.get(firstUnit).getValue() * value / units2.get(secondUnit).getValue();
            if (diff > 1) {
                rebuildData0(units1, units2, diff, secondBaseUnit, firstBaseUnit, store);
            } else {
                diff = units2.get(secondUnit).getValue() / (units1.get(firstUnit).getValue() * value);
                rebuildData0(units2, units1, diff, firstBaseUnit, secondBaseUnit, store);
            }
        } else if (!baseUnitToUnitsMap.containsKey(firstUnit) && baseUnitToUnitsMap.containsKey(secondUnit)) {
            double diff = units1.get(firstUnit).getValue() * value;
            rebuildData0(units1, units2, diff, secondBaseUnit, firstBaseUnit, store);
        } else if (baseUnitToUnitsMap.containsKey(firstUnit) && !baseUnitToUnitsMap.containsKey(secondUnit)) {
            double diff = value / units2.get(secondUnit).getValue();
            rebuildData0(units1, units2, diff, secondBaseUnit, firstUnit, store);
        } else if (baseUnitToUnitsMap.containsKey(firstUnit) && baseUnitToUnitsMap.containsKey(secondUnit)) {
            rebuildData0(units1, units2, value, secondUnit, firstUnit, store);
        }
    }

    private static void rebuildData0(Map<String, UnitOfMeasure> dest, Map<String, UnitOfMeasure> src, double diff,
                                     String oldBaseUnit, String newBaseUnit, UnitsStore store) {
        Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = store.getBaseUnitToUnitsMap();

        mergeUnitMaps(dest, src, diff);
        baseUnitToUnitsMap.remove(oldBaseUnit);
        changeBaseUnit(oldBaseUnit, newBaseUnit, store);
    }

    private static void mergeUnitMaps(Map<String, UnitOfMeasure> dest, Map<String, UnitOfMeasure> src, double diff) {
        for (Map.Entry<String, UnitOfMeasure> entry : src.entrySet()) {
            UnitOfMeasure unitOfMeasure = entry.getValue();
            unitOfMeasure.setValue(unitOfMeasure.getValue() * diff);
            dest.put(entry.getKey(), unitOfMeasure);
        }
    }

    private static void changeBaseUnit(String oldBaseUnit, String newBaseUnit, UnitsStore store) {
        Map<String, String> unitToBaseUnit = store.getUnitToBaseUnit();

        for (Map.Entry<String, String> entry : unitToBaseUnit.entrySet()) {
            if (entry.getValue().equals(oldBaseUnit)) {
                entry.setValue(newBaseUnit);
            }
        }
    }

    private static File getFile(String pathToFile) {
        File file = Paths.get(pathToFile).toFile();
        if (!file.exists()) {
            if (pathToFile.startsWith("/") || pathToFile.startsWith("\\")) {
                file = Paths.get(pathToFile.substring(1)).toFile();
            }
        }
        return file;
    }
}
