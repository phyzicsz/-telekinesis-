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


import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.outbrain.swinfra.metrics.MetricCollector;
import com.outbrain.swinfra.metrics.MetricRegistry;
import com.outbrain.swinfra.metrics.exporter.text.TextFormatter;
import com.phyzicsz.telekinesis.metric.actor.CollectorActor;
import com.phyzicsz.telekinesis.metric.events.CounterCreateEvent;
import com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;
import io.prometheus.client.CollectorRegistry;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterTest {

    static final ActorTestKit testKit = ActorTestKit.create();
    
    private static final String NAME = "Counter1";
    private static final String HELP = "Counter1 help";

    public CounterTest() {
    }
    
    @AfterAll
    public static void cleanup() {
        testKit.shutdownTestKit();
    }

    /**
     * Test of inc method, of class Counter.
     */
    @Test
    public void exporeOutbrainsMetric() throws IOException {

        MetricRegistry registry = new MetricRegistry();
        
        final com.outbrain.swinfra.metrics.Counter counter = new com.outbrain.swinfra.metrics.Counter.CounterBuilder(NAME, HELP)
                .withLabels("MetricLabel1")
                .build();
        registry.getOrRegister(counter);

        counter.inc(1, "A");
        counter.inc(2, "B");
        
        final com.outbrain.swinfra.metrics.SettableGauge gauge = new com.outbrain.swinfra.metrics.SettableGauge.SettableGaugeBuilder("Guage1", HELP)
                .withLabels("GaugeLabel")
                .build();
        registry.getOrRegister(gauge);

        gauge.set(20, "Gauge1Value");
        

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
    
    @Test
    public void akkaCollectorTest() throws IOException {
        
        ActorRef<MetricEvent> collectorReference = testKit.spawn(CollectorActor.create(), "metrics");
        TestProbe<MetricEvent> probe = testKit.createTestProbe();
        
        //creating a new Counter will generate a new CounterCreationEvent
        Counter other = new Counter.CounterBuilder()
                .withName(NAME)
                .withHelp(HELP)
                .withLabels("MetricLabel1")
                .withCollectorReference(probe.ref())
                .build();
        
        //should look like this
        MetricEvent event = CounterCreateEvent.builder()
                    .name(NAME)
                    .help(HELP)
                    .labelNames("MetricLabel1")
                    .build();
                
         MetricEvent actual = probe.expectMessage(event);
    }

}
