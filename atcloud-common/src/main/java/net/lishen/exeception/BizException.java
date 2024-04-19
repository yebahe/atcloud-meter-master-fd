package net.lishen.exeception;

import lombok.Data;
import net.lishen.enums.BizCodeEnum;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.exeception
 * @Project：atcloud-meter
 * @name：BizException
 * @Date：2023-12-29 9:06
 * @Filename：BizException
 */
@Data
public class BizException extends RuntimeException {

    private int code;
    private String msg;
    private String detail;
    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(BizCodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

    public BizException(BizCodeEnum bizCodeEnum,Exception e){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
        this.detail = e.toString();
    }
}