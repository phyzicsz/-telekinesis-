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

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Holder for the metric data in the metrics repository
 * @author phyzicsz <phyzics.z@gmail.com>
 * @param <T> The parameterized data type given the metric
 */
public class MetricData<T> {
  private final T metric;
  private final List<String> labelValues;

  public MetricData(final T metric) {
    this(metric, null);
  }

  public MetricData(final T metric, final List<String> labelValues) {
    this.metric = metric;
    this.labelValues = labelValues == null ? emptyList() : labelValues;
  }

  public T getMetric() {
    return metric;
  }

  public List<String> getLabelValues() {
    return labelValues;
  }
}
