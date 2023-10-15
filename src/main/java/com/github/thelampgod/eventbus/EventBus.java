package com.github.thelampgod.eventbus;

import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Set;

public class EventBus {
    private final Set<Object> subscribers = Sets.newConcurrentHashSet();
    private final Logger logger = LogManager.getLogger("EventBus");

    public EventBus() {
        logger.info("EventBus loaded.");
    }

    public void post(Event event) {
        for (Object subscriber : subscribers) {
            final Class<?> clazz = subscriber.getClass();
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Subscriber.class)) {
                    if (m.getParameterCount() != 1) continue;
                    if (!(m.getParameterTypes()[0].equals(event.getClass()))) continue;

                    try {
                        m.invoke(subscriber, event);
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public void register(Object obj) {
        this.subscribers.add(obj);
    }

    public void unregister(Object obj) {
        this.subscribers.remove(obj);
    }

}
