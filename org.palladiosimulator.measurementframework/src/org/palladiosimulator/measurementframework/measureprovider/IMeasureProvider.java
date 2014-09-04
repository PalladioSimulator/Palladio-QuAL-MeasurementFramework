package org.palladiosimulator.measurementframework.measureprovider;

import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.metricspec.MetricDescription;

/**
 * A measure provider offers access to an ordered list of measure objects as defined by the JScience
 * Framework.
 * 
 * @author Sebastian Lehrig
 */
public interface IMeasureProvider {

    /**
     * Returns a measure object by looking for a given metric conforming to that measure object.
     * 
     * @param wantedMetric
     *            The metric to look for.
     * @param <VALUE_TYPE>
     *            Value type of the measure, e.g., Double.
     * @param <QUANTITY>
     *            Quantity of the measure, e.g., 2.0 seconds.
     * 
     * @return A measure object conforming to the given metric.
     */
    public abstract <VALUE_TYPE, QUANTITY extends Quantity> Measure<VALUE_TYPE, QUANTITY> getMeasureForMetric(
            MetricDescription wantedMetric);

    /***
     * Returns the list of measure objects via a Java utils list.
     * 
     * @return the list of measure objects.
     */
    public abstract List<Measure<?, ?>> asList();

    /**
     * Returns the list of measure objects via an array.
     * 
     * @return the array of measure objects.
     */
    public abstract Measure<?, ?>[] asArray();

}