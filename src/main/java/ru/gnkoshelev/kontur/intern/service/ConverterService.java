/*
 * ConverterService
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gnkoshelev.kontur.intern.excertions.ConvertException;
import ru.gnkoshelev.kontur.intern.excertions.UnknownUnitException;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class ConverterService {
    private final Converter converter;

    @Autowired
    public ConverterService(Converter converter) {
        this.converter = converter;
    }

    public String convert(String from, String to) throws UnknownUnitException, ConvertException {
        NumberFormat formatter = NumberFormat.getInstance(Locale.ENGLISH);
        formatter.setMaximumFractionDigits(15);
        formatter.setGroupingUsed(false);
        return formatter.format(converter.convert(from, to));
    }
}
