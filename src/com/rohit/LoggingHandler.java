package com.rohit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingHandler implements InvocationHandler{

    // ─── THE TARGET ─────────────────────────────────────────────
    // This is the ONE specific instance the proxy will delegate to.
    // It's set ONCE at construction and never changes.
    private final PaymentService target;

    public LoggingHandler(PaymentService target) {
        this.target = target;  // captures the specific instance
    }

    // ─── THE INTERCEPTION POINT ─────────────────────────────────
    // EVERY method call on the proxy lands here.
    // No matter which method the client calls (charge, getStatus,
    // toString, hashCode...), this is the ONLY entry point.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        // `proxy`  → the proxy object itself (the $Proxy0 instance)
        // `method` → a Method object representing WHICH method was called
        // `args`   → the arguments the caller passed (null if no args)

        // ── BEFORE: add your cross-cutting logic here ──
        System.out.println("[BEFORE] " + method.getName()
                + " called with args: " + Arrays.toString(args));

        long start = System.nanoTime();

        // ── DELEGATE: call the REAL method on the REAL object ──
        // This is the line that "delegates to RealPaymentService".
        // `method` is a reflection handle to, say, `charge(int)`.
        // `target` is the specific RealPaymentService instance.
        // This is just: target.charge(amount) — but done via reflection
        // so the SAME handler works for ALL methods without knowing
        // their names at compile time.
        Object result = method.invoke(target, args);

        long elapsed = System.nanoTime() - start;

        // ── AFTER: more cross-cutting logic ──
        System.out.println("[AFTER]  " + method.getName()
                + " returned: " + result
                + " in " + elapsed + " ns");

        // ── RETURN: whatever you return here is what the caller sees
        //    as the method's return value.
        return result;
    }

}
