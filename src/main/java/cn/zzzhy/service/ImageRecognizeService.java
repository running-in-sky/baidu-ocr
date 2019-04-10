package cn.zzzhy.service;

import cn.hutool.core.util.ImageUtil;
import cn.zzzhy.bean.AsyncResult;
import cn.zzzhy.bean.ExcelResult;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Slf4j
public final class ImageRecognizeService {

    private final static String digitAsyncResultUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/request?access_token=";
    private final static String digitExcelResultUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/get_request_result?access_token=";

    private static Map<String, String> generateImageParams(String filePath) throws IOException {
        String imageType = FilenameUtils.getExtension(filePath);
        String base64 = ImageUtil.toBase64(
                ImageUtil.toImage(Files.readAllBytes(Paths.get(filePath))),
                imageType.toLowerCase());
        Map<String, String> asyncResultMap = Maps.newHashMap();
        asyncResultMap.put("image", base64);
        return asyncResultMap;
    }

    public static String recognize(String filePath) throws IOException, InterruptedException, ExecutionException {
        String accessToken = AuthService.getAuth();
        Map<String, String> asyncResultMap = generateImageParams(filePath);
        return recognize(accessToken, asyncResultMap);
    }

    private static String recognize(String accessToken, Map<String, String> asyncResultMap) throws IOException, InterruptedException {
        String asyncResultString = OkHttpService.sendPostForm(digitAsyncResultUrl + accessToken, asyncResultMap);
        AsyncResult asyncResult = JSON.parseObject(asyncResultString, AsyncResult.class);
        while (true) {
            TimeUnit.SECONDS.sleep(10);
            asyncResultMap.clear();
            asyncResultMap.put("request_id", asyncResult.getResult().get(0).getRequest_id());
            asyncResultMap.put("result_type", "excel");
            String excelResultString = OkHttpService.sendPostForm(digitExcelResultUrl + accessToken, asyncResultMap);
            ExcelResult excelResult = JSON.parseObject(excelResultString, ExcelResult.class);
            switch (excelResult.getResult().getRet_code()) {
                //1：任务未开始，2：进行中,3:已完成
                default:
                case 1:
                    log.info("解析任务未开始");
                    break;
                case 2:
                    log.info("解析任务进行中");
                    break;
                case 3:
                    log.info("解析任务已完成");
                    return excelResult.getResult().getResult_data();
            }
        }
    }

}
