package com.stylefeng.guns.gateway.modular.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {
    /**
     * 0 - 成功
     * 1 - 业务失败
     * 999 - 系统异常
     */
    private int status;
    private String msg;
    private T data;
    private String imgPre;
    private int nowPage;
    private int totalPage;

    private ResponseVO() {
    }

    public static <T> ResponseVO success(T data) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(data);

        return responseVO;
    }

    public static <T> ResponseVO success(String imgPre, T data) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static <T> ResponseVO success(String imgPre, T data, int nowPage, int totalPage) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);

        return responseVO;
    }

    public static ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static <T> ResponseVO serviceFail(String msg) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static <T> ResponseVO systemFail(String msg) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }
}
