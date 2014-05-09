package org.palladiosimulator.measurementspec;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.palladiosimulator.metricspec.Identifier;

public class IdentifierMeasure<Q extends Quantity> extends Measure<Identifier, Q> {
    private static final long serialVersionUID = -4805416155308639141L;
    private final Identifier value;
    private final Unit<Q> unit;

    public IdentifierMeasure(final Identifier identifier, final Unit<Q> unit) {
        super();
        this.value = identifier;
        this.unit = unit;
    }

    @Override
    public Identifier getValue() {
        return value;
    }

    @Override
    public Unit<Q> getUnit() {
        return unit;
    }

    @Override
    public Measure<Identifier, Q> to(final Unit<Q> unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double doubleValue(final Unit<Q> unit) {
        throw new UnsupportedOperationException();
    }

    public static <U extends Quantity> IdentifierMeasure<U> valueOf(final Identifier identifier, final Unit<U> unit) {
        return new IdentifierMeasure<U>(identifier, unit);
    }

}
