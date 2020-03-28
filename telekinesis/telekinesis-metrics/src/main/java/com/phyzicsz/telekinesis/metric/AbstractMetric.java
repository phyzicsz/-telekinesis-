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


import com.phyzicsz.telekinesis.metrics.utils.NameUtils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for all the metrics.
 * 
 * @param <T> the type of the wrapped metric
 * 
 * @author phyzicsz <phyzics.z@gmail.com>
 */
abstract class AbstractMetric<T> implements Metric {

  private final String name;
  private final String help;
  private final List<String> labelNames;

  AbstractMetric(final String name,
                 final String help,
                 final String[] labelNames) {
    this.name = name;
    this.help = help;
    this.labelNames = Arrays.asList(labelNames);
  }

  T createMetric() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getHelp() {
    return help;
  }

  @Override
  public List<String> getLabelNames() {
    return labelNames;
  }
}
