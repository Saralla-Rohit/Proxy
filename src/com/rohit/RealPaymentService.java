package com.rohit;

public class RealPaymentService implements PaymentService{
	@Override
    public void charge(int amount) {
        // Actual business logic — talks to a bank API, writes to DB, etc.
        System.out.println("Charging $" + amount + " to bank...");
    }

    @Override
    public String getStatus() {
        return "ACTIVE";
    }
}
