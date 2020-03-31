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

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.phyzicsz.telekinesis.metric.Counter;
import com.phyzicsz.telekinesis.metric.actor.CollectorActor;
import com.phyzicsz.telekinesis.metric.events.CounterCreateEvent;
import com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent;
import com.phyzicsz.telekinesis.metric.events.ExportReplyEvent;
import com.phyzicsz.telekinesis.metric.events.ExportRequestEvent;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterRepositoryTest {

    static final ActorTestKit testKit = ActorTestKit.create();

    public CounterRepositoryTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
        testKit.shutdownTestKit();
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of onCounterCreateEvent method, of class CounterRepository.
     */
    @Test
    public void testOnCounterCreateEvent() {
        System.out.println("onCounterCreateEvent");
        CounterCreateEvent event = CounterCreateEvent.builder()
                .name("MetricCounter1")
                .labelNames("A")
                .help("Metric Help")
                .build();

        CounterRepository repository = new CounterRepository();
        repository.onCounterCreateEvent(event);

        CounterMetricData data = repository.lookup("MetricCounter1", "A");

        CounterMonotonicIncrementEvent monotonicInc = CounterMonotonicIncrementEvent.builder()
                .name("MetricCounter1")
                .labelNames("A")
                .labelValues("a")
                .build();
        repository.onCounterMonotonicIncrementEvent(monotonicInc);

        CounterMetricData dataPlusOne = repository.lookup("MetricCounter1", "A");

        int i = 0;
    }

    @Test
    public void testExport() {
        ActorRef<MetricEvent> collectorReference = testKit.spawn(CollectorActor.create(), "metrics");
        TestProbe<MetricEvent> probe = testKit.createTestProbe();

        //creating a new Counter will generate a new CounterCreationEvent
        Counter other = new Counter.CounterBuilder()
                .withName("MetricCounter1")
                .withHelp("Metric Help")
                .withLabels("MetricLabel1")
                .withCollectorReference(probe.ref())
                .build();
        
         CounterCreateEvent counterCreateEvent = probe.expectMessageClass(CounterCreateEvent.class);

        ExportRequestEvent exportRequest = new ExportRequestEvent()
                .replyTo(probe.ref());

        collectorReference.tell(exportRequest);

        ExportReplyEvent actual = probe.expectMessageClass(ExportReplyEvent.class);
        
        int i = 0;
    }

}
