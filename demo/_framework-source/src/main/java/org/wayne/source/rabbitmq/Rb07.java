package org.wayne.source.rabbitmq;

// line58
// line73 initializeContainer 做特有属性赋值

// 此类总结就是
// 1把MesssageListener set到MessageListenerContainer中
// 2创建一个适配器MessagingMessageListenerAdapter能反射到监听的方法,即@RabbitListener注解的方法

public class Rb07 {
   /*
   public abstract class AbstractRabbitListenerContainerFactory<C extends AbstractMessageListenerContainer>
		implements RabbitListenerContainerFactory<C>, ApplicationContextAware, ApplicationEventPublisherAware {

	@Override
	public C createListenerContainer(RabbitListenerEndpoint endpoint) {
		C instance = createContainerInstance();

		JavaUtils javaUtils =
				JavaUtils.INSTANCE
						.acceptIfNotNull(this.connectionFactory, instance::setConnectionFactory)
						.acceptIfNotNull(this.errorHandler, instance::setErrorHandler);
		if (this.messageConverter != null && endpoint != null) {
			endpoint.setMessageConverter(this.messageConverter);
		}
		javaUtils
			.acceptIfNotNull(this.acknowledgeMode, instance::setAcknowledgeMode)
			.acceptIfNotNull(this.channelTransacted, instance::setChannelTransacted)
			.acceptIfNotNull(this.applicationContext, instance::setApplicationContext)
			.acceptIfNotNull(this.taskExecutor, instance::setTaskExecutor)
			.acceptIfNotNull(this.transactionManager, instance::setTransactionManager)
			.acceptIfNotNull(this.prefetchCount, instance::setPrefetchCount)
			.acceptIfNotNull(this.defaultRequeueRejected, instance::setDefaultRequeueRejected)
			.acceptIfNotNull(this.adviceChain, instance::setAdviceChain)
			.acceptIfNotNull(this.recoveryBackOff, instance::setRecoveryBackOff)
			.acceptIfNotNull(this.mismatchedQueuesFatal, instance::setMismatchedQueuesFatal)
			.acceptIfNotNull(this.missingQueuesFatal, instance::setMissingQueuesFatal)
			.acceptIfNotNull(this.consumerTagStrategy, instance::setConsumerTagStrategy)
			.acceptIfNotNull(this.idleEventInterval, instance::setIdleEventInterval)
			.acceptIfNotNull(this.failedDeclarationRetryInterval, instance::setFailedDeclarationRetryInterval)
			.acceptIfNotNull(this.applicationEventPublisher, instance::setApplicationEventPublisher)
			.acceptIfNotNull(this.autoStartup, instance::setAutoStartup)
			.acceptIfNotNull(this.phase, instance::setPhase)
			.acceptIfNotNull(this.afterReceivePostProcessors, instance::setAfterReceivePostProcessors)
			.acceptIfNotNull(this.deBatchingEnabled, instance::setDeBatchingEnabled);
		if (this.batchListener && this.deBatchingEnabled == null) {
			// turn off container debatching by default for batch listeners
			instance.setDeBatchingEnabled(false);
		}
		if (endpoint != null) { // endpoint settings overriding default factory settings
			javaUtils
				.acceptIfNotNull(endpoint.getAutoStartup(), instance::setAutoStartup)
				.acceptIfNotNull(endpoint.getTaskExecutor(), instance::setTaskExecutor)
				.acceptIfNotNull(endpoint.getAckMode(), instance::setAcknowledgeMode);
			javaUtils
				.acceptIfNotNull(this.batchingStrategy, endpoint::setBatchingStrategy);
			instance.setListenerId(endpoint.getId());
			endpoint.setBatchListener(this.batchListener);
			endpoint.setupListenerContainer(instance);
		}
		if (instance.getMessageListener() instanceof AbstractAdaptableMessageListener) {
			AbstractAdaptableMessageListener messageListener = (AbstractAdaptableMessageListener) instance
					.getMessageListener();
			javaUtils
					.acceptIfNotNull(this.beforeSendReplyPostProcessors,
							messageListener::setBeforeSendReplyPostProcessors)
					.acceptIfNotNull(this.retryTemplate, messageListener::setRetryTemplate)
					.acceptIfCondition(this.retryTemplate != null && this.recoveryCallback != null,
							this.recoveryCallback, messageListener::setRecoveryCallback)
					.acceptIfNotNull(this.defaultRequeueRejected, messageListener::setDefaultRequeueRejected)
					.acceptIfNotNull(endpoint.getReplyPostProcessor(), messageListener::setReplyPostProcessor);
		}
		initializeContainer(instance, endpoint);

		if (this.containerCustomizer != null) {
			this.containerCustomizer.configure(instance);
		}

		return instance;
	}

	*/
}
