package org.palladiosimulator.measurementframework.measureprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.measure.Measure;

import org.palladiosimulator.measurementframework.Measurement;
import org.palladiosimulator.metricspec.MetricDescription;

/**
 * A measurement list measure provider uses a list of measurements to determine measures.
 * 
 * @author Sebastian Lehrig
 */
public class MeasurementListMeasureProvider extends AbstractMeasureProvider {

    /** The given list of measurement. */
    private final List<Measurement> measurements;

    /**
     * Default constructor. Initializes this measure provider using a given list of measurements.
     * Each such measurement comes with measures to be provided.
     * 
     * @param measurements
     *            the given list of measurements.
     */
    public MeasurementListMeasureProvider(final List<Measurement> measurements) {
        super();
        this.measurements = measurements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Measurement getMeasurementForMetric(final MetricDescription wantedMetric) {
        for (final Measurement subsubmedMeasurement : this.measurements) {
            final Measurement subsubmedMeasure = subsubmedMeasurement.getMeasurementForMetric(wantedMetric);
            if (subsubmedMeasure != null) {
                return subsubmedMeasure;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Measure<?, ?>> asList() {
        final ArrayList<Measure<?, ?>> result = new ArrayList<Measure<?, ?>>();
        for (final Measurement m : this.measurements) {
            result.addAll(m.asList());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DataTuple [");
        for (final Measure<?, ?> m : asList()) {
            sb.append(m.toString() + " ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the list of subsumed measurements managed by this class.
     * 
     * @return the list of subsumed measurements.
     */
    public final List<Measurement> getSubsumedMeasurements() {
        return Collections.unmodifiableList(this.measurements);
    }
}
