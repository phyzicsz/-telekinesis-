/*
 * Copyright 2020 phyzicsz <phyzics.z@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phyzicsz.telekinesis.metric;

import akka.actor.typed.ActorRef;
import com.phyzicsz.telekinesis.metric.Gauge.SettableDoubleSupplier;
import com.phyzicsz.telekinesis.metric.events.GaugeCreateEvent;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;

import java.util.function.DoubleSupplier;

/**
 * An implementation of a Gauge metric. A gauge is a decimal value that can
 * increase or decrease.
 * 
 * The gauge exposes a single time-series with its value and labels.
 *
 * @see
 * <a href="https://prometheus.io/docs/concepts/metric_types/#gauge">Prometheus
 * gauge metric</a>
 * 
 * @author phyzicsz <phyzics.z@gmail.com>
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
