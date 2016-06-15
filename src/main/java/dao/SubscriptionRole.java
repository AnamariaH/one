package dao;

public class SubscriptionRole {

    private int id;
    private Subscription subscription;
    private Role role;

    public SubscriptionRole() {
    }

    public SubscriptionRole(Subscription subscription, Role role) {
        this.subscription=subscription;
        this.role = role;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
