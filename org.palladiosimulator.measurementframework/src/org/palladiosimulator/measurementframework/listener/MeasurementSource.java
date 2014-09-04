package org.palladiosimulator.measurementframework.listener;

import java.util.Collection;

import org.palladiosimulator.commons.designpatterns.AbstractObservable;
import org.palladiosimulator.commons.designpatterns.IAbstractObservable;
import org.palladiosimulator.measurementframework.Measurement;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.metricentity.IMetricEntity;
import org.palladiosimulator.metricspec.metricentity.MetricEntity;

/**
 * AbstractMeasureProvider sources provide measurements from analyzers, e.g., by providing the
 * measurements from probes. To provide such measurements, they implement the observer pattern.
 * Observers have to implement the {@link IMeasurementSourceListener} interface to get informed
 * about new measurements.
 * 
 * @author Sebastian Lehrig, Steffen Becker
 */
public abstract class MeasurementSource extends MetricEntity implements IAbstractObservable<IMeasurementSourceListener> {

    /** Delegator object for handling observers. */
    private final AbstractObservable<IMeasurementSourceListener> observableDelegate;

    /**
     * Default constructor. Nothing special.
     * 
     * @param metricDesciption
     *            Metric description as needed by the superclass.
     */
    public MeasurementSource(final MetricDescription metricDesciption) {
        super(metricDesciption);

        this.observableDelegate = new AbstractObservable<IMeasurementSourceListener>() {
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final IMeasurementSourceListener observer) {
        observableDelegate.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IMeasurementSourceListener observer) {
        observableDelegate.removeObserver(observer);
    }

    /**
     * Getter for the list of registered observers (aka. measurement source listeners).
     * 
     * @return The list of observers.
     */
    protected final Collection<IMeasurementSourceListener> getMeasurementSourceListeners() {
        return observableDelegate.getObservers();
    }

    /**
     * Triggers the call-back method for observers with the given, new measurement.
     * 
     * @param newMeasurement
     *            The new measurement observers are informed about.
     */
    protected final void notifyMeasurementSourceListener(final Measurement newMeasurement) {
        if (!isCompatibleMeasurement(newMeasurement)) {
            throw new IllegalArgumentException("Taken measurement has an incompatible metric");
        }
        observableDelegate.getEventDispatcher().newMeasurementAvailable(newMeasurement);
    }

    /**
     * Checks whether a measurement source is compatible with a given measurement, i.e., whether
     * their metric descriptions are equal.
     * 
     * @param measurement
     *            The measurement to be checked.
     * @return <code>true</code> if measurement source and measurement are compatible,
     *         <code>false</code> otherwise.
     */
    private boolean isCompatibleMeasurement(final IMetricEntity measurement) {
        if (!isCompatibleWith(measurement.getMetricDesciption())) {
            return false;
        }
        return true;
    }
}
