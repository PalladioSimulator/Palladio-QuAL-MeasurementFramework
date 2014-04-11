package org.palladiosimulator.measurementspec;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.edp2.models.ExperimentData.BaseMetricDescription;
import org.palladiosimulator.edp2.models.ExperimentData.Identifier;
import org.palladiosimulator.edp2.models.ExperimentData.MetricDescription;
import org.palladiosimulator.edp2.models.ExperimentData.NumericalBaseMetricDescription;
import org.palladiosimulator.measurementspec.requestcontext.RequestContext;

/**
 * Represents a sample which is taken for a probe.
 * <p>
 * Use this class to store any type of probe sample. Therefore the class parameters V and Q have to
 * be specified which represents the sample type.
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

    /**
     * Class constructor specifying the measured value, the id and type of the probe.
     * 
     * @param measure
     *            the measured value in conjunction with its {@link Quantity}
     * @param measuredProbe
     * @param modelElementID
     * @param probeID
     *            the id of the probe
     * @param probeType
     *            the type of the probe
     * @see Measure
     * @see ProbeType
     */
    public BasicMeasurement(final Measure<V, Q> measure, final MetricDescription metricDescription,
            final MeasurementSource measurementSource, final RequestContext requestContext, final String modelElementID) {
        super(requestContext, metricDescription, measurementSource, modelElementID);
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
        if (!metricDesciption.getUuid().equals(this.metricDesciption.getUuid())) {
            return null;
        }
        return this;
    }
}
