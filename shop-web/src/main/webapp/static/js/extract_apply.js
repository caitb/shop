$(function(){
    $("#extractBtnId").on("click", function(){
        var money = $("#extractMoneyId").val();
        if((+money) > (+maxMoney)){
            // 提现金额大于可提现金额
            alert("可提现金额最多为" + maxMoney);
            return;
        }
        // 可以提现
        $(".back").css("display", "-webkit-box");
    });
});

function choiceBank(){
    window.location.href = basepath + "extractwayinfo/findByUserId.do";
}