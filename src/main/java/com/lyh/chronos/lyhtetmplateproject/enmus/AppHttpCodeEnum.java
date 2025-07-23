package com.lyh.chronos.lyhtetmplateproject.enmus;

public enum AppHttpCodeEnum {
    //操作成功
    SUCCESS(200,"操作成功"),
    //操作失败
    ERROR(500,"操作失败"),
    DATA_NOT_EXIST(501,"对象无法访问" ),
    CODE_ERROR(400, "验证码错误"),
    CODE_NOT_FOUND(400, "未找到验证码"),
    USERNAME_EXIST(410, "用户名已占用"),
    EMAIL_EXIST(410, "邮箱已占用"),
    EMAIL_NOT_FOUND(410, "邮箱不存在"),
    PASSWORD_ERROR(403, "密码错误"),
    NEED_LOGIN(402, "用户未登录"),
    USER_NOT_FOUND(410, "用户不存在"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    UPDATE_FAILED(410, "更新失败");
    int code;
    String msg;
    AppHttpCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
