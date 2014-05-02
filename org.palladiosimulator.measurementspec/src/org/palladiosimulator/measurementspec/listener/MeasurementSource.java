package org.palladiosimulator.measurementspec.listener;

import java.util.Collection;

import org.palladiosimulator.commons.designpatterns.AbstractObservable;
import org.palladiosimulator.commons.designpatterns.IAbstractObservable;
import org.palladiosimulator.measurementspec.Measurement;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.metricentity.MetricEntity;

/**
 * 
 * @author Sebastian Lehrig, Steffen Becker
 */
public abstract class MeasurementSource extends MetricEntity implements IAbstractObservable<IMeasurementSourceListener> {

    private final AbstractObservable<IMeasurementSourceListener> observableDelegate = new AbstractObservable<IMeasurementSourceListener>() {};

    public MeasurementSource(final MetricDescription metricDesciption) {
        super(metricDesciption);
    }

    /**
     * @param observer
     * @see org.palladiosimulator.commons.designpatterns.IAbstractObservable#addObserver(java.lang.Object)
     */
    @Override
    public void addObserver(final IMeasurementSourceListener observer) {
        observableDelegate.addObserver(observer);
    }

    /**
     * @param observer
     * @see org.palladiosimulator.commons.designpatterns.IAbstractObservable#removeObserver(java.lang.Object)
     */
    @Override
    public void removeObserver(final IMeasurementSourceListener observer) {
        observableDelegate.removeObserver(observer);
    }


    public boolean isCompatibleMeasurement(final Measurement measurement) {
        if (!isCompatibleWith(measurement.getMetricDesciption())) {
            return false;
        }
        return true;
    }

    /**
     * @param newMeasurement
     */
    protected void notifyMeasurementSourceListener(final Measurement newMeasurement) {
        if (!isCompatibleMeasurement(newMeasurement)) {
            throw new IllegalArgumentException("Taken measurement has an incompatible metric");
        }
        observableDelegate.getEventDispatcher().newMeasurementAvailable(newMeasurement);
    }

    protected Collection<IMeasurementSourceListener> getMeasurementSourceListeners() {
        return observableDelegate.getObservers();
    }
}
