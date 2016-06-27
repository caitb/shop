function blackShow(){
    $(".black").show();
}
function blackHide(){
    $(".black").hide();
}

$(".que_que").on("click",function(){
    var upgradeId = $("#upgradeId").val();
    $("#confirm").attr('disabled',true);
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/temporarilyUpgrade.do',
        dataType: 'json',
        data:{upgradeId:upgradeId},
        success: function(data){
            if (data){
                if (data.isTrue == "true"){
                    alert(data.message);
                    blackHide();
                    $("#floor3").html("").append("<h1>您已选择暂不升级，您的下级将会与您解除关系。您可以获得一次性奖励</h1>");
                    $("#floor2").html("");
                    $("#status").text(data.status);
                    //var newUp = data.newUp;
                    notUpgradeMessage(upgradeId);
                }else {
                    alert(data.message);
                    $("#confirm").attr('disabled',false);
                }
            }
        },
        error: function(xhr, type){
            alert("网络错误");
        }
    });
});

function toUpgrade(){
    fullShow();
    window.location.href = basePath + "upgrade/init.shtml";
}

/**
 * 暂不升级发送微信消息
 * @param keyProperty
 */
function notUpgradeMessage(upgradeId){
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/notUpgradeMessage.do',
        dataType: 'json',
        data:{upgradeId:upgradeId},
        success: function(data){

        },
    });
}