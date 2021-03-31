package ru.gnkoshelev.kontur.intern.service;

import org.junit.Test;
import ru.gnkoshelev.kontur.intern.excertions.ConvertException;
import ru.gnkoshelev.kontur.intern.excertions.UnknownUnitException;

import static org.junit.Assert.assertEquals;

public class ConverterServiceTest {
    private static final ConverterService converterService = DataTestHelper.getConverterService();

    @Test
    public void convertEmptyToEmpty() throws ConvertException, UnknownUnitException {
        String actual = converterService.convert("", "");
        assertEquals("1", actual);
    }

    @Test
    public void convertEmptyToValue() throws ConvertException, UnknownUnitException {
        String actual = converterService.convert("", "м / км");
        assertEquals("1000", actual);
    }

    @Test
    public void convertValueToEmpty() throws ConvertException, UnknownUnitException {
        String actual = converterService.convert("км / м", "");
        assertEquals("1000", actual);
    }

    @Test
    public void convert1() throws ConvertException, UnknownUnitException {
        String actual = converterService.convert("м / с", "км / час");
        assertEquals("3.6", actual);
    }

    @Test
    public void convert2() throws ConvertException, UnknownUnitException {
        String actual = converterService.convert("с", "час");
        assertEquals("0.000277777777778", actual);
    }

    @Test(expected = UnknownUnitException.class)
    public void convertUnknownUnit1() throws ConvertException, UnknownUnitException {
        converterService.convert("неделя/ мс", "мин / час");
    }

    @Test(expected = UnknownUnitException.class)
    public void convertUnknownUnit2() throws ConvertException, UnknownUnitException {
        converterService.convert("неделя/ с", "мин / мс");
    }

    @Test(expected = ConvertException.class)
    public void convertConvertException1() throws ConvertException, UnknownUnitException {
        converterService.convert("м */ с", "км / час");
    }

    @Test(expected = ConvertException.class)
    public void convertConvertException2() throws ConvertException, UnknownUnitException {
        converterService.convert("м * м / с", "км / час");
    }
}
