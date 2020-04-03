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
package com.phyzicsz.telekinesis.metric.actor;

import com.phyzicsz.telekinesis.metric.repository.MetricData;
import java.io.IOException;
import java.util.concurrent.atomic.LongAdder;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class PrometheusExpositionWriter {

    public void header(final StringBuilder sb, final MetricData metric) {
        sb.append("# HELP ")
                .append(metric.getName())
                .append(" ")
                .append(escapeHelp(metric.getHelp()))
                .append("\n")
                .append("# TYPE ")
                .append(metric.getName())
                .append(" ")
                .append(metric.getMetricType().getName())
                .append("\n");
    }

    private static String escapeHelp(final String help) {
        return help.replace("\\", "\\\\").replace("\n", "\\n");
    }

    public void consumeCounter(final StringBuilder sb, final MetricData<LongAdder> metric,
            final String[] labelValues,
            final double value) {
        appendSample(sb, metric.getName(), value, metric.getLabelNames(), labelValues);
    }

    private void appendSample(final StringBuilder sb,
            final String name,
            final double value,
            final String[] labelNames,
            final String[] labelValues) {
        appendSample(sb, name, null, value, labelNames, labelValues, null, null);
    }

    private void appendSample(final StringBuilder sb,
            final String name,
            final String nameSuffix,
            final double value,
            final String[] labelNames,
            final String[] labelValues) {
        appendSample(sb, name, nameSuffix, value, labelNames, labelValues, null, null);
    }

    private void appendSample(final StringBuilder sb,
            final String name,
            final double value,
            final String[] labelNames,
            final String[] labelValues,
            final String sampleLevelLabelName,
            final String sampleLevelLabelValue) {
        appendSample(sb, name, null, value, labelNames, labelValues, sampleLevelLabelName, sampleLevelLabelValue);
    }

    private void appendSample(final StringBuilder sb,
            final String name,
            final String nameSuffix,
            final double value,
            final String[] labelNames,
            final String[] labelValues,
            final String sampleLevelLabelName,
            final String sampleLevelLabelValue) {
        try {
            sb.append(name);
            if (nameSuffix != null) {
                sb.append(nameSuffix);
            }
            appendLabels(sb, labelNames, labelValues, sampleLevelLabelName, sampleLevelLabelValue);
            sb.append(" ").append(doubleToString(value)).append("\n");
        } catch (final IOException e) {
            throw new RuntimeException("failed appending to output stream");
        }
    }

    private void appendLabels(final StringBuilder sb, 
            final String[] labelNames,
            final String[] labelValues,
            final String sampleLevelLabelName,
            final String sampleLevelLabelValue) throws IOException {
        if (containsLabels(labelNames, sampleLevelLabelName)) {
            sb.append("{");

//            for (final Map.Entry<String, String> entry : staticLabels.entrySet()) {
//                appendLabel(stream, entry.getKey(), entry.getValue());
//            }
            for (int i = 0; i < labelNames.length; ++i) {
                appendLabel(sb, labelNames[i], labelValues[i]);
            }
            if (sampleLevelLabelName != null) {
                appendLabel(sb, sampleLevelLabelName, sampleLevelLabelValue);
            }
            sb.append("}");
        }
    }

    private void appendLabel(final StringBuilder sb, 
            final String name, 
            final String value) throws IOException {
        sb.append(name).append("=\"").append(escapeLabelValue(value)).append("\",");
    }

    private boolean containsLabels(final String[] labelNames, final String sampleLevelLabelName) {
        return !(labelNames.length == 0) || sampleLevelLabelName != null;
    }

    private static String escapeLabelValue(final String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
    
     /**
         * Convert a double to it's string representation
         */
        private static String doubleToString(final double value) {
        if (value == Double.POSITIVE_INFINITY) {
            return "+Inf";
        }
        if (value == Double.NEGATIVE_INFINITY) {
            return "-Inf";
        }
        if (Double.isNaN(value)) {
            return "NaN";
        }
        return Double.toString(value);
    }

}
