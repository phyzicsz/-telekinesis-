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

import akka.actor.typed.ActorRef;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;
import com.phyzicsz.telekinesis.metrics.utils.NameUtils;
import com.phyzicsz.telekinesis.metrics.utils.StringUtils;
import com.phyzicsz.telekinesis.metrics.utils.Validate;


/**
 * Abstract Builder class for the metrics
 * 
 * @param <T> Metric type
 * @param <B> Builder for metric type
 * 
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public abstract class AbstractMetricBuilder<T extends AbstractMetric, B extends AbstractMetricBuilder<T, B>> {

    private String name;
    private String help;

    private String namespace = "";
    private String subsystem = "";
    String[] labelNames = new String[]{};
    private ActorRef<MetricEvent> collectorReference;

    AbstractMetricBuilder() {

    }
    
    public B withName(final String name){
        this.name = name;
        return getThis();
    }
    
    public B withHelp(final String help){
        this.help = help;
        return getThis();
    }

    public B withSubsystem(final String subsystem) {
        this.subsystem = subsystem;
        return getThis();
    }

    public B withNamespace(final String namespace) {
        this.namespace = namespace;
        return getThis();
    }

    public B withLabels(final String... labelNames) {
        this.labelNames = labelNames;
        return getThis();
    }
    
    public B withCollectorReference(final ActorRef<MetricEvent> collectorReference) {
        this.collectorReference = collectorReference;
        return getThis();
    }

    protected abstract T create(final String fullName, 
            final String help, 
            final String[] labelNames,
            final ActorRef<MetricEvent> collectorReference);
    
    protected abstract MetricEvent onNewEvent(final String fullName, 
            final String help, 
            final String[] labelNames);

    public T build() {
        validateParams();
        String fullName = createFullName();
        final T metric = create(fullName, help, labelNames,collectorReference);
        MetricEvent event = onNewEvent(fullName, help, labelNames);
        collectorReference.tell(event);
        return metric;
    }

    void validateParams() {
        //checkNotNull(collectorReference);
        Validate.notBlank(name, "The metric's name must be set");
        Validate.notBlank(help, "The metric's help must be set");
        NameUtils.validateMetricName(name);
        NameUtils.validateLabelNames(labelNames);
    }

    private String createFullName() {
        final StringBuilder sb = new StringBuilder(name);
        if (StringUtils.isNotBlank(subsystem)) {
            sb.insert(0, subsystem + "_");
        }
        if (StringUtils.isNotBlank(namespace)) {
            sb.insert(0, namespace + "_");
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    protected B getThis() {
        return (B) this;
    }
}