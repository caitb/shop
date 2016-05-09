$(function(){
    $("#extractBtnId").on("click", function(){
        if($(".sec2").length == 0){
            alert("请添加银行卡");
            return;
        }
        var money = $("#extractMoneyId").val();
        if(money.trim().length <= 0){
            alert("请输入提现金额");
            return;
        }
        if((+money) > (+maxMoney)){
            // 提现金额大于可提现金额
            alert("可提现金额最多为" + maxMoney);
            return;
        }
        if((+money) <= 0){
            alert("提现金额为："+money+"  请重新输入");
            return;
        }
        // 可以提现
        $("#ex_money").html(money + " 元");
        $(".back").show();
        $(".back_que").css("display", "-webkit-box");
    });

    $("#exBtnOk").on("click", function(){
        var money = $("#extractMoneyId").val();
        var options = {
            url:basepath + "extract/apply",
            type:"POST",
            dataType:"JSON",
            data:{money : money},
            success:function(response){
                if(response.resCode == "success"){
                    window.location.href = basepath + response.resUrl;
                    sendMessage(money);
                } else if (response.resCode == "fail") {
                    alert(response.resMsg);
                }
            },
            error:function(response){
                alert(response);
            }
        };
        $.ajax(options);
    });

    $("#exBtnCancel").on("click", function(){
        $(".back").css("display", "none");
        $(".back_que").css("display", "none");
    });
});

function choiceBank(){
    window.location.href = basepath + "extractwayinfo/findExtractwayInfo.shtml";
}

function backLastPage(){
    fullShow();//跳转页面钱展示全屏遮罩loading...
    window.location.href= basepath + "account/home";
}

/**
 * 申请提现成功发送短信
 */
function sendMessage(money){
    var options = {
        url:basepath + "extract/sendMessageWithdrawRequest.do",
        type:"POST",
        dataType:"JSON",
        data:{money : money},
        success:function(response){

        },
        error:function(response){

        }
    };
    $.ajax(options);
}