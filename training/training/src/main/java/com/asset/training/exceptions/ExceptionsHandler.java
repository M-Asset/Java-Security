package com.asset.training.exceptions;

import com.asset.training.cache.MessageCache;
import com.asset.training.constants.ErrorCodes;
import com.asset.training.models.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler{
    private final MessageCache messagesCache;

    @Autowired
    public ExceptionsHandler(MessageCache messagesCache) {
        this.messagesCache = messagesCache;
    }

    @ExceptionHandler(TrainingException.class)
    public ResponseEntity<BaseResponse> exceptions(TrainingException ex){
        String errorMessage = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null)
            errorMessage = messagesCache.replaceArgument(errorMessage, ex.getArgs());
        BaseResponse baseResponse = new BaseResponse(ex.getErrorCode(), errorMessage, null);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @ExceptionHandler (value = Exception.class)
    public ResponseEntity<BaseResponse> exception(Exception ex) {
        String errorMessage = messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR);
        BaseResponse baseResponse = new BaseResponse(ErrorCodes.ERROR.UNKNOWN_ERROR, errorMessage, null);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
