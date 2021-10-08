package org.wayne.thief.util;


/**
 * @Description:
 * @author: lwq
 */
public class ChromeProxyUtil {
//    public static List<String> saveHttpTransferDataIfNecessary(ChromeDriverProxy driver) {
//        Logs logs = driver.manage().logs();
//        Set<String> availableLogTypes = logs.getAvailableLogTypes();
//        if (availableLogTypes.contains(LogType.PERFORMANCE)) {
//            LogEntries logEntries = logs.get(LogType.PERFORMANCE);
//            List<ResponseReceivedEvent> responseReceivedEvents = new ArrayList<>();
//            for (LogEntry entry : logEntries) {
//                JSONObject jsonObj = JSON.parseObject(entry.getMessage()).getJSONObject("message");
//                String method = jsonObj.getString("method");
//                String params = jsonObj.getString("params");
//                if (method.equals(NETWORK_RESPONSE_RECEIVED)) {
//                    ResponseReceivedEvent response = JSON.parseObject(params, ResponseReceivedEvent.class);
//                    responseReceivedEvents.add(response);
//                }
//            }
//            doSaveHttpTransferDataIfNecessary(driver, responseReceivedEvents);
//        }
//    } // 保存网络请求
//
//    private static void saveHttpTransferDataIfNecessary(ChromeDriverProxy driver, List<ResponseReceivedEvent> responses) {
//        List<String> content = new ArrayList<>(1024);
//        for (ResponseReceivedEvent response : responses) {
//            String url = response.getResponse().getUrl();
//            boolean staticFiles = url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".css") || url.endsWith(".ico") || url.endsWith(".js") || url.endsWith(".gif");
//            if (!staticFiles && url.startsWith("http")) {
//                content.add(url);
//                content.add(response.getResponse().getRequestHeadersText());
//                content.add(response.getResponse().getHeadersText());
//                // 使用上面开发的接口获取返回数据
//                ResponseBodyVo body = driver.getResponseBody(response.getRequestId());
//                if (body != null && body.getStatus() == 0) {
//                    content.add("base64Encoded:" + body.getValue().getBase64Encoded());
//                    content.add("body:\n" + body.getValue().getBody());
//                }
//                content.add("\n");
//            }
//        } // 写文件至本地
//    }
}
