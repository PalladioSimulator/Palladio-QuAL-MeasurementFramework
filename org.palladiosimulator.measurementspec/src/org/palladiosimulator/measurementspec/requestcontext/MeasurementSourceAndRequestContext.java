package org.palladiosimulator.measurementspec.requestcontext;

import org.palladiosimulator.edp2.metricentity.MetricEntity;
import org.palladiosimulator.measurementspec.MeasurementSet;
import org.palladiosimulator.measurementspec.MeasurementSource;

/**
 * Represents a (ProbeSet, {@link RequestContext})-pair. The ProbeSet is
 * identified by its ID.
 * <p>
 * It is used to uniquely identify {@link MeasurementSet}.
 * 
 * @author Faber
 * @author Philipp Merkle
 * 
 */
public final class MeasurementSourceAndRequestContext {

    // the ID representing the ProbeSet
    private final MeasurementSource measurementSource;

    private final RequestContext requestContext;

    /**
     * Default constructor. Constructs a pair consisting of a ProbeSet and a
     * {@link RequestContext}.
     * 
     * @param probeSetId
     *            the ID representing the ProbeSet
     * @param requestContext
     *            the RequestContext
     */
    public MeasurementSourceAndRequestContext(final MeasurementSource measuredProbe,
            final RequestContext requestContext) {
        this.measurementSource = measuredProbe;
        this.requestContext = requestContext;
    }

    /**
     * Returns the ID of the ProbeSet component.
     * 
     * @return The ID representing the ProbeSet
     */
    public final MetricEntity getMeasurementSource() {
        return measurementSource;
    }

    /**
     * Returns the {@link RequestContext} component.
     * 
     * @return the context
     */
    public final RequestContext getRequestContext() {
        return requestContext;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((requestContext == null) ? 0 : requestContext.hashCode());
        result = prime * result
                + ((measurementSource == null) ? 0 : measurementSource.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MeasurementSourceAndRequestContext)) {
            return false;
        }
        final MeasurementSourceAndRequestContext other = (MeasurementSourceAndRequestContext) obj;
        if (requestContext == null) {
            if (other.requestContext != null) {
                return false;
            }
        } else if (!requestContext.equals(other.requestContext)) {
            return false;
        }
        if (measurementSource == null) {
            if (other.measurementSource != null) {
                return false;
            }
        } else if (!measurementSource.equals(other.measurementSource)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return measurementSource + "-" + requestContext.toString();
    }

}
