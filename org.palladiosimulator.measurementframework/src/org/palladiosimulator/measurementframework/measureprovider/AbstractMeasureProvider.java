package org.palladiosimulator.measurementframework.measureprovider;

import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.measurementframework.BasicMeasurement;
import org.palladiosimulator.measurementframework.MeasuringValue;
import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;

/**
 * Abstract implementation of a measure provider.
 * 
 * @author Sebastian Lehrig
 */
public abstract class AbstractMeasureProvider implements IMeasureProvider {

    /**
     * Default Constructor.
     */
    protected AbstractMeasureProvider() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V, Q extends Quantity> Measure<V, Q> getMeasureForMetric(final MetricDescription wantedMetric) {
        if (wantedMetric == null || !(wantedMetric instanceof BaseMetricDescription)) {
            throw new IllegalArgumentException("Only base metrics have measures attached.");
        }
        final MeasuringValue wantedMeasurement = getMeasurementForMetric(wantedMetric);
        if (wantedMeasurement == null || !(wantedMeasurement instanceof BasicMeasurement<?, ?>)) {
            throw new IllegalStateException("Measurement for a base metric is not an BasicMeasurement.");
        }
        final BasicMeasurement<V, Q> basicMeasurement = (BasicMeasurement<V, Q>) wantedMeasurement;
        return basicMeasurement.getMeasure();
    }

    /**
     * {@inheritDoc}
     */
    public abstract MeasuringValue getMeasurementForMetric(final MetricDescription metricDesciption);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract List<Measure<?, ?>> asList();

    /**
     * {@inheritDoc}
     */
    @Override
    public Measure<?, ?>[] asArray() {
        final List<Measure<?, ?>> asList = asList();
        final Measure<?, ?>[] result = new Measure<?, ?>[asList.size()];
        for (int i = 0; i < asList.size(); i++) {
            result[i] = asList.get(i);
        }
        return result;
    }
}
