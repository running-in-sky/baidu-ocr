package cn.zzzhy.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenResult implements Serializable {

    //access_token： 要获取的Access Token；
    //expires_in： Access Token的有效期(秒为单位，一般为1个月)；
    //error： 错误码；关于错误码的详细信息请参考下方鉴权认证错误码。
    //error_description： 错误描述信息，帮助理解和解决发生的错误。
    private String access_token;
    private Long expires_in;
    private Integer error;
    private String error_description;

}
