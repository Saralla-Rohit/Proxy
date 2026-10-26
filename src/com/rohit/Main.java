package com.rohit;

import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) {

        // 1. Create the REAL object
        PaymentService realService = new RealPaymentService();

        // 2. Wrap it in a handler (the "brain")
        LoggingHandler handler = new LoggingHandler(realService);

        // 3. Ask the JVM to generate a proxy class at runtime
        PaymentService proxy = (PaymentService) Proxy.newProxyInstance(

            // PARAM 1: ClassLoader
            // The classloader that will "load" the generated proxy class.
            // Usually the same one that loaded your interface.
            PaymentService.class.getClassLoader(),

            // PARAM 2: Interfaces to implement
            // The generated class will implement ALL of these.
            // This is why it can only be interfaces (classes can't be "implemented").
            new Class[] { PaymentService.class },

            // PARAM 3: The handler
            // Every method call on the proxy → handler.invoke(proxy, method, args)
            handler
        );

        // 4. Use the proxy — the client doesn't know it's a proxy
        proxy.charge(500);
        proxy.getStatus();

        // 5. Proof it's a generated class
        System.out.println(proxy.getClass().getName());
        // Output: jdk.proxy2.$Proxy0
	}
}
