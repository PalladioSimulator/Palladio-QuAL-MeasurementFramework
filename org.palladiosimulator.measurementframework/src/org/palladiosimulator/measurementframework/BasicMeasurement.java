package org.palladiosimulator.measurementframework;

import java.util.ArrayList;
import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.Identifier;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.NumericalBaseMetricDescription;

/**
 * Represents a basic measurement, i.e., a measurement for a {@see BaseMetricDescription}.
 * 
 * @param <VALUE_TYPE>
 *            denotes the class of the taken sample (Integer, Long, ...)
 * @param <QUANTITY>
 *            denotes the measured {@link Quantity}
 * 
 * @author Sebastian Lehrig
 */
public final class BasicMeasurement<VALUE_TYPE, QUANTITY extends Quantity> extends Measurement {

    /** The represented measure. */
    private final Measure<VALUE_TYPE, QUANTITY> measure;

    /**
     * Default Constructor.
     * 
     * @param measure
     *            The measure to be represented.
     * @param metricDescription
     *            The base metric to be represented.
     */
    public BasicMeasurement(final Measure<VALUE_TYPE, QUANTITY> measure, final BaseMetricDescription metricDescription) {
        super(metricDescription);
        checkMeasureDataType(measure, metricDescription);
        this.measure = measure;
    }

    /**
     * Checks whether a given measure and a given metric are compatible with each other.
     * 
     * @param measure
     *            The measure to be checked.
     * @param metricDescription
     *            The metric to be checked.
     */
    private void checkMeasureDataType(final Measure<VALUE_TYPE, QUANTITY> measure,
            final BaseMetricDescription metricDescription) {
        final Class<?> valueDataType;
        switch (metricDescription.getCaptureType()) {
        case IDENTIFIER:
            valueDataType = Identifier.class;
            break;
        case INTEGER_NUMBER:
            valueDataType = Long.class;
            break;
        case REAL_NUMBER:
            valueDataType = Double.class;
            break;
        default:
            valueDataType = null;
            break;
        }

        if (!valueDataType.isAssignableFrom(measure.getValue().getClass())) {
            throw new IllegalArgumentException("Datatype of measurement (" + measure.getValue().getClass().getName()
                    + ") not compatible with declared base metric (" + valueDataType.getName() + "; "
                    + metricDescription.getName() + ")");
        }

        if (metricDescription instanceof NumericalBaseMetricDescription) {
            final NumericalBaseMetricDescription numericalBaseMetricDescription;
            numericalBaseMetricDescription = (NumericalBaseMetricDescription) metricDescription;
            if (!measure.getUnit().isCompatible(numericalBaseMetricDescription.getDefaultUnit())) {
                throw new IllegalArgumentException("Unit of measurement not compatible with declared base metric");
            }
        }

    }

    /**
     * Returns the encapsulated measured value in conjunction with its measured {@link Quantity}.
     * 
     * @return the measured value and its quantity
     * @see Measure
     */
    public final Measure<VALUE_TYPE, QUANTITY> getMeasure() {
        return this.measure;
    }

    @Override
    public List<Measure<?, ?>> asList() {
        final ArrayList<Measure<?, ?>> result = new ArrayList<Measure<?, ?>>(1);
        result.add(this.measure);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "unchecked" })
    public <S, T extends Quantity> Measure<S, T> getMeasureForMetric(final MetricDescription wantedMetric) {
        if (wantedMetric == null || !(wantedMetric instanceof BaseMetricDescription)) {
            throw new IllegalArgumentException("Only base metrics have measures attached.");
        }
        final Measurement wantedMeasurement = getMeasurementForMetric(wantedMetric);
        if (wantedMeasurement == null || !(wantedMeasurement instanceof BasicMeasurement<?, ?>)) {
            throw new IllegalStateException("Measurement for a base metric is not an BasicMeasurement.");
        }
        final BasicMeasurement<S, T> basicMeasurement = (BasicMeasurement<S, T>) wantedMeasurement;
        return basicMeasurement.getMeasure();
    }

}
