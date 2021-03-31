package ru.gnkoshelev.kontur.intern.service;

import java.util.HashMap;
import java.util.Map;

public class DataTestHelper {
    private static final UnitsStore unitsStore = new UnitsStore();
    private static final Converter converter = new Converter(unitsStore);
    private static final ConverterService converterService = new ConverterService(converter);

    static {
        Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap = new HashMap<>();

        Map<String, UnitOfMeasure> insideMapTime = new HashMap<>();
        insideMapTime.put("неделя", new UnitOfMeasure("неделя", 1));
        insideMapTime.put("день", new UnitOfMeasure("день", 7));
        insideMapTime.put("час", new UnitOfMeasure("час", 168));
        insideMapTime.put("мин", new UnitOfMeasure("мин", 10080));
        insideMapTime.put("с", new UnitOfMeasure("с", 604800));
        baseUnitToUnitsMap.put("неделя", insideMapTime);

        Map<String, UnitOfMeasure> insideMapMeter = new HashMap<>();
        insideMapMeter.put("км", new UnitOfMeasure("км", 1));
        insideMapMeter.put("м", new UnitOfMeasure("м", 1000));
        insideMapMeter.put("дм", new UnitOfMeasure("дм", 10000));
        insideMapMeter.put("см", new UnitOfMeasure("см", 100000));
        insideMapMeter.put("мм", new UnitOfMeasure("мм", 1000000));
        baseUnitToUnitsMap.put("км", insideMapMeter);

        Map<String, String> unitToBaseUnit = new HashMap<>();
        unitToBaseUnit.put("неделя", "неделя");
        unitToBaseUnit.put("день", "неделя");
        unitToBaseUnit.put("час", "неделя");
        unitToBaseUnit.put("мин", "неделя");
        unitToBaseUnit.put("с", "неделя");
        unitToBaseUnit.put("км", "км");
        unitToBaseUnit.put("м", "км");
        unitToBaseUnit.put("дм", "км");
        unitToBaseUnit.put("см", "км");
        unitToBaseUnit.put("мм", "км");

        unitsStore.setBaseUnitToUnitsMap(baseUnitToUnitsMap);
        unitsStore.setUnitToBaseUnit(unitToBaseUnit);
    }

    public static UnitsStore getUnitsStore() {
        return unitsStore;
    }

    public static ConverterService getConverterService() {
        return converterService;
    }
}
