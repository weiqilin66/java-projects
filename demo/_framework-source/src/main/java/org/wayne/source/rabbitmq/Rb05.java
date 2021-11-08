package org.wayne.source.rabbitmq;

// @RabbitListener的处理器是RabbitListenerAnnotationBeanPostProcessor
// line15  BeanPostProcessor后置处理器在Bean初始化前后做的操作,刚方法为初始化后
// line17 buildMetadata找出所有@rabbitListener注解的方法循环处理
// line29 把所有注解值设置到endpoint上 最终line83注册endpoint


public class Rb05 {
    /*public class RabbitListenerAnnotationBeanPostProcessor
            implements BeanPostProcessor, Ordered, BeanFactoryAware, BeanClassLoaderAware, EnvironmentAware,
            SmartInitializingSingleton {

        @Override
        public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            final TypeMetadata metadata = this.typeCache.computeIfAbsent(targetClass, this::buildMetadata);
            for (ListenerMethod lm : metadata.listenerMethods) {
                for (RabbitListener rabbitListener : lm.annotations) {
                    processAmqpListener(rabbitListener, lm.method, bean, beanName);
                }
            }
            if (metadata.handlerMethods.length > 0) {
                processMultiMethodListeners(metadata.classAnnotations, metadata.handlerMethods, bean, beanName);
            }
            return bean;
        }

        protected void processListener(MethodRabbitListenerEndpoint endpoint, RabbitListener rabbitListener, Object bean,
			Object target, String beanName) {

		endpoint.setBean(bean);
		endpoint.setMessageHandlerMethodFactory(this.messageHandlerMethodFactory);
		endpoint.setId(getEndpointId(rabbitListener));
		endpoint.setQueueNames(resolveQueues(rabbitListener));
		endpoint.setConcurrency(resolveExpressionAsStringOrInteger(rabbitListener.concurrency(), "concurrency"));
		endpoint.setBeanFactory(this.beanFactory);
		endpoint.setReturnExceptions(resolveExpressionAsBoolean(rabbitListener.returnExceptions()));
		Object errorHandler = resolveExpression(rabbitListener.errorHandler());
		if (errorHandler instanceof RabbitListenerErrorHandler) {
			endpoint.setErrorHandler((RabbitListenerErrorHandler) errorHandler);
		}
		else if (errorHandler instanceof String) {
			String errorHandlerBeanName = (String) errorHandler;
			if (StringUtils.hasText(errorHandlerBeanName)) {
				endpoint.setErrorHandler(this.beanFactory.getBean(errorHandlerBeanName, RabbitListenerErrorHandler.class));
			}
		}
		else {
			throw new IllegalStateException("error handler mut be a bean name or RabbitListenerErrorHandler, not a "
					+ errorHandler.getClass().toString());
		}
		String group = rabbitListener.group();
		if (StringUtils.hasText(group)) {
			Object resolvedGroup = resolveExpression(group);
			if (resolvedGroup instanceof String) {
				endpoint.setGroup((String) resolvedGroup);
			}
		}
		String autoStartup = rabbitListener.autoStartup();
		if (StringUtils.hasText(autoStartup)) {
			endpoint.setAutoStartup(resolveExpressionAsBoolean(autoStartup));
		}

		endpoint.setExclusive(rabbitListener.exclusive());
		String priority = resolve(rabbitListener.priority());
		if (StringUtils.hasText(priority)) {
			try {
				endpoint.setPriority(Integer.valueOf(priority));
			}
			catch (NumberFormatException ex) {
				throw new BeanInitializationException("Invalid priority value for " +
						rabbitListener + " (must be an integer)", ex);
			}
		}

		resolveExecutor(endpoint, rabbitListener, target, beanName);
		resolveAdmin(endpoint, rabbitListener, target);
		resolveAckMode(endpoint, rabbitListener);
		resolvePostProcessor(endpoint, rabbitListener, target, beanName);
		RabbitListenerContainerFactory<?> factory = resolveContainerFactory(rabbitListener, target, beanName);

		this.registrar.registerEndpoint(endpoint, factory);
	}
    }*/
}
