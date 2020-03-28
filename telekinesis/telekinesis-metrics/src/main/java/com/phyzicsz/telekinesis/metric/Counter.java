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

import static com.phyzicsz.telekinesis.metric.MetricType.COUNTER;
import java.util.concurrent.atomic.LongAdder;


/**
 * An implementation of a Counter metric. A counter is a whole number that can only increase its value.
 * 
 * The counter exposes a single time-series with its value and labels.
 *
 * @see <a href="https://prometheus.io/docs/concepts/metric_types/#counter">Prometheus counter metric</a>
 * 
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class Counter extends AbstractMetric<LongAdder> {

  private Counter(final String name, final String help, final String[] labelNames) {
    super(name, help, labelNames);
  }

  public void inc(final String... labelValues) {
    inc(1, labelValues);
  }

  public void inc(final long n, final String... labelValues) {
    
  }

  public long getValue(final String... labelValues) {
    return 0;
  }

  @Override
  LongAdder createMetric() {
    return new LongAdder();
  }

  @Override
  public MetricType getType() {
    return COUNTER;
  }


  public static class CounterBuilder extends AbstractMetricBuilder<Counter, CounterBuilder> {

    public CounterBuilder(final String name, final String help) {
      super(name, help);
    }

    @Override
    protected Counter create(final String fullName, final String help, final String[] labelNames) {
      return new Counter(fullName, help, labelNames);
    }
  }

}
