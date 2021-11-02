package org.wayne.source.mybatis;

// line57 invoke()先判断代理对象是否是类 equals.Object 否者执行cacheMapperMethod将sqlSession做为参数执行初始化





public class Mybatis01 {


   /*
   class MapperProxy<T> implements InvocationHandler, Serializable {

        private static final long serialVersionUID = -4724728412955527868L;
        private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;
        private static final Constructor<MethodHandles.Lookup> lookupConstructor;
        private static final Method privateLookupInMethod;
        private final SqlSession sqlSession;
        private final Class<T> mapperInterface;
        private final Map<Method, MapperMethodInvoker> methodCache;

        public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethodInvoker> methodCache) {
            this.sqlSession = sqlSession;
            this.mapperInterface = mapperInterface;
            this.methodCache = methodCache;
        }

        static {
            Method privateLookupIn;
            try {
                privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
            } catch (NoSuchMethodException e) {
                privateLookupIn = null;
            }
            privateLookupInMethod = privateLookupIn;

            Constructor<MethodHandles.Lookup> lookup = null;
            if (privateLookupInMethod == null) {
                // JDK 1.8
                try {
                    lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                    lookup.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException(
                            "There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.",
                            e);
                } catch (Exception e) {
                    lookup = null;
                }
            }
            lookupConstructor = lookup;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                } else {
                    return cachedInvoker(method).invoke(proxy, method, args, sqlSession);
                }
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        }

        private MapperMethodInvoker cachedInvoker(Method method) throws Throwable {
            try {
                // A workaround for https://bugs.openjdk.java.net/browse/JDK-8161372
                // It should be removed once the fix is backported to Java 8 or
                // MyBatis drops Java 8 support. See gh-1929
                MapperMethodInvoker invoker = methodCache.get(method);
                if (invoker != null) {
                    return invoker;
                }

                return methodCache.computeIfAbsent(method, m -> {
                    if (m.isDefault()) {
                        try {
                            if (privateLookupInMethod == null) {
                                return new DefaultMethodInvoker(getMethodHandleJava8(method));
                            } else {
                                return new DefaultMethodInvoker(getMethodHandleJava9(method));
                            }
                        } catch (IllegalAccessException | InstantiationException | InvocationTargetException
                                | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return new PlainMethodInvoker(new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
                    }
                });
            } catch (RuntimeException re) {
                Throwable cause = re.getCause();
                throw cause == null ? re : cause;
            }
        }

        private MethodHandle getMethodHandleJava9(Method method)
                throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            final Class<?> declaringClass = method.getDeclaringClass();
            return ((MethodHandles.Lookup) privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup())).findSpecial(
                    declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()),
                    declaringClass);
        }

        private MethodHandle getMethodHandleJava8(Method method)
                throws IllegalAccessException, InstantiationException, InvocationTargetException {
            final Class<?> declaringClass = method.getDeclaringClass();
            return lookupConstructor.newInstance(declaringClass, ALLOWED_MODES).unreflectSpecial(method, declaringClass);
        }

        interface MapperMethodInvoker {
            Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable;
        }

        private static class PlainMethodInvoker implements MapperMethodInvoker {
            private final MapperMethod mapperMethod;

            public PlainMethodInvoker(MapperMethod mapperMethod) {
                super();
                this.mapperMethod = mapperMethod;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
                return mapperMethod.execute(sqlSession, args);
            }
        }

        private static class DefaultMethodInvoker implements MapperMethodInvoker {
            private final MethodHandle methodHandle;

            public DefaultMethodInvoker(MethodHandle methodHandle) {
                super();
                this.methodHandle = methodHandle;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
                return methodHandle.bindTo(proxy).invokeWithArguments(args);
            }
        }
    }*/

}
