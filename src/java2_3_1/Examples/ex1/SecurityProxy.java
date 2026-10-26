package java2_3_1.Examples.ex1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SecurityProxy implements InvocationHandler {
    private final Object target;

    private SecurityProxy(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createSecureProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new SecurityProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(TransactionLimit.class)) {
            TransactionLimit limitInfo = method.getAnnotation(TransactionLimit.class);
            double requestedAmount = (double) args[0];

            if (requestedAmount > limitInfo.limit()) {
                System.out.println("SECURITY ALERT: Απορρίφθηκε κλήση στη μέθοδο " + method.getName());
                System.out.println("Αιτιολογία: " + limitInfo.description());
                return null;
            }
        }
        return method.invoke(target, args);
    }
}