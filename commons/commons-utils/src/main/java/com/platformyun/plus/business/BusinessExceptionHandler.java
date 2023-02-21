package com.platformyun.plus.business;


import com.platformyun.plus.commons.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseResult businessException(HttpServletRequest request, BusinessException ex) {
//        log.warn("\n全局业务异常编码：\n{}\n异常记录：\n{}",ex.getCode(),ex.getMessage());
        return new ResponseResult(ex.getCode(),ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        log.error("\n数据校验异常:\n{}", ex.getMessage());
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        String errMessage="";
        for(int i=0;i<errors.size();i++){
            errMessage += errors.get(i).getDefaultMessage();
            if(i<errors.size()-1){
                errMessage +=",";
            }
            errorMap.put(errors.get(i).getField(), errors.get(i).getDefaultMessage());
        }
        return new ResponseResult(BusinessStatus.FAIL.getCode(),"数据校验异常,请检查:"+errMessage,errorMap);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseResult exception(Exception ex) {
//        log.error("\n全局业务未知异常：\n" + ex.getMessage());
        return new ResponseResult(BusinessStatus.FAIL.getCode(),"网络异常，请联系管理员！");
    }

}

