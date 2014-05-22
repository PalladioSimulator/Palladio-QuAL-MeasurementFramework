package org.palladiosimulator.measurementframework;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param metricDescription
     *            Metric set description of this measurement.
     * @throws IllegalArgumentException
     *             If number of measures does not equal number of subsumed metrics.
     */
    public TupleMeasurement(final List<Measurement> subsumedMeasurements, final MetricSetDescription metricDescription) {
        super(metricDescription);

        this.subsumedMeasurements = subsumedMeasurements;
        this.measureProvider = new MeasurementListMeasureProvider(subsumedMeasurements);

        final MetricSetDescription metrics = (MetricSetDescription) getMetricDesciption();
        if (subsumedMeasurements.size() != metrics.getSubsumedMetrics().size()) {
            throw new IllegalArgumentException(
                    "Number of measurements has to match the number of child metrics in the metric set description");
        }
    }

    public TupleMeasurement(final MetricSetDescription metricDescription, final Measure<?, ?>... measures) {
        this(metricDescription, Arrays.asList(measures));
    }

    public TupleMeasurement(final MetricSetDescription metricDescription, final List<Measure<?, ?>> measures) {
        this(computeSubsumedMeasures(metricDescription, measures), metricDescription);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static List<Measurement> computeSubsumedMeasures(final MetricSetDescription metricDescription,
            final List<Measure<?, ?>> measures) {
        final List<Measurement> subsumedMeasurements = new ArrayList<Measurement>();
        int i = 0;
        for (final Measure<?, ?> measure : measures) {
            MetricDescription subsumedMetric = metricDescription.getSubsumedMetrics().get(i++);
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
    public <V, Q extends Quantity> Measure<V, Q> getMeasureForMetric(MetricDescription wantedMetric) {
        return this.measureProvider.getMeasureForMetric(wantedMetric);
    }

    public List<Measurement> getSubsumedMeasurements() {
        return this.subsumedMeasurements;
    }
}