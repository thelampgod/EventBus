package com.github.thelampgod.eventbus;

public abstract class EventCancelable extends Event {
    private boolean canceled;

    public EventCancelable() {
        this.canceled = false;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
