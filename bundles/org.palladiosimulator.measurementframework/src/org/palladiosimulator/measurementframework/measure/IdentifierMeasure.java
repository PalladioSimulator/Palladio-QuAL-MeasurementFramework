package org.palladiosimulator.measurementframework.measure;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.palladiosimulator.metricspec.Identifier;

/**
 * Holds <code>Identifier</code> values that are used to characterize textual base metrics.
 * 
 * @param <QUANTITY>
 *            the quantity to be used, e.g., <code>Dimensionless</code> with <code>Unit.ONE</code>.
 * 
 * @author Steffen Becker, Sebastian Lehrig
 */
public class IdentifierMeasure<QUANTITY extends Quantity> extends Measure<Identifier, QUANTITY> {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = -4805416155308639141L;

    /** The given identifier used as a measure value. */
    private final Identifier value;

    /** The given unit conforming to <code>QUANTITY</code>. */
    private final Unit<QUANTITY> unit;

    /**
     * Default constructor. Initializes this measure based on a given identifier and a unit
     * conforming to <code>QUANTITY</code>.
     * 
     * @param identifier
     *            the identifier used for initialization.
     * @param unit
     *            the unit used for initialization.
     */
    public IdentifierMeasure(final Identifier identifier, final Unit<QUANTITY> unit) {
        super();
        this.value = identifier;
        this.unit = unit;
    }

    @Override
    public Identifier getValue() {
        return value;
    }

    @Override
    public Unit<QUANTITY> getUnit() {
        return unit;
    }

    @Override
    public Measure<Identifier, QUANTITY> to(final Unit<QUANTITY> unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double doubleValue(final Unit<QUANTITY> unit) {
        throw new UnsupportedOperationException();
    }

    /**
     * Factory for IdentifierMeasures based on the given identifier and unit.
     * 
     * @param identifier
     *            the identifier used for initialization.
     * @param unit
     *            the unit used for initialization.
     * @param <Q> the quantity to be used.
     * @return a newly created IdentifierMeasure object.
     */
    public static <Q extends Quantity> IdentifierMeasure<Q> valueOf(final Identifier identifier, final Unit<Q> unit) {
        return new IdentifierMeasure<Q>(identifier, unit);
    }

}
