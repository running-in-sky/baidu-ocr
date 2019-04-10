package cn.zzzhy.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AsyncResult implements Serializable {

    //字段	是否必选	类型	说明
    //log_id	是	long	唯一的log id，用于问题定位
    //result	是	list	返回的结果列表
    //+request_id	是	string	该请求生成的request_id，后续使用该request_id获取识别结果
    private Long log_id;
    private List<RequestId> result;
    private String error_code;
    private String error_msg;

    @Data
    public static class RequestId implements Serializable {
        private String request_id;
    }

}
