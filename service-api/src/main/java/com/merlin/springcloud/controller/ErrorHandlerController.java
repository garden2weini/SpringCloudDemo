package com.merlin.springcloud.controller;

import com.merlin.springcloud.result.ResponseData;
import com.merlin.springcloud.result.ResponseDataUtil;
import com.merlin.springcloud.result.ResultEnums;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller统一异常处理
 * REF: https://blog.csdn.net/weixin_33863087/article/details/89540234
 */
@RestController
public class ErrorHandlerController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/error")
    public ResponseData error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request);
        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");
        if (StringUtils.isNotBlank(trace)) {
            message += String.format("and trace %s", trace);
        }
        return ResponseDataUtil.buildError(ResultEnums.SERVER_ERROR_CODE.getCode(), message + "(provided by ErrorHandlerControlller in service api module)");
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
    }
}
