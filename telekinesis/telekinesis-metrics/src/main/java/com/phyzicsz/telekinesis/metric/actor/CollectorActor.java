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

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.phyzicsz.telekinesis.metric.events.CounterCreateEvent;
import com.phyzicsz.telekinesis.metric.events.CounterIncrementEvent;
import com.phyzicsz.telekinesis.metric.events.CounterMonotonicIncrementEvent;
import com.phyzicsz.telekinesis.metric.events.MetricEvent;
import com.phyzicsz.telekinesis.metric.repository.CounterRepository;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class CollectorActor extends AbstractBehavior<MetricEvent> {

    private final CounterRepository counterRepository;
    
    private CollectorActor(ActorContext<MetricEvent> context) {
        super(context);
        
        counterRepository = new CounterRepository();
    }

    @Override
    public Receive<MetricEvent> createReceive() {
        return newReceiveBuilder()
                .onMessage(CounterCreateEvent.class, this::onCounterCreateEvent)
                .onMessage(CounterMonotonicIncrementEvent.class, this::onCounterMonotonicIncrementEvent)
                .onMessage(CounterIncrementEvent.class, this::onCounterIncrementEvent)
                .build();
    }

    public static Behavior<MetricEvent> create() {
        return Behaviors.setup(CollectorActor::new);
    }

    private Behavior<MetricEvent> onCounterCreateEvent(CounterCreateEvent event) {
        getContext().getLog().info("onCounterCreateEvent!");
        counterRepository.onCounterCreateEvent(event);
        return Behaviors.same();
    }

    private Behavior<MetricEvent> onCounterMonotonicIncrementEvent(CounterMonotonicIncrementEvent event) {
        getContext().getLog().info("onCounterCounterMonotonicIncrementEvent!");
        counterRepository.onCounterMonotonicIncrementEvent(event);
        return Behaviors.same();
    }
    
    private Behavior<MetricEvent> onCounterIncrementEvent(CounterIncrementEvent event) {
        getContext().getLog().info("onCounterIncrementEvent!");
        counterRepository.onCounterIncrementEvent(event);
        return Behaviors.same();
    }

}
