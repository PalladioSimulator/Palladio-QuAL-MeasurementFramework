package org.palladiosimulator.measurementspec;

import java.util.ArrayList;
import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.Identifier;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.NumericalBaseMetricDescription;

/**
 * Represents a basic measurement, i.e., a measurement for a {@see BaseMetricDescription}
 *
 * @param <V>
 *            denotes the class of the taken sample (Integer, Long, ...)
 * @param <Q>
 *            denotes the measured {@link Quantity}
 * @author pmerkle
 * 
 */
public final class BasicMeasurement<V, Q extends Quantity> extends Measurement {

    /** the measured value and its quantity. */
    private final Measure<V, Q> measure;

    public BasicMeasurement(final Measure<V, Q> measure, final MetricDescription metricDescription) {
        super(metricDescription);
        if (!(metricDescription instanceof BaseMetricDescription)) {
            throw new IllegalArgumentException("A basic measurement must have a base metric description");
        }
        checkMeasureDataType(measure, metricDescription);
        this.measure = measure;
    }

    /**
     * @param measure
     * @param metricDescription
     */
    protected void checkMeasureDataType(final Measure<V, Q> measure, final MetricDescription metricDescription) {
        final BaseMetricDescription baseMetricDescription = (BaseMetricDescription) metricDescription;
        final Class<?> valueDataType;
        switch (baseMetricDescription.getCaptureType()) {
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
            throw new IllegalArgumentException("Datatype of measurement not compatible with declared base metric");
        }

        if (baseMetricDescription instanceof NumericalBaseMetricDescription) {
            final NumericalBaseMetricDescription numericalBaseMetricDescription = (NumericalBaseMetricDescription) baseMetricDescription;
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
    final Measure<V, Q> getMeasure() {
        return measure;
    }

    @Override
    public BasicMeasurement<V, Q> getMeasurementForMetric(final MetricDescription metricDesciption) {
        if (!metricDesciption.getId().equals(this.getMetricDesciption().getId())) {
            return null;
        }
        return this;
    }

    @Override
    public List<Measure<?, ?>> asList() {
        final ArrayList<Measure<?,?>> result = new ArrayList<Measure<?,?>>(1);
        result.add(this.measure);
        return result;
    }
}
