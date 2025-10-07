/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.subscription;

import java.util.HashMap;
import java.util.Map;


public class SubscriptionPlans {
    private static final Map<Integer, SubscriptionPlan> PLANS = new HashMap<>();

    static {
        PLANS.put(1, new SubscriptionPlan(1, "free", 0.00, 0));
        PLANS.put(2, new SubscriptionPlan(2, "gold", 4.99, 30));
        PLANS.put(3, new SubscriptionPlan(3, "platinum", 9.99, 30));
        PLANS.put(4, new SubscriptionPlan(4, "diamond", 19.99, 30));
        PLANS.put(5, new SubscriptionPlan(5, "trial", 0.00, 7));
    }

    public static SubscriptionPlan getById(int id) {
        return PLANS.get(id);
    }
}
