package cn.zzzhy;

import cn.zzzhy.service.ImageRecognizeService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        String path = "/Users/gongjunhui/zwl/src/main/resources/aaa.png";
        String url = ImageRecognizeService.recognize(path);
        log.info("digit url: {}", url);
    }

}
