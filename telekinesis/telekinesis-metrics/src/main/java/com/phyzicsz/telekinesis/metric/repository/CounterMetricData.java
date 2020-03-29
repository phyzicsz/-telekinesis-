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

import java.util.concurrent.atomic.LongAdder;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterMetricData {

    private String name;
    private String help;
    private String[] labelNames;
    private String[] labelValues;
    private LongAdder counter;

    public CounterMetricData name(final String value) {
        this.name = value;
        return this;
    }

    public CounterMetricData help(final String value) {
        this.help = value;
        return this;
    }

    public CounterMetricData labelNames(final String[] value) {
        this.labelNames = value;
        return this;
    }

    public CounterMetricData labelValues(final String[] value) {
        this.labelValues = value;
        return this;
    }

    public CounterMetricData counter(final LongAdder value) {
        this.counter = value;
        return this;
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

    public LongAdder getCounter() {
        return counter;
    }
    
    
}
