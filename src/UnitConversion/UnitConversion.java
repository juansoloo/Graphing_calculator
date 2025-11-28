package UnitConversion;

import java.util.HashMap;
import java.util.Map;

public class UnitConversion {
    // base unit: meter
    private final Map<String, Double> lengthUnitMap = new HashMap<>();

    public UnitConversion() {
        lengthUnitMap.put("IN", 0.0254);
        lengthUnitMap.put("FT", 0.3048);
        lengthUnitMap.put("YD", 0.9144);
        lengthUnitMap.put("MI", 1609.34);

        lengthUnitMap.put("MM", 0.001);
        lengthUnitMap.put("CM", 0.01);
        lengthUnitMap.put("M", 1.0);
        lengthUnitMap.put("KM", 1000.0);
    }

    // source -> base (meters)
    public double convertToBase(String fromUnit, double value) {
        Double fromFactor = lengthUnitMap.get(fromUnit);
        if (fromFactor == null) {
            throw new IllegalArgumentException("Unknown length unit: " + fromUnit);
        }
        return value * fromFactor;
    }

    // base (meters) -> target
    public double convertToTarget(String toUnit, double valueInBase) {
        Double toFactor = lengthUnitMap.get(toUnit);
        if (toFactor == null) {
            throw new IllegalArgumentException("Unknown length unit: " + toUnit);
        }
        return valueInBase / toFactor;
    }

    public double convertToFahr(double celsius) {
        return (celsius * 1.8) + 32.0;
    }

    public double convertToCel(double fahr) {
        return (fahr - 32.0) / 1.8;
    }
}
