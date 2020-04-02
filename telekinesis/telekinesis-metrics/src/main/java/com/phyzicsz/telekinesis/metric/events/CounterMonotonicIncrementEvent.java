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
package com.phyzicsz.telekinesis.metric.events;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterMonotonicIncrementEvent implements MetricEvent {

    private final  String name;
    private final  String[] labelNames;
    private final  String[] labelValues;
    
    public String name() {
        return name;
    }

    public String[] labelNames() {
        return labelNames;
    }
    
     public String[] labelValues() {
        return labelValues;
    }

    public static class Builder {

        private String name;
        private String[] labelNames;
        private String[] labelValues;

        private Builder() {
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder labelNames(final String... value) {
            this.labelNames = value;
            return this;
        }
        
        public Builder labelValues(final String... value) {
            this.labelValues = value;
            return this;
        }

        public CounterMonotonicIncrementEvent build() {
            return new com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent(name, labelNames,labelValues);
        }
    }

    public static CounterMonotonicIncrementEvent.Builder builder() {
        return new CounterMonotonicIncrementEvent.Builder();
    }

    private CounterMonotonicIncrementEvent(final String name, final String[] labelNames,final String[] labelValues) {
        this.name = name;
        this.labelNames = labelNames;
        this.labelValues = labelValues;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Arrays.deepHashCode(this.labelNames);
        hash = 47 * hash + Arrays.deepHashCode(this.labelValues);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CounterMonotonicIncrementEvent other = (CounterMonotonicIncrementEvent) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.labelNames, other.labelNames)) {
            return false;
        }
        if (!Arrays.deepEquals(this.labelValues, other.labelValues)) {
            return false;
        }
        return true;
    }

   
}
