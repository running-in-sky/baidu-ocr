package cn.zzzhy.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelResult implements Serializable {

    //字段	是否必选	类型	说明
    //log_id	是	long	唯一的log id，用于问题定位
    //result	是	object	返回的结果
    //+result_data	是	string	识别结果字符串，如果request_type是excel，则返回excel的文件下载地址，如果request_type是json，则返回json格式的字符串
    //+percent	是	int	表格识别进度（百分比）
    //+request_id	是	string	该图片对应请求的request_id
    //+ret_code	是	int	识别状态，1：任务未开始，2：进行中,3:已完成
    //+ret_msg	是	string	识别状态信息，任务未开始，进行中,已完成
    private Long log_id;
    private Result result;

    @Data
    public static class Result implements Serializable {
        private String result_data;
        private String request_id;
        private Integer percent;
        private Integer ret_code;
        private String ret_msg;
    }

}
