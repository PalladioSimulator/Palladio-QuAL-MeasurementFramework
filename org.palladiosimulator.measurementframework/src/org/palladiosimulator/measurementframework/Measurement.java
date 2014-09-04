package org.palladiosimulator.measurementframework;

import java.util.List;

import javax.measure.Measure;

import org.palladiosimulator.measurementframework.measureprovider.IMeasureProvider;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.metricentity.IMetricEntity;
import org.palladiosimulator.metricspec.metricentity.MetricEntity;

/**
 * Measurements are taken in a (set of) measure(s), a metric, and at a concrete measuring point.
 * 
 * TODO Add measuring point here.
 * 
 * @author Sebastian Lehrig
 */
public abstract class Measurement implements IMeasureProvider, IMetricEntity {

    /** Delegate to a metric entity holding the represented metric. */
    private final MetricEntity metricEntity;

    /**
     * Default constructor.
     * 
     * @param metricDescription
     *            Delegator object for implementing IMetricEntity.
     */
    public Measurement(final MetricDescription metricDescription) {
        super();

        this.metricEntity = new MetricEntity(metricDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MetricDescription getMetricDesciption() {
        return this.metricEntity.getMetricDesciption();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatibleWith(MetricDescription other) {
        return this.metricEntity.isCompatibleWith(other);
    }

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

    public Measurement getMeasurementForMetric(final MetricDescription metricDesciption) {
        if (!metricDesciption.getId().equals(getMetricDesciption().getId())) {
            return null;
        }
        return this;
    }
}
