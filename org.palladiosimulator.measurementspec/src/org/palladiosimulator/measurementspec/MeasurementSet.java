package org.palladiosimulator.measurementspec;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.palladiosimulator.edp2.models.ExperimentData.MetricDescription;
import org.palladiosimulator.edp2.models.ExperimentData.MetricSetDescription;
import org.palladiosimulator.measurementspec.requestcontext.RequestContext;

/**
 * Represents a sample which is taken for a ProbeSet in a {@link RequestContext}
 * .
 * <p>
 * The probe set sample is the result of a probe set measurement. It contains
 * one or more probe samples; one for each probe assigned to the underlying
 * probe set. In other words: The contained probe samples constitute the
 * combined sample for the annotated model element which is named probe set
 * sample.
 * <p>
 * A probe set (notice: not the resulting sample) encapsulates one or more
 * probes whose results are taken for the identical model element which is
 * annotated by the probe set.
 * 
 * @author pmerkle
 * @author Faber
 * @author Sebastian Lehrig
 */
public final class MeasurementSet extends Measurement {

    private final List<Measurement> subsumedMeasurements = new LinkedList<Measurement>();

    /**
     * Class constructor specifying the encapsulated probe samples, the context
     * id, the model element id and the probe set id.
     * 
     * @param probeSamples
     *            the probe samples to be encapsulated within this probe set
     *            sample
     * @param ctxID
     *            the identifier for the context in which the contained probe
     *            samples have been taken
     * @param modelElementID
     *            the id of the model element which is annotated by the
     *            underlying probe set
     * @param probeSetID
     *            the id of the probe set according to the underlying model
     * @see RequestContext
     */
    public MeasurementSet(
            final List<Measurement> subsumedMeasurements, final MetricSetDescription metrics, final MeasurementSource measurementSource) {
        super(
                getRequestContext(subsumedMeasurements),
                metrics,
                measurementSource,
                getModelElementID(subsumedMeasurements));
        this.subsumedMeasurements.addAll(subsumedMeasurements);
        checkValidParameters();
    }

    private void checkValidParameters() {
        final MetricSetDescription metrics = (MetricSetDescription) getMetricDesciption();

        if (this.subsumedMeasurements.size() != metrics.getSubsumedMetrics().size()) {
            throw new IllegalArgumentException("Number of measurements has to match the number of child metrics in the metric set description");
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
    public final List<Measurement> getSubsumedMeasurements () {
        return Collections.unmodifiableList(this.subsumedMeasurements);
    }

    private static RequestContext getRequestContext(final List<Measurement> subsumedMeasurements) {
        if (subsumedMeasurements == null || subsumedMeasurements.size() == 0) {
            throw new IllegalArgumentException("Illegal measurements provided");
        }
        if (subsumedMeasurements.contains(null)) {
            throw new IllegalArgumentException("Measurements are not allowed to include null values as subsumed measurements");
        }

        final RequestContext result = subsumedMeasurements.get(0).getRequestContext();
        for (final Measurement childMeasurment : subsumedMeasurements) {
            if (childMeasurment.getRequestContext() != result) {
                throw new RuntimeException("All measurments have to be taken in the same context");
            }
        }
        return result;
    }

    private static String getModelElementID(final List<Measurement> subsumedMeasurements) {
        final String result = subsumedMeasurements.get(0).getModelElementID();
        for (final Measurement childMeasurment : subsumedMeasurements) {
            if (childMeasurment.getModelElementID() != result) {
                throw new RuntimeException("All measurments have to be taken from the same model element");
            }
        }
        return result;
    }

    @Override
    public Measurement getMeasurementForMetric(final MetricDescription wantedMetric) {
        if (this.getMetricDesciption().getUuid().equals(wantedMetric.getUuid())) {
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
}
