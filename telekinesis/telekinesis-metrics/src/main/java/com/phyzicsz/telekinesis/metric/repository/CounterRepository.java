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
package com.phyzicsz.telekinesis.metric.repository;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.phyzicsz.telekinesis.metric.MetricType;
import com.phyzicsz.telekinesis.metric.actor.PrometheusExpositionWriter;
import com.phyzicsz.telekinesis.metric.events.CounterCreateEvent;
import com.phyzicsz.telekinesis.metric.events.CounterIncrementEvent;
import com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent;
import com.phyzicsz.telekinesis.metrics.utils.NameUtils;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterRepository.class);

    private final Table<String, StringsKey, MetricData<LongAdder>> counterRepository;

    public CounterRepository() {
        counterRepository = HashBasedTable.create();
    }

    public void onCounterCreateEvent(CounterCreateEvent event) {
        StringsKey key = new StringsKey(event.labels());

        MetricData<LongAdder> data = new MetricData<LongAdder>()
                .name(event.name())
                .help(event.help())
                .labelNames(event.labels())
                .metricType(MetricType.COUNTER)
                .value(null);

        counterRepository.put(event.name(), key, data);
    }

    public void onCounterMonotonicIncrementEvent(CounterMonotonicIncrementEvent event) {
        StringsKey key = new StringsKey(event.labelNames());

        MetricData<LongAdder> data = counterRepository.get(event.name(), key);
        if (data == null) {
            LOGGER.error("Metric not found in CounterRepository: name={}", event.name());
            return;
        }

        if (event.labelNames() != null) {
            NameUtils.validateLabelsCount(event.name(), event.labelNames(), event.labelValues());
            NameUtils.validateLabelValuesContainText(event.labelValues());
        }

        if (data.getValue() == null) {
            data.labelValues(event.labelValues());
            data.value(new LongAdder());
            data.getValue().increment();
        } else {
            data.getValue().increment();
        }

        counterRepository.put(event.name(), key, data);
    }

    public void onCounterIncrementEvent(CounterIncrementEvent event) {
        StringsKey key = new StringsKey(event.labelNames());

        MetricData<LongAdder> data = counterRepository.get(event.name(), key);
        if (data == null) {
            LOGGER.error("Metric not found in CounterRepository: name={}", event.name());
            return;
        }

        if (event.labelNames() != null) {
            NameUtils.validateLabelsCount(event.name(), event.labelNames(), event.labelValues());
            NameUtils.validateLabelValuesContainText(event.labelValues());
        }

        if (data.getValue() == null) {
            data.labelValues(event.labelValues());
            data.value(new LongAdder());
            data.getValue().add(event.inc());
        } else {
            data.getValue().add(event.inc());
        }

        counterRepository.put(event.name(), key, data);
    }

    public MetricData<LongAdder> lookup(String name, String... labels) {
        StringsKey key = new StringsKey(labels);
        MetricData<LongAdder> metricData = counterRepository.get(name, key);
        return metricData;
    }

    public MetricData<LongAdder> lookup(String name) {
        MetricData<LongAdder> metricData = lookup(name, (String[]) null);
        return metricData;
    }

    public void export(StringBuilder sb) {
        PrometheusExpositionWriter exporter = new PrometheusExpositionWriter();

        Set<String> keys = counterRepository.rowKeySet();

        for (String key : keys) {
            Map<StringsKey, MetricData<LongAdder>> map = counterRepository.row(key);
            if (map.isEmpty()) {
                continue;
            }

            //grab the first record to write the header...
            Map.Entry<StringsKey, MetricData<LongAdder>> entry = map.entrySet().iterator().next();
            exporter.header(sb, entry.getValue());

            for (Map.Entry<StringsKey, MetricData<LongAdder>> rows : map.entrySet()) {
                StringsKey labelKey = rows.getKey();
                MetricData<LongAdder> metric = rows.getValue();
                exporter.consumeCounter(sb, metric, metric.labelValues, metric.value.doubleValue());
            }

        }
    }

}
