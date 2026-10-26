package com.rohit;

public interface PaymentService {
	void charge(int amount);
    String getStatus();
}
