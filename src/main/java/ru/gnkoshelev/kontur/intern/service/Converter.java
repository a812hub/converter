/*
 * Converter
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gnkoshelev.kontur.intern.excertions.ConvertException;
import ru.gnkoshelev.kontur.intern.excertions.UnknownUnitException;

import java.util.HashMap;
import java.util.Map;

@Component
public class Converter {

    private UnitsStore store;

    @Autowired
    public Converter(UnitsStore store) {
        this.store = store;
    }

    public double convert(String from, String to) throws UnknownUnitException, ConvertException {
        from = normalizeExpression(from);
        to = normalizeExpression(to);
        checkExpression(from);
        checkExpression(to);

        String unionExpr = getUnionExpression(from, to);
        checkUnionExpression(unionExpr);

        double numerator = getMultiplication(unionExpr.substring(0, unionExpr.indexOf('/')));
        double denominator = getMultiplication(unionExpr.substring(unionExpr.indexOf('/') + 1));
        return numerator / denominator;
    }

    private String normalizeExpression(String expr) {
        if (expr.trim().equals("")) {
            return "1";
        }
        return expr.replaceAll(" *\\* *", "*").replaceAll(" */ *", "/").trim();
    }

    private void checkExpression(String expr) throws ConvertException {
        if (!expr.matches("[1а-яёА-ЯЁa-zA-Z]+(\\*[а-яёА-ЯЁa-zA-Z]+)*(/[а-яёА-ЯЁa-zA-Z]+(\\*[а-яёА-ЯЁa-zA-Z]+)*)?")) {
            throw new ConvertException("Bad body request: " + expr);
        }
    }

    private String getUnionExpression(String from, String to) {
        String fromNumerator;
        String fromDenominator;
        String toNumerator;
        String toDenominator;

        if (from.contains("/")) {
            fromNumerator = from.substring(0, from.indexOf('/'));
            fromDenominator = from.substring(from.indexOf('/') + 1);
        } else {
            fromNumerator = from;
            fromDenominator = "1";
        }

        if (to.contains("/")) {
            toNumerator = to.substring(0, to.indexOf('/'));
            toDenominator = to.substring(to.indexOf('/') + 1);
        } else {
            toNumerator = to;
            toDenominator = "1";
        }

        return toNumerator + "*" + fromDenominator + "/" + fromNumerator + "*" + toDenominator;
    }

    private void checkUnionExpression(String expr) throws ConvertException, UnknownUnitException {
        Map<String, Integer> checker = new HashMap<>();

        String numerator = expr.substring(0, expr.indexOf('/'));
        String denominator = expr.substring(expr.indexOf('/') + 1);
        String[] numeratorUnits = numerator.split("\\*");
        String[] denominatorUnits = denominator.split("\\*");

        for (String unit : numeratorUnits) {
            if (!unit.equals("1")) {
                if (!store.getUnitToBaseUnit().containsKey(unit)) {
                    throw new UnknownUnitException("Unknown measure unit: " + unit);
                }
                String baseUnit = store.getUnitToBaseUnit().get(unit);
                checker.merge(baseUnit, 1, Integer::sum);
            }
        }
        for (String unit : denominatorUnits) {
            if (!unit.equals("1")) {
                if (!store.getUnitToBaseUnit().containsKey(unit)) {
                    throw new UnknownUnitException("Unknown measure unit: " + unit);
                }
                String baseUnit = store.getUnitToBaseUnit().get(unit);
                checker.merge(baseUnit, -1, Integer::sum);
            }
        }
        if (!isMapEmpty(checker)) {
            throw new ConvertException("Bad expression");
        }
    }

    private boolean isMapEmpty(Map<String, Integer> map) {
        for (Integer value : map.values()) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    private double getMultiplication(String expr) {
        String[] units = expr.split("\\*");
        double result = 1d;
        for (String unit : units) {
            if (!unit.equals("1")) {
                String baseUnit = store.getUnitToBaseUnit().get(unit);
                double value = store.getBaseUnitToUnitsMap().get(baseUnit).get(unit).getValue();
                result = result * value;
            }
        }
        return result;
    }
}
