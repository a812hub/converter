package ru.gnkoshelev.kontur.intern.service;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UnitsStoreTest {

    private static final Map<String, Map<String, UnitOfMeasure>> baseUnitToUnitsMap =
            DataTestHelper.getUnitsStore().getBaseUnitToUnitsMap();

    private static final Map<String, String> unitToBaseUnit = DataTestHelper.getUnitsStore().getUnitToBaseUnit();

    @Test
    public void testFile1() throws IOException {
        String file = "src/test/resources/testFile1.csv";
        UnitsStore unitsStore = new UnitsStore(file);

        assertEquals(baseUnitToUnitsMap, unitsStore.getBaseUnitToUnitsMap());
        assertEquals(unitToBaseUnit, unitsStore.getUnitToBaseUnit());
    }

    @Test
    public void testFile2() throws IOException {
        String file = "src/test/resources/testFile2.csv";
        UnitsStore unitsStore = new UnitsStore(file);

        assertEquals(baseUnitToUnitsMap, unitsStore.getBaseUnitToUnitsMap());
        assertEquals(unitToBaseUnit, unitsStore.getUnitToBaseUnit());
    }

    @Test
    public void testFile3() throws IOException {
        String file = "src/test/resources/testFile3.csv";
        UnitsStore unitsStore = new UnitsStore(file);

        assertEquals(baseUnitToUnitsMap, unitsStore.getBaseUnitToUnitsMap());
        assertEquals(unitToBaseUnit, unitsStore.getUnitToBaseUnit());
    }

    @Test
    public void testFile4() throws IOException {
        String file = "src/test/resources/testFile4.csv";
        UnitsStore unitsStore = new UnitsStore(file);

        assertEquals(baseUnitToUnitsMap, unitsStore.getBaseUnitToUnitsMap());
        assertEquals(unitToBaseUnit, unitsStore.getUnitToBaseUnit());
    }
}
