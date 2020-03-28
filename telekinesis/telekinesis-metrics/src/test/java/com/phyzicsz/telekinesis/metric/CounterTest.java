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

import com.outbrain.swinfra.metrics.Counter.CounterBuilder;
import com.outbrain.swinfra.metrics.MetricCollector;
import com.outbrain.swinfra.metrics.MetricRegistry;
import com.outbrain.swinfra.metrics.exporter.text.TextFormatter;
import io.prometheus.client.CollectorRegistry;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterTest {

    private static final String NAME = "Counter1";
    private static final String HELP = "Counter1 help";

    public CounterTest() {
    }

    /**
     * Test of inc method, of class Counter.
     */
    @Test
    public void exporeOutbrainsMetric() throws IOException {

        MetricRegistry registry = new MetricRegistry();
        
        final com.outbrain.swinfra.metrics.Counter other = new com.outbrain.swinfra.metrics.Counter.CounterBuilder(NAME, HELP)
                .withLabels("MetricLabel1")
                .build();
        
        registry.getOrRegister(other);

        other.inc(1, "A");
        other.inc(2, "B");

        Long aValue = other.getValue("A");
        Long bValue = other.getValue("B");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        MetricCollector collector = new MetricCollector(registry);
        TextFormatter formatter = new TextFormatter(collector);
        formatter.exportTo(output);

        String metricString = output.toString();

        assert (true);

    }
    
    @Test
    public void explorePrometheusClient() throws IOException {

        CollectorRegistry registry = new CollectorRegistry();
        io.prometheus.client.Counter other = io.prometheus.client.Counter.build()
                .name(NAME)
                .help(HELP)
                .labelNames("MetricLabel1","MetricLabel2")
                .register(registry);
        
        other.labels("A","B").inc(1);
        
        StringWriter writer = new StringWriter();
        io.prometheus.client.exporter.common.TextFormat.write004(writer, registry.metricFamilySamples());
        
        String metricString = writer.toString();

        assert (true);

    }

}
