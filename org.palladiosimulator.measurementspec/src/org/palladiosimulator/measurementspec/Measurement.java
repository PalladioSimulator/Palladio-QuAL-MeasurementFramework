package org.palladiosimulator.measurementspec;

import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.metricentity.MetricEntity;

public abstract class Measurement extends MetricEntity {

    /**
     * @param measuredMetric
     * @param measuredProbe
     * @param modelElementID
     */
    protected Measurement(final MetricDescription measuredMetric) {
        super(measuredMetric);
    }

    @SuppressWarnings("unchecked")
    public <V,Q extends Quantity> Measure<V,Q> getMeasureForMetric(final MetricDescription wantedMetric) {
        if (wantedMetric == null || !(wantedMetric instanceof BaseMetricDescription)) {
            throw new IllegalArgumentException("Only base metrics have measures attached.");
        }
        final Measurement wantedMeasurement = getMeasurementForMetric(wantedMetric);
        if (wantedMeasurement == null || !(wantedMeasurement instanceof BasicMeasurement<?, ?>)) {
            throw new IllegalStateException("Measurement for a base metric is not an BasicMeasurement.");
        }
        final BasicMeasurement<V,Q> basicMeasurement = (BasicMeasurement<V, Q>) wantedMeasurement;
        return basicMeasurement.getMeasure();
    }

    public abstract Measurement getMeasurementForMetric(final MetricDescription metricDesciption);

    public abstract List<Measure<?, ?>> asList();

    public Measure<?,?>[] asArray() {
        final List<Measure<?,?>> asList = asList();
        final Measure<?,?>[] result = new Measure<?,?>[asList.size()];
        for (int i = 0; i < asList.size(); i++) {
            result[i] = asList.get(i);
        }
        return result;
    }

}
