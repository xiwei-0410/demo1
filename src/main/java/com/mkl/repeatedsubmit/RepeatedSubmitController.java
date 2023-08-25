package com.mkl.repeatedsubmit;

import com.mkl.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 重复提交问题
 * @author mkl
 */
@RequestMapping("/api/repeatedSubmit/")
@Controller
@ResponseBody
public class RepeatedSubmitController {

    @RequestMapping("/submit")
    @RepeatSubmit
    public Object submit(){
        return Result.success("==========测试======");
    }



}
