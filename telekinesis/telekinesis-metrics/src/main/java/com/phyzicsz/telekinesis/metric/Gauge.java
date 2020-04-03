package com.phyzicsz.telekinesis.metric;

import akka.actor.typed.ActorRef;
import com.phyzicsz.telekinesis.metric.Gauge.SettableDoubleSupplier;
import com.phyzicsz.telekinesis.metric.events.GaugeCreateEvent;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;

import java.util.function.DoubleSupplier;

/**
 * An implementation of a Gauge metric. A gauge is a decimal value that can
 * increase or decrease.
 * <p>
 * The gauge exposes a single time-series with its value and labels.
 * </p>
 * <p>
 * The SettableGauge is meant to be set from the outside as opposed to the
 * regular {@link Gauge}. This is an important distinction because consecutive
 * calls to the method <i>set</i> will not all show when sampling this metric,
 * rather the last value set will show.
 * </p>
 *
 * @see
 * <a href="https://prometheus.io/docs/concepts/metric_types/#gauge">Prometheus
 * gauge metric</a>
 */
public class Gauge extends AbstractMetric<SettableDoubleSupplier> {

    private Gauge(final String name,
            final String help,
            final String[] labelNames,
            final ActorRef<MetricEvent> collectorReference) {
        super(name, help, labelNames, collectorReference);
    }
    
    public double getValue(final String... labelValues) {
        return 0.0;
    }

    @Override
    SettableDoubleSupplier createMetric() {
        return new SettableDoubleSupplier();
    }

    @Override
    public MetricType getType() {
        return MetricType.GAUGE;
    }

    public void set(final double value, final String... labelValues) {

    }

    public static class GaugeBuilder extends AbstractMetricBuilder<Gauge, Gauge.GaugeBuilder> {
        @Override
        protected Gauge create(String fullName, String help, String[] labelNames, ActorRef<MetricEvent> collectorReference) {
            return new Gauge(fullName, help, labelNames, collectorReference);
        }

        @Override
        protected MetricEvent onNewEvent(String fullName, String help, String[] labelNames) {
            MetricEvent event = GaugeCreateEvent.builder()
                    .name(fullName)
                    .help(help)
                    .labelNames(labelNames)
                    .build();
            return event;
        }
    }

    static class SettableDoubleSupplier implements DoubleSupplier {

        private volatile double value = 0;

        void set(final double value) {
            this.value = value;
        }

        @Override
        public double getAsDouble() {
            return value;
        }
    }

}
