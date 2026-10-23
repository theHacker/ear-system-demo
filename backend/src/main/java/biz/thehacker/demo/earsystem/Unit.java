package biz.thehacker.demo.earsystem;

/**
 * Enum for unit of values.
 * <p>
 * <strong>Careful: Identifiers are a mix of German and English</strong>
 *
 * <p>
 * See <a href="https://test.ear-system.de/ear-soap/v2/doc/xml_ear_Masseinheit.html"><code>Masseinheit</code> in docs</a>.
 */
public enum Unit {

    KILOGRAM(1, 3), // Note: Typo is intensional!
    STUECK(2, 0),
    TONNEN(3, 6);

    private int value;
    private int maxDecimalPlaces;

    Unit(int value, int maxDecimalPlaces) {
        this.value = value;
        this.maxDecimalPlaces = maxDecimalPlaces;
    }

    public int getValue() {
        return value;
    }

    public int getMaxDecimalPlaces() {
        return maxDecimalPlaces;
    }
}
