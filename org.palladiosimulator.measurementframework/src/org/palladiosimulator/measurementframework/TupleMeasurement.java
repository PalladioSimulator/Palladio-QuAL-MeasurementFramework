package org.palladiosimulator.measurementframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.measurementframework.measureprovider.IMeasureProvider;
import org.palladiosimulator.measurementframework.measureprovider.MeasurementListMeasureProvider;
import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.MetricSetDescription;

/**
 * Represents a tuple measurement, i.e., a measurement for a {@see MetricSetDescription}.
 * 
 * @author Sebastian Lehrig
 */
public final class TupleMeasurement extends Measurement {

    /** Delegate to a measure provider holding the represented measure. */
    private final IMeasureProvider measureProvider;

    /** List of subsumed measurements. */
    private final List<Measurement> subsumedMeasurements;

    /**
     * Default constructor.
     * 
     * @param subsumedMeasurements
     *            List of subsumed measurements, needed to construct a measure provider as needed by
     *            the super class.
     * @param metricSetDescription
     *            Metric set description of this measurement.
     * @throws IllegalArgumentException
     *             If number of measures does not equal number of subsumed metrics.
     */
    public TupleMeasurement(final List<Measurement> subsumedMeasurements,
            final MetricSetDescription metricSetDescription) {
        super(metricSetDescription);

        this.subsumedMeasurements = subsumedMeasurements;
        this.measureProvider = new MeasurementListMeasureProvider(subsumedMeasurements);

        if (subsumedMeasurements.size() != metricSetDescription.getSubsumedMetrics().size()) {
            throw new IllegalArgumentException(
                    "Number of measurements has to match the number of child metrics in the metric set description");
        }

        int i = 0;
        for (final MetricDescription subsumedMetric : metricSetDescription.getSubsumedMetrics()) {
            if (!subsumedMeasurements.get(i++).getMetricDesciption().getId().equals(subsumedMetric.getId())) {
                throw new IllegalArgumentException("Subsumed metric \"" + subsumedMetric.getName() + "\" of metric \""
                        + metricSetDescription.getName() + "\" not present in measurement");
            }
        }
    }

    /**
     * Convenience constructor based on subsumed measures.
     * 
     * @param metricDescription
     *            Metric set description of this measurement.
     * @param measures
     *            Subsumed measures.
     */
    public TupleMeasurement(final MetricSetDescription metricDescription, final Measure<?, ?>... measures) {
        this(metricDescription, Arrays.asList(measures));
    }

    /**
     * Convenience constructor based on subsumed measures.
     * 
     * @param metricDescription
     *            Metric set description of this measurement.
     * @param measures
     *            Subsumed measures.
     */
    public TupleMeasurement(final MetricSetDescription metricDescription, final List<Measure<?, ?>> measures) {
        this(computeSubsumedMeasurements(metricDescription, measures), metricDescription);
    }

    /**
     * Computes a list of subsumed measurements based on the given list of measures that conform to
     * the given metric set description.
     * 
     * @param metricDescription
     *            the metric set description measurements have to conform to.
     * @param measures
     *            the given list of measures.
     * @return the computed list of subsumed measurements.
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    private static List<Measurement> computeSubsumedMeasurements(final MetricSetDescription metricDescription,
            final List<Measure<?, ?>> measures) {
        final List<Measurement> subsumedMeasurements = new ArrayList<Measurement>();
        int i = 0;
        for (final Measure<?, ?> measure : measures) {
            final MetricDescription subsumedMetric = metricDescription.getSubsumedMetrics().get(i++);
            if (subsumedMetric instanceof BaseMetricDescription) {
                subsumedMeasurements.add(new BasicMeasurement(measure, (BaseMetricDescription) subsumedMetric));
            } else if (subsumedMetric instanceof MetricSetDescription) {
                subsumedMeasurements.add(new TupleMeasurement((MetricSetDescription) subsumedMetric, measure));
            } else {
                throw new IllegalArgumentException("Unsupported type of Metric Description");
            }
        }
        return subsumedMeasurements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Measure<?, ?>> asList() {
        return this.measureProvider.asList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V, Q extends Quantity> Measure<V, Q> getMeasureForMetric(final MetricDescription wantedMetric) {
        return this.measureProvider.getMeasureForMetric(wantedMetric);
    }

    /**
     * Returns the list of subsumed measurements.
     * 
     * @return the list of subsumed measurements.
     */
    public List<Measurement> getSubsumedMeasurements() {
        return Collections.unmodifiableList(this.subsumedMeasurements);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TupleMeasurement [");
        for (final Measure<?, ?> sub : asList()) {
            sb.append(sub.toString());
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}
