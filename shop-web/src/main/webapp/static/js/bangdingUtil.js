$(".bd").on("click",function(){
    if ($(".tel").val()==""){
        $(".tishi").show().html("手机号不能为空")
        $(".tel").focus();
        return false;
    }
    if (!isMobile($(this).val()))
    {
        $(".tishi").show().html("手机号输入有误")
        $(".tel").focus();
        $(".tel").focus().css("color","red");
        return false;
    }else{
        $(".tishi").hide()
    }



    if ($(".password").val()==""){
        $(".tishi").show().html("密码不能为空");
        $(".password").focus();
        return false;
    }
    if (!isCardName($(".password").val())){
        $(".tishi").show().html("密码只支持数字和字母")
        $(".password").focus().css("color","red");
        return false;
    }else{
        $(".tishi").hide();
    }


    function isMobile(s){
        var patrn=/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
        if(!patrn.exec(s))
        {
            return false;
        }
        return true;
    }

    function isCardName(s){
        var patern = /[^0-9a-zA-Z]/;
        //return patern.test(s);
        if(patern.exec(s))
        {
            return false;
        }
        return true;
    }
})

$("#codeId").click(function(){
    times();
    $.ajax({
        type:"POST",
        url : "<%=path%>/binding/securityCode.do",
        data:"phone="+$(".tel").val(),
        dataType:"Json",
        success:function(result){
            if(!result){
                $(".tishi").show().html("短信发送失败,请重试!")
                $(".yan").focus().css("color","red");
            }
        }
    });
});

    var s = 60, t;
    function times(){
        s--;
        $("#codeId").val("剩余" + s + "s");
        $("#codeId").attr({"disabled":"disabled"});
        t = setTimeout(function (){times();}, 1000);
        if ( s <= 0 ){
            s = 60;
            $("#codeId").removeAttr("disabled");
            $("#codeId").val("获取验证码");
            clearTimeout(t);
        }
    }
