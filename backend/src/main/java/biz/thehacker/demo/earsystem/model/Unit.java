package biz.thehacker.demo.earsystem.model;

/**
 * Enum for unit of values.
 * <p>
 * See <a href="https://test.ear-system.de/ear-soap/v2/doc/xml_ear_Masseinheit.html"><code>Masseinheit</code> in docs</a>.
 */
public enum Unit {

    /** kilograms, match {@code Masseinheit}'s {@code KILOGRAM}. */
    KILOGRAM(1, 3),
    /** pieces, match {@code Masseinheit}'s {@code STUECK}. */
    PIECE(2, 0),
    /** metric tons, match {@code Masseinheit}'s {@code TONNE}. */
    TON(3, 6);

    private final int value;
    private final int maxDecimalPlaces;

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
