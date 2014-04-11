package org.palladiosimulator.measurementspec;

import javax.measure.Measure;
import javax.measure.quantity.Quantity;

import org.palladiosimulator.edp2.metricentity.MetricEntity;
import org.palladiosimulator.edp2.models.ExperimentData.BaseMetricDescription;
import org.palladiosimulator.edp2.models.ExperimentData.MetricDescription;
import org.palladiosimulator.measurementspec.requestcontext.MeasurementSourceAndRequestContext;
import org.palladiosimulator.measurementspec.requestcontext.RequestContext;

public abstract class Measurement extends MetricEntity {

    private final MeasurementSourceAndRequestContext measurementSourceAndRequestContext;

    /** The id of the annotated model element */
    private final String modelElementID;


    /**
     * @param measuredMetric
     * @param measuredProbe
     * @param modelElementID
     */
    protected Measurement(final RequestContext requestContext, final MetricDescription measuredMetric, final MeasurementSource measurementSource, final String modelElementID) {
        super(measuredMetric);
        this.measurementSourceAndRequestContext = new MeasurementSourceAndRequestContext(measurementSource, requestContext);
        this.modelElementID = modelElementID;
    }

    /**
     * @return the measuredProbe
     */
    public final MetricEntity getMeasurementSource() {
        return measurementSourceAndRequestContext.getMeasurementSource();
    }

    /**
     * Returns the id of the model element which is annotated by the underlying
     * probe set.
     * 
     * @return the model element id
     */
    public final String getModelElementID() {
        return modelElementID;
    }

    public RequestContext getRequestContext() {
        return this.measurementSourceAndRequestContext.getRequestContext();
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

}
