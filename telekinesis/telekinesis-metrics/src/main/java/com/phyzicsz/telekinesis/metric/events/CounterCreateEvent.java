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
public class CounterCreateEvent implements MetricEvent {

    private final  String name;
    private final  String help;
    private final  String[] labelNames;

    public String name() {
        return name;
    }

    public String help() {
        return help;
    }

    public String[] names() {
        return labelNames;
    }

    public static class Builder {

        private String name;
        private String help;
        private String[] labelNames;

        private Builder() {
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder help(final String value) {
            this.help = value;
            return this;
        }

        public Builder labelNames(final String... value) {
            this.labelNames = value;
            return this;
        }

        public CounterCreateEvent build() {
            return new com.phyzicsz.telekinesis.metric.events.CounterCreateEvent(name, help, labelNames);
        }
    }

    public static CounterCreateEvent.Builder builder() {
        return new CounterCreateEvent.Builder();
    }

    private CounterCreateEvent(final String name, final String help, final String[] labelNames) {
        this.name = name;
        this.help = help;
        this.labelNames = labelNames;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.help);
        hash = 89 * hash + Arrays.deepHashCode(this.labelNames);
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
        final CounterCreateEvent other = (CounterCreateEvent) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.help, other.help)) {
            return false;
        }
        if (!Arrays.deepEquals(this.labelNames, other.labelNames)) {
            return false;
        }
        return true;
    }
    
    

    
}
