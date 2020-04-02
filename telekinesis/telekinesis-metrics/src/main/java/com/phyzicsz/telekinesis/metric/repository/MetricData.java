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

import com.phyzicsz.telekinesis.metric.MetricType;

/**
 * Holder for the metric data in the metrics repository
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 * @param <T> The parameterized data type given the metric
 */
public class MetricData<T>{

    protected MetricType metricType;
    protected T value;
    protected String name;
    protected String help;
    protected String[] labelNames;
    protected String[] labelValues; 

    public MetricData metricType(final MetricType value) {
        this.metricType = value;
        return this;
    }

    public MetricData value(final T value) {
        this.value = value;
        return this;
    }

    public MetricData name(final String value) {
        this.name = value;
        return this;
    }

    public MetricData help(final String value) {
        this.help = value;
        return this;
    }

    public MetricData labelNames(final String[] value) {
        this.labelNames = value;
        return this;
    }

    public MetricData labelValues(final String[] value) {
        this.labelValues = value;
        return this;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public String[] getLabelNames() {
        return labelNames;
    }

    public String[] getLabelValues() {
        return labelValues;
    }  
}
