package cn.zzzhy.service;

import cn.zzzhy.bean.AccessTokenResult;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
class AuthService {

    private final static String ACCESS_TOKEN = "access_token";
    private final static Cache<String, String> cache = CacheBuilder.newBuilder()
            .initialCapacity(1)
            .expireAfterWrite(25000L, TimeUnit.SECONDS)
            .build();

    static String getAuth() throws ExecutionException {
        return cache.get(ACCESS_TOKEN, () -> {
            // 官网获取的 API Key 更新为你注册的
            String clientId = "s7PVCUVyCEeDWZ6TAZBbpMUG";
            // 官网获取的 Secret Key 更新为你注册的
            String clientSecret = "n4GHpGOH8FDHxCIDYbEV3ALjaUkmCFhL";
            try {
                log.info("获取 access_token.");
                return getAuth(clientId, clientSecret);
            } catch (IOException e) {
                log.error("获取 access_token 失败", e);
                throw new RuntimeException(e);
            }
        });
    }

    private static String getAuth(String ak, String sk) throws IOException {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        String result = OkHttpService.sendGet(getAccessTokenUrl);
        AccessTokenResult accessToken = JSON.parseObject(result, AccessTokenResult.class);
        return accessToken.getAccess_token();
    }

}
