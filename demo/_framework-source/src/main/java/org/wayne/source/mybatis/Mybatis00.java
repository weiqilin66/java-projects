package org.wayne.source.mybatis;


// line29 SqlSession作为MapperProxy构造方法的参数
public class Mybatis00 {
    /*
    public class MapperProxyFactory<T> {

        private final Class<T> mapperInterface;
        private final Map<Method, MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

        public MapperProxyFactory(Class<T> mapperInterface) {
            this.mapperInterface = mapperInterface;
        }

        public Class<T> getMapperInterface() {
            return mapperInterface;
        }

        public Map<Method, MapperMethodInvoker> getMethodCache() {
            return methodCache;
        }

        @SuppressWarnings("unchecked")
        protected T newInstance(MapperProxy<T> mapperProxy) {
            return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
        }

        public T newInstance(SqlSession sqlSession) {
            final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
            return newInstance(mapperProxy);
        }

    }*/

}
