package org.palladiosimulator.measurementframework;

import java.util.List;

import jakarta.measure.Measure;

import org.palladiosimulator.measurementframework.measureprovider.IMeasureProvider;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.metricentity.IMetricEntity;
import org.palladiosimulator.metricspec.metricentity.MetricEntity;

/**
 * Measuring values are taken in a list of measures, a metric description, and at a concrete
 * measuring point.
 * 
 * TODO Add measuring point here.
 * 
 * @author Sebastian Lehrig
 */
public abstract class MeasuringValue implements IMeasureProvider, IMetricEntity {

    /** Delegate to a metric entity holding the represented metric. */
    private final MetricEntity metricEntity;

    /**
     * Default constructor.
     * 
     * @param metricDescription
     *            Delegator object for implementing IMetricEntity.
     */
    public MeasuringValue(final MetricDescription metricDescription) {
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

    /**
     * Returns this measuring value in case it conforms to the given metric description.
     * 
     * @param metricDesciption
     *            the given metric description.
     * @return this measuring value if it conforms to the given metric description,
     *         <code>null</code> otherwise.
     */
    public MeasuringValue getMeasuringValueForMetric(final MetricDescription metricDesciption) {
        if (!metricDesciption.getId().equals(getMetricDesciption().getId())) {
            return null;
        }
        return this;
    }
}
