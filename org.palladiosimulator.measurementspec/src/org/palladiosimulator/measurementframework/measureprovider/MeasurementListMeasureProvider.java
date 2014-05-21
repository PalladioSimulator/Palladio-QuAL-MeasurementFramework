package org.palladiosimulator.measurementframework.measureprovider;

import java.util.ArrayList;
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

    private final List<Measurement> measurements;

    public MeasurementListMeasureProvider(final List<Measurement> measurements) {
        super();
        this.measurements = measurements;
    }

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

    @Override
    public List<Measure<?, ?>> asList() {
        final ArrayList<Measure<?, ?>> result = new ArrayList<Measure<?, ?>>();
        for (final Measurement m : this.measurements) {
            result.addAll(m.asList());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
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
}
