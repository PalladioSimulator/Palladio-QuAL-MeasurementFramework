package org.palladiosimulator.measurementframework.measureprovider;

import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.metricspec.MetricDescription;

/**
 * A measure provider offers a (set of) measure(s) as defined by the JScience Framework.
 * 
 * @author Sebastian Lehrig
 */
public interface IMeasureProvider {

    /**
     * Returns a measure by looking for a given metric conforming to that measure.
     * 
     * @param wantedMetric
     *            The metric to look for.
     * @return A measure object conforming to the given metric.
     */
    public abstract <V, Q extends Quantity> Measure<V, Q> getMeasureForMetric(MetricDescription wantedMetric);

    public abstract List<Measure<?, ?>> asList();

    public abstract Measure<?, ?>[] asArray();

}