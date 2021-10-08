//package org.wayne.common.base.demo;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.PropertyFilter;
//import com.alibaba.fastjson.serializer.SerializerFeature;
////import com.ccbc.common.api.annotation.DataValidated;
//import com.ccbc.common.dao.IBaseDao;
//import com.ccbc.common.entity.CcbcTranLog;
//import com.ccbc.common.event.IPublishEvent;
//import com.ccbc.common.exception.CcbcException;
//import com.ccbc.common.exception.ErrorCode;
//import com.ccbc.common.jedis.JedisCcbcUtil;
//import com.ccbc.common.lock.ICcbcLock;
//import com.ccbc.common.service.exception.ErrorCodeUtils;
//import com.ccbc.common.utils.SpringUtils;
//import com.ccbc.context.*;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.util.ClassUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import redis.clients.jedis.JedisCluster;
//
//import javax.annotation.Resource;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.*;
//
///**
// * 范例
// * 1封装事务
// * 2封装属性校验
// * 3封装请求入口
// * @param <Request>
// * @param <Response>
// */
//public abstract class AbstractCcbcService<Request extends CcbcRequest, Response extends CcbcResponse> implements ICcbcService<Request, Response>, ICcbcInnerService<Request, Response> {
//    private static Logger log = LoggerFactory.getLogger(AbstractCcbcService.class);
//    private static PropertyFilter serializeFilter = new PropertyFilter() {
//        public boolean apply(Object source, String name, Object value) {
//            if ("password".equals(name)) {
//                return false;
//            } else if ("loginPwd".equals(name)) {
//                return false;
//            } else if ("signPwd".equals(name)) {
//                return false;
//            } else if ("afterPwd".equals(name)) {
//                return false;
//            } else if ("beforPwd".equals(name)) {
//                return false;
//            } else if ("oldLoginPwd".equals(name)) {
//                return false;
//            } else if ("newLoginPwd".equals(name)) {
//                return false;
//            } else {
//                return !"payPwd".equals(name);
//            }
//        }
//    };
//    @Resource
//    private IBaseDao<CcbcTranLog> dao;
//    @Resource
//    private JpaTransactionManager transactionManager;
//    public static final int IDEMPOTENT_DEFALUT_PERIOD = 3;
//    public static final String IDEMPOTENT_FLOW_PREFIX = "IDEMPOTENT_FLOW_";
//
//    public AbstractCcbcService() {
//    }
//
//    @Operation(
//            value = "使用JSON调用CCBC同步微服务",
//            tags = {"CCBC sync with pojo"},
//            notes = "JSON串必须要与对应的接口的请求类POJO完全匹配"
//    )
//    @RequestMapping(
//            value = {"/execute"},
//            method = {RequestMethod.POST},
//            produces = {"application/json;charset=UTF-8"}
//    )
//    public Response execute(@Parameter(name = "req",description = "JSON字符串的请求报文",required = true) @RequestBody Request req) {
//        Response resp = null;
//        CcbcTranLog ccbcTranLog = null;
//
//        try {
//            //幂等
//            this.checkIdempotent(req);
//        } catch (CcbcException var15) {
//            log.error("checkIdempotent error", var15);
//            // 同一封装返回bean
//            resp = this.exceptionHandler(var15);
//            return resp;
//        }
//
//        try {
//            log.info("receive request");
//            // 数据校验 非空必输等
//            this.checkDataValid(req);
//        } catch (CcbcException var14) {
//            log.error("checkDataValid error", var14);
//            resp = this.exceptionHandler(var14);
//            return resp;
//        }
//
//        try {
//            ccbcTranLog = this.saveLog(req);
//            resp = this.executeTransaction(req);
//        } catch (CcbcException var11) {
//            log.error("doExecute error:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}", var11);
//            resp = this.exceptionHandler(var11);
//        } catch (Exception var12) {
//            log.error("doExecute error:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}", var12);
//            resp = this.exceptionHandler(var12);
//        } finally {
//            this.updateLog(ccbcTranLog, req, resp);
//        }
//
//        return resp;
//    }
//
//    protected Type getParameterizedType(String absClassName) {
//        log.debug("myself class: " + this.getClass());
//        Class<?> myself = this.getClass();
//        Type type = null;
//
//        while(true) {
//            String clsName = myself.getSimpleName();
//            if (clsName.equals(absClassName) || "java.lang.Object".equals(myself.getName())) {
//                return type;
//            }
//
//            type = myself.getGenericSuperclass();
//            myself = myself.getSuperclass();
//        }
//    }
//
//    public String executeWithJson(String reqJson) {
//        Type mytype = this.getParameterizedType("AbstractCcbcService");
//        Class<Request> cls = (Class)((ParameterizedType)mytype).getActualTypeArguments()[0];
//        log.info("receive JSON request, Request type[" + cls.getName() + "], json string: " + reqJson);
//        Request req = null;
//        req = (CcbcRequest)JSON.parseObject(reqJson, cls);
//        Response resp = this.execute(req);
//        String respJson = JSON.toJSONString(resp, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
//        log.info("return response withJson, Response type[" + resp.getClass().getName() + "] ");
//        return respJson;
//    }
//
//    private Response executeTransaction(Request req) throws CcbcException {
//        Response resp = null;
//        TransactionStatus status = null;
//        boolean isOpenTran = this.isOpenTran(req);
//        if (isOpenTran) {
//            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//            def.setPropagationBehavior(0);
//            status = this.transactionManager.getTransaction(def);
//            log.info("begin Transaction:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}");
//        }
//
//        boolean var16 = false;
//
//        try {
//            var16 = true;
//            resp = this.executeEvent(req);
//            if (resp == null) {
//                throw new CcbcException(ErrorCodeUtils.formatErrorCode("CCBCB0202", new String[0]));
//            }
//
//            if (StringUtils.isBlank(resp.getErrorCode())) {
//                resp.setErrorCode("CCBC00000");
//            }
//
//            if (isOpenTran) {
//                if ("CCBC00000".equals(resp.getErrorCode())) {
//                    this.transactionManager.commit(status);
//                    log.info("commit Transaction:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}");
//                    var16 = false;
//                } else {
//                    var16 = false;
//                }
//            } else {
//                var16 = false;
//            }
//        } catch (Exception var19) {
//            throw var19;
//        } finally {
//            if (var16) {
//                if ((resp == null || !"CCBC00000".equals(resp.getErrorCode())) && isOpenTran) {
//                    log.error("rollback:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}");
//                    this.transactionManager.rollback(status);
//                }
//
//                ICcbcLock lock = (ICcbcLock)SpringUtils.getBean(ICcbcLock.class);
//                boolean hasLock = lock.hasLock();
//                if (hasLock) {
//                    try {
//                        lock.releaseAllLock();
//                        log.info("【解锁成功】" + this.toJSONString(req));
//                    } catch (Exception var17) {
//                        log.error("【解锁失败】" + this.toJSONString(req));
//                    }
//                }
//
//            }
//        }
//
//        if ((resp == null || !"CCBC00000".equals(resp.getErrorCode())) && isOpenTran) {
//            log.error("rollback:{flowNo:" + req.getFlowNo() + ",tradeTime" + req.getTradeTime() + "}");
//            this.transactionManager.rollback(status);
//        }
//
//        ICcbcLock lock = (ICcbcLock)SpringUtils.getBean(ICcbcLock.class);
//        boolean hasLock = lock.hasLock();
//        if (hasLock) {
//            try {
//                lock.releaseAllLock();
//                log.info("【解锁成功】" + this.toJSONString(req));
//            } catch (Exception var18) {
//                log.error("【解锁失败】" + this.toJSONString(req));
//            }
//        }
//
//        return resp;
//    }
//
//    private Response executeEvent(Request req) throws CcbcException {
//        Response resp = null;
//        IPublishEvent publishEvent = null;
//        boolean isEventReq = this.isEventReq(req);
//
//        try {
//            if (isEventReq) {
//                publishEvent = (IPublishEvent)SpringUtils.getBean(IPublishEvent.class);
//            }
//
//            resp = this.innerExecute(req);
//            if (isEventReq) {
//                publishEvent.publish((IEventRequest)req);
//            }
//        } catch (CcbcException var9) {
//            throw var9;
//        } finally {
//            if (isEventReq) {
//                publishEvent.clear();
//            }
//
//        }
//
//        return resp;
//    }
//
//    /**
//     * 线程锁
//     * @param req
//     * @return
//     * @throws CcbcException
//     */
//    public Response innerExecute(Request req) throws CcbcException {
//        CcbcResponse resp = null;
//
//        try {
//            log.info("innerExecute:" + this.toJSONString(this.getClass()));
//            ICcbcLock lock = (ICcbcLock)SpringUtils.getBean(ICcbcLock.class);
//            boolean islockReq = this.isLockReq(req);
//            boolean isLock = false;
//            String lockId = null;
//            if (islockReq) {
//                lockId = ((ILockRequest)req).getLockId();
//                isLock = lock.lock(lockId);
//                if (!isLock) {
//                    log.error("加锁失败：" + this.toJSONString(req));
//                    throw new CcbcException(ErrorCodeUtils.formatErrorCode("CCBCB0210", new String[]{"【加锁失败】"}));
//                }
//
//                log.info("加锁成功：" + this.toJSONString(req));
//            }
//
//            ContextThreadLocal.setCaptialSide(req.getCapitalSide());
//            ContextThreadLocal.setReq(req);
//            resp = this.doExecute(req);
//            return resp;
//        } catch (CcbcException var7) {
//            throw var7;
//        }
//    }
//
//    protected abstract Response doExecute(Request var1) throws CcbcException;
//
//    protected boolean checkDataValid(CcbcRequest request) throws CcbcException {
//        StringBuilder errorMsg = new StringBuilder();
//        if (request == null) {
//            errorMsg.append("【请求对象：CCBCRequest】");
//        } else {
//            if (StringUtils.isBlank(request.getFlowNo())) {
//                errorMsg.append("流水号(FlowNo),");
//            }
//
//            try {
//                request.setServiceId((String)this.getClass().getField("serviceId").get(this.getClass()));
//            } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException var7) {
//                request.setServiceId("SERVICE ID FAILED");
//            }
//
//            if (StringUtils.isBlank(request.getServiceId())) {
//                errorMsg.append("服务码(ServiceId),");
//            }
//
//            if (StringUtils.isBlank(request.getTradeTime())) {
//                errorMsg.append("交易时间(TradeTime),");
//            }
//
//            if (StringUtils.isBlank(request.getSysFlag())) {
//                errorMsg.append("渠道标识(sysFlag),");
//            }
//
//            if (StringUtils.isBlank(request.getAssetSide())) {
//                errorMsg.append("资产标识(assetSide),");
//            }
//
//            if (StringUtils.isBlank(request.getCapitalSide())) {
//                errorMsg.append("资金标识(capitalSide),");
//            }
//
//            try {
//                isNull(request, errorMsg);
//            } catch (Exception var6) {
//                log.error("错误", var6);
//            }
//
//            if (errorMsg.length() > 0) {
//                errorMsg.deleteCharAt(errorMsg.length() - 1);
//            }
//
//            if (!StringUtils.isEmpty(errorMsg)) {
//                errorMsg.insert(0, "【请求信息：");
//                errorMsg.append("】属性不能为空");
//            }
//
//            validate(request, errorMsg);
//        }
//
//        if (!StringUtils.isEmpty(errorMsg)) {
//            throw new CcbcException(new ErrorCode("CCBCB0201", errorMsg.toString()));
//        } else {
//            String serviceId = null;
//
//            try {
//                serviceId = (String)this.getClass().getField("serviceId").get(this.getClass());
//            } catch (Exception var5) {
//            }
//
//            if (!StringUtils.isEmpty(serviceId) && !serviceId.equals(request.getServiceId())) {
//                throw new CcbcException(ErrorCodeUtils.formatErrorCode("CCBCB0206", new String[]{"【请求信息：服务码(ServiceId)】"}));
//            } else {
//                return true;
//            }
//        }
//    }
//
//    /**
//     * 封装幂等性校验
//     * @param req
//     * @return
//     * @throws CcbcException
//     */
//    private boolean checkIdempotent(CcbcRequest req) throws CcbcException {
//        boolean need = this.isIdempotentReq(req);
//        if (need) {
//            IAvoidDupliCommit ide = (IAvoidDupliCommit)req;
//            String idempotentKey = ide.getUniqueIdentify();
//            if (StringUtils.isBlank(idempotentKey)) {
//                log.warn(String.format("无法获取幂等性key,req:%s", JSONObject.toJSONString(req)));
//                return true;
//            } else if ("need_not_avoid".equals(idempotentKey)) {
//                log.info("不需要去重");
//                return true;
//            } else {
//                int expireTime = ide.getExpireTime();
//                if (expireTime <= 0) {
//                    expireTime = 3;
//                }
//                // 加盐
//                idempotentKey = "avoidDuplication" + idempotentKey;
//                JedisCluster cluster = JedisCcbcUtil.getJc();
//                String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
//                String key = "IDEMPOTENT_FLOW_" + date;
//                String uuid = JedisCcbcUtil.getSeqByKeyWithPrefix(key, 40);
//                long change = cluster.setnx(idempotentKey, uuid);
//                if (change > 0L) {
//                    cluster.expire(idempotentKey, expireTime);
//                } else {
//                    String theUuid = cluster.get(idempotentKey);
//                    if (!uuid.equals(theUuid)) {
//                        String errorMsg = String.format("重复交易校验未通过，%ds内有多次请求,req:%s", expireTime, JSONObject.toJSONString(req));
//                        log.error(errorMsg);
//                        throw new CcbcException(new ErrorCode("CCBCB0215", errorMsg));
//                    }
//
//                    cluster.expire(idempotentKey, expireTime);
//                }
//
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }
//
//    private Response exceptionHandler(Throwable e) {
//        ErrorCode errorCode = null;
//        if (e instanceof CcbcException) {
//            String ec = ((CcbcException)e).getErrCode();
//            if (StringUtils.isBlank(ec)) {
//                errorCode = ErrorCodeUtils.formatErrorCode("CCBCB0500", new String[]{e.getMessage()});
//            } else {
//                errorCode = new ErrorCode(ec, e.getMessage());
//            }
//        } else {
//            errorCode = ErrorCodeUtils.formatErrorCode("CCBCB0500", new String[]{e.getClass().getName()});
//        }
//
//        CcbcResponse resp = null;
//
//        try {
//            resp = this.createResponseObject(this);
//            resp.setErrorCode(errorCode);
//        } catch (Exception var5) {
//            log.error("初始化返回对象发生异常", var5);
//        }
//
//        return resp;
//    }
//
//    /**
//     * 反射返回新对象
//     * @param o
//     * @return
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     */
//    protected Response createResponseObject(Object o) throws InstantiationException, IllegalAccessException {
//        Type mytype = this.getParameterizedType("AbstractCcbcService");
//        Class<Response> response = (Class)((ParameterizedType)mytype).getActualTypeArguments()[1];
//        return (CcbcResponse)response.newInstance();
//    }
//
//    private CcbcTranLog saveLog(CcbcRequest req) {
//        try {
//            log.info("requestObject:" + this.toJSONString(req));
//            CcbcTranLog ccbcTranLog = new CcbcTranLog();
//            ccbcTranLog.setFlowNo(req.getFlowNo());
//            ccbcTranLog.setAssetSide(req.getAssetSide());
//            ccbcTranLog.setCapitalSide(req.getCapitalSide());
//            ccbcTranLog.setServiceId(req.getServiceId());
//            ccbcTranLog.setSysFlag(req.getSysFlag());
//            ccbcTranLog.setTradeTime(req.getTradeTime());
//            ccbcTranLog.setCreateTime(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//            ccbcTranLog.setLastModifyTime(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//            ccbcTranLog.setRequestMsg(this.toJSONString(req));
//            return (CcbcTranLog)this.dao.merge(ccbcTranLog);
//        } catch (Exception var3) {
//            log.error("saveLog error", var3);
//            return null;
//        }
//    }
//
//    private void updateLog(CcbcTranLog ccbcTranLog, CcbcRequest req, Response resp) {
//        try {
//            log.info("responseObject:{flowNo:" + req.getFlowNo() + ",tradeTime:" + req.getTradeTime() + ",serviceid:" + req.getServiceId() + "}," + this.toJSONString(resp));
//            if (ccbcTranLog != null) {
//                ccbcTranLog.setResponseMsg(this.toJSONString(resp));
//                ccbcTranLog.setErrorCode(resp.getErrorCode());
//                ccbcTranLog.setErrorMsg(resp.getErrorMsg());
//                ccbcTranLog.setLastModifyTime(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//                this.dao.merge(ccbcTranLog);
//            }
//        } catch (Exception var5) {
//            log.error("updateLog error", var5);
//        }
//
//    }
//
//    protected boolean isOpenTran(Request req) {
//        Class<?> cls = this.getClass();
//        Method m = null;
//        Annotation a = null;
//
//        do {
//            try {
//                m = cls.getDeclaredMethod("doExecute", req.getClass());
//                a = m.getAnnotation(Transactional.class);
//            } catch (SecurityException | NoSuchMethodException var8) {
//                try {
//                    m = cls.getDeclaredMethod("doExecute", CcbcRequest.class);
//                    a = m.getAnnotation(Transactional.class);
//                } catch (SecurityException | NoSuchMethodException var7) {
//                }
//            }
//
//            cls = cls.getSuperclass();
//        } while(m == null && cls != AbstractCcbcService.class);
//
//        if (a == null) {
//            log.info("notOpenTran  ...");
//        }
//
//        return a != null;
//    }
//
//    private String toJSONString(Object obj) {
//        return JSON.toJSONString(obj, serializeFilter, new SerializerFeature[0]);
//    }
//
//    private boolean isEventReq(CcbcRequest req) {
//        return ClassUtils.getAllInterfacesForClassAsSet(req.getClass()).contains(IEventRequest.class);
//    }
//
//    private boolean isLockReq(CcbcRequest req) {
//        return ClassUtils.getAllInterfacesForClassAsSet(req.getClass()).contains(ILockRequest.class);
//    }
//
//    private boolean isIdempotentReq(CcbcRequest req) {
//        return ClassUtils.getAllInterfacesForClassAsSet(req.getClass()).contains(IAvoidDupliCommit.class);
//    }
//
//    public static StringBuilder isNull(Object bean, StringBuilder errorMsg) throws IllegalArgumentException, IllegalAccessException {
//        Field[] fs = bean.getClass().getDeclaredFields();
//        Object val = null;
//        Field[] var4 = fs;
//        int var5 = fs.length;
//
//        for(int var6 = 0; var6 < var5; ++var6) {
//            Field f = var4[var6];
//            DataValidated validated = (DataValidated)f.getAnnotation(DataValidated.class);
//            if (validated != null) {
//                if (validated.isNotEmpty()) {
//                    f.setAccessible(true);
//                    val = f.get(bean);
//                    if (val == null) {
//                        errorMsg.append("" + f.getName() + ",");
//                    } else {
//                        try {
//                            Method m = val.getClass().getMethod("isEmpty");
//                            if (m == null) {
//                                StringBuilder errorMsg2 = new StringBuilder();
//                                isNull(val, errorMsg2);
//                                if (errorMsg2.length() > 0) {
//                                    errorMsg2.deleteCharAt(errorMsg2.length() - 1);
//                                    errorMsg.append(f.getName()).append("[").append(errorMsg2).append("],");
//                                }
//                            } else {
//                                boolean val2 = (Boolean)m.invoke(val);
//                                if (val2) {
//                                    errorMsg.append("" + f.getName() + ",");
//                                } else {
//                                    Collection<?> cols = null;
//                                    if (val instanceof Map) {
//                                        cols = ((Map)val).values();
//                                    }
//
//                                    Object cols;
//                                    if (val.getClass().isArray()) {
//                                        cols = Arrays.asList(val);
//                                    } else {
//                                        if (!(val instanceof Collection)) {
//                                            continue;
//                                        }
//
//                                        cols = (Collection)val;
//                                    }
//
//                                    Iterator iter = ((Collection)cols).iterator();
//
//                                    while(iter.hasNext()) {
//                                        StringBuilder errorMsg2 = new StringBuilder();
//                                        isNull(iter.next(), errorMsg2);
//                                        if (errorMsg2.length() > 0) {
//                                            errorMsg2.deleteCharAt(errorMsg2.length() - 1);
//                                            errorMsg.append(f.getName()).append("[").append(errorMsg2).append("],");
//                                        }
//                                    }
//                                }
//                            }
//                        } catch (Exception var14) {
//                            log.error("错误", var14);
//                        }
//                    }
//                } else if (validated.notNull() || validated.value()) {
//                    f.setAccessible(true);
//                    val = f.get(bean);
//                    if (val == null) {
//                        errorMsg.append("" + f.getName() + ",");
//                    } else {
//                        StringBuilder errorMsg2 = new StringBuilder();
//                        isNull(val, errorMsg2);
//                        if (errorMsg2.length() > 0) {
//                            errorMsg2.deleteCharAt(errorMsg2.length() - 1);
//                            errorMsg.append(f.getName()).append("[").append(errorMsg2).append("],");
//                        }
//                    }
//                }
//            }
//        }
//
//        return errorMsg;
//    }
//
//    public static StringBuilder validate(Object bean, StringBuilder errorMsg) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<Object>> violations = validator.validate(bean, new Class[0]);
//        Iterator iter = violations.iterator();
//
//        while(iter.hasNext()) {
//            errorMsg.append(((ConstraintViolation)iter.next()).getMessage());
//            errorMsg.append(",");
//        }
//
//        return errorMsg;
//    }
//}
