/*$(".tel").on("blur",function(){

    if ($(".tel").val()==""){
                        $(".tishi").show().html("手机号输入有误")
                        return false;
                    }
                    if (!isMobile($(this).val()))
                    {
                        $(".tishi").show().html("手机号输入有误")
                        return false;
                    }
                    function isMobile(s){
                        var patrn=/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                        if(!patrn.exec(s))
                        {
                            return false;
                        }
                        return true;
                    }
})*/

$("#ce").on("click",function(){
                    if ($(".name").val()==""){                        
                        $(".tishi").show().html("姓名不能为空");
                        $(".name").focus();
                        return false;
                    }
                    if (!isCardName($(".name").val())){                        
                         $(".tishi").show().html("姓名格式错误")
                         $(".name").focus().css("color","red");
                        return false;
                    }else{
                       $(".tishi").hide();
                    }
                //检验汉字
                    function isChinese(s){
                        var patrn = /^\s*[\u4e00-\u9fa5]{1,15}\s*$/;
                        if(!patrn.exec(s))
                        {
                            return false;
                        }
                        return true;
                    }

                    //检验姓名：姓名是2-15字的汉字
                    function isCardName(s){
                        var patrn = /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,15}[\u4e00-\u9fa5]{1,}\s*$/; 
                        if(!patrn.exec(s))
                        {
                            return false;
                        }
                        return true;
                    }
    
    
         if ($(".tel").val()==""){
                            $(".tishi").show().html("手机号不能为空")
                            $(".tel").focus();
                            return false;
                        }
                        if (!isMobile($(this).val()))
                        {
                            $(".tishi").show().html("手机号输入有误")
                            $(".tel").focus();
                            $(".name").focus().css("color","red");
                            return false;
                        }else{
                            $(".tishi").hide()
                        }
                        function isMobile(s){
                            var patrn=/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                            if(!patrn.exec(s))
                            {
                                return false;
                            }
                            return true;
                        }
})


$(".yan").on("blur",function(){
        if ($(this).val()=="")
                    {                        
                        $(this).parents("p").addClass("yno");
                        $(this).css({"color":"#F74A11"})
                        return false;
                    }
                    if (!isNumber($(this).val()))
                    {                        
                        $(this).parents("p").addClass("yno");
                        $(this).css({"color":"#F74A11"})
                        return false;
                    }else{
                        $(this).parents("p").addClass("yon");
                        return true;
                    }
                //检验汉字
                        function isNumber(s) 
                    {
                        var patrn =/^\d{6}$/;
                        if(!patrn.exec(s))
                        {
                            return false;
                        }
                        return true;
                    }
})
//身份证
/*function ShenFen(){
    if ($(this).val()=="")
    {
        $(this).attr("placeholder","身份证号码不能为空！");
        return false;
    }
    if (!isIdCard($(this).val()))
    {
        $(this).attr("placeholder","身份证号码错误！");
        return false;
    }

    function isIdCard(s) 
    {
        var patrn =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
        var patrn1 =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
        if(!patrn.exec(s) && !patrn1.exec(s))
        {
            return false;
        }
        return true;
    }
}*/
//手机验证

$("input").on("focus",function(){
     $(this).parents("p").removeClass("pon");
    $(".no").hide();
    $(this).css("color","#333333")
})
