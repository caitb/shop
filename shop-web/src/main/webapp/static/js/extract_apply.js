$(function(){
    $("#extractBtnId").on("click", function(){
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
        // 可以提现
        $("#ex_money").html(money + " 元");
        $(".back").css("display", "-webkit-box");
    });

    $("#exBtnOk").on("click", function(){
        var money = $("#extractMoneyId").val();
        var options = {
            url:basepath + "extractapply/apply",
            type:"POST",
            dataType:"JSON",
            data:{money : money},
            success:function(response){
                if(response.resCode == "success"){
                    window.location.href = basepath + response.resUrl;
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
    });
});

function choiceBank(){
    window.location.href = basepath + "extractwayinfo/findByUserId.do";
}