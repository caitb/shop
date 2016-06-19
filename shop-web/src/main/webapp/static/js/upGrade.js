$(function(){
    $("p label b").html($("select option:selected").text());
});

//$(".floor2").on("click","p",function(){
//    $(this).addClass("on");
//    $(this).siblings().removeClass("on")
//})
$("select").on("change",function(){
    var tabVal=$("select option:selected").text();
    $("p label b").html(tabVal);
})
function blackShow(){
    var chooseWhether = $("#chooseWhether").val();
    if (chooseWhether == "false"){
        alert("请选择等级");
        return;
    }
    $(".black").show();
}
function blackHide(){
    $(".black").hide();
}
function changeSku(){
    var value = $("#name").val();
    if (value == ""){
        $("#upGradePackage").html("<p>请选择产品</p>");
        $("#product").text("请选择产品");
        $("#currentLevel").text("请选择产品");
        return;
    }
    var data = value.split("_");
    var skuId = data[0];
    var skuName = data[1];
    var agentLevelId = data[2];
    var agentName = data[3];
    var userPid = data[4];
    $("#product").text(skuName);
    $("#currentLevel").text(agentName);
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/getUpGradePackage.do',
        dataType: 'json',
        data:{skuId:skuId, agentLevelId:agentLevelId, userPid:userPid, skuName:skuName, agentName:agentName},
        success: function(data){
            if (data){
                $("#skuId").val(skuId);
                $("#userPid").val(userPid);
                $("#upAgentLevel").val(data.upAgentLevel);
                if (data.isTrue == "true"){
                    $("#upGradePackage").html(data.message);
                }else {
                    $("#upGradePackage").html("<p>" + data.message + "</p>");
                }
            }
        },
        error: function(xhr, type){
            alert("网络错误");
        }
    });
}
function choiceAgent(data){
    $(".floor2").on("click","p",function(){
        $(this).addClass("on");
        $(this).siblings().removeClass("on")
    });
    var msg = data.split(",");
    var skuName = msg[0];
    var curAgentLevel = msg[1];
    var curAgentName = msg[2];
    var upgradeLevel = msg[3];
    var upgradeName = msg[4];
    $("#curAgentLevel").val(curAgentLevel);
    $("#upgradeLevel").val(upgradeLevel);
    $("#productName").text(skuName);
    $("#curLevel").text(curAgentName);
    $("#upLevel").text(upgradeName);
    $("#chooseWhether").val(true);
}

$(".que_que").on("click",function(){
    var curAgentLevel = $("#curAgentLevel").val();
    var upgradeLevel = $("#upgradeLevel").val();
    var skuId = $("#skuId").val();
    var userPid = $("#userPid").val();
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/upGradeConfirm.do',
        dataType: 'json',
        data:{curAgentLevel:curAgentLevel, upgradeLevel:upgradeLevel, skuId:skuId, userPid:userPid},
        success: function(data){
            if (data){
                upgradeApplySubmitNotice(data.keyProperty);
                if (data.isTrue == "true"){
                    if (data.isEquals == "true"){
                        window.location.href = basePath + "upgrade/applicationComplete.shtml";
                    }else {
                        window.location.href = basePath + "upgrade/skipOrderPageGetNoticeInfo.html?upgradeNoticeId="+data.keyProperty;
                    }
                }else {
                    alert(data.message);
                }
            }
        },
        error: function(xhr, type){
            alert("网络错误");
        }
    });
});

function upgradeApplySubmitNotice(keyProperty){
    var upgradeLevel = $("#upgradeLevel").val();
    var upAgentLevel = $("#upAgentLevel").val();
    var userPid = $("#userPid").val();
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/upgradeApplySubmitNotice.do',
        dataType: 'json',
        data:{upgradeLevel:upgradeLevel, upAgentLevel:upAgentLevel, upgradeId:keyProperty, userPid:userPid},
        success: function(data){
            if (data){
                upgradeApplySubmitNotice(data.keyProperty);
                if (data.isTrue == "true"){
                    if (data.isEquals == "true"){
                        window.location.href = basePath + "upgrade/applicationComplete.shtml";
                    }else {
                        window.location.href = basePath + "upgrade/skipOrderPageGetNoticeInfo.html?upgradeNoticeId="+data.keyProperty;
                    }
                }else {
                    alert(data.message);
                }
            }
        },
        error: function(xhr, type){
            alert("网络错误");
        }
    });
}
