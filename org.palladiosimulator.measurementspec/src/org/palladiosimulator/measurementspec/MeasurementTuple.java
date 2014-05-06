package org.palladiosimulator.measurementspec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.measure.Measure;

import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.MetricSetDescription;

/**
 * Represents a sample which is taken for a probe list in a {@link RequestContext} .
 * <p>
 * The probe list sample is the result of a probe list measurement. It contains one or more probe
 * samples; one for each probe assigned to the underlying probe list. In other words: The contained
 * probe samples constitute the combined sample for the annotated model element which is named probe
 * list sample.
 * <p>
 * A probe list (notice: not the resulting sample) encapsulates one or more probes whose results are
 * taken for the identical model element which is annotated by the probe list.
 * 
 * TODO Update outdated JavaDoc.
 * 
 * @author pmerkle, Faber, Sebastian Lehrig
 */
public final class MeasurementTuple extends Measurement {

    private final List<Measurement> subsumedMeasurements;

    /**
     * Class constructor specifying the encapsulated probe samples, the context id, the model
     * element id and the probe list id.
     * 
     * @param probeSamples
     *            the probe samples to be encapsulated within this probe set sample
     * @param ctxID
     *            the identifier for the context in which the contained probe samples have been
     *            taken
     * @param modelElementID
     *            the id of the model element which is annotated by the underlying probe set
     * @param probeSetID
     *            the id of the probe set according to the underlying model
     * @see RequestContext
     */
    public MeasurementTuple(final List<Measurement> subsumedMeasurements, final MetricSetDescription metrics) {
        super(metrics);
        this.subsumedMeasurements = Collections.unmodifiableList(subsumedMeasurements);
        checkValidParameters();
    }

    public MeasurementTuple(final MetricSetDescription metric, final Measure... measures) {
        this(metric, Arrays.asList(measures));
    }

    public MeasurementTuple(final MetricSetDescription metric, final List<Measure> measures) {
        super(metric);
        final ArrayList<Measurement> measurements = new ArrayList<Measurement>();
        int i = 0;
        for (final Measure measure : measures) {
            measurements.add(new BasicMeasurement(measure, metric.getSubsumedMetrics().get(i++)));
        }
        this.subsumedMeasurements = Collections.unmodifiableList(measurements);
        checkValidParameters();
    }

    private void checkValidParameters() {
        final MetricSetDescription metrics = (MetricSetDescription) getMetricDesciption();

        if (this.subsumedMeasurements.size() != metrics.getSubsumedMetrics().size()) {
            throw new IllegalArgumentException(
                    "Number of measurements has to match the number of child metrics in the metric set description");
        }
    }

    /**
     * Returns the encapsulated probe samples satisfying the specified rule set.
     * 
     * @param matchingRule
     *            the rule set
     * @return
     * @see BasicMeasurement
     */
    public final List<Measurement> getSubsumedMeasurements() {
        return this.subsumedMeasurements;
    }

    @Override
    public Measurement getMeasurementForMetric(final MetricDescription wantedMetric) {
        if (this.getMetricDesciption().getId().equals(wantedMetric.getId())) {
            return this;
        }

        for (final Measurement childMeasurement : subsumedMeasurements) {
            final Measurement childResult = childMeasurement.getMeasurementForMetric(wantedMetric);
            if (childResult != null) {
                return childResult;
            }
        }
        return null;
    }

    @Override
    public List<Measure<?, ?>> asList() {
        final ArrayList<Measure<?, ?>> result = new ArrayList<Measure<?, ?>>();
        for (final Measurement m : subsumedMeasurements) {
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
