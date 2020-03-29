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

import com.phyzicsz.telekinesis.metric.events.CounterCreateEvent;
import com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CounterRepositoryTest {
    
    public CounterRepositoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
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
        
        int i  = 0;
    }
    
}
