$(function(){
    $("p label b").html($("select option:selected").text());
});

//$(".floor2").on("click","p",function(){
//    $(this).addClass("on");
//    $(this).siblings().removeClass("on")
//})
$("select").on("change",function(){
    var tabVal=$("select option:selected").text();
    $("p label").html(tabVal);
})
function blackShow(){
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
    alert(value);
    $("#product").text(skuName);
    $("#currentLevel").text(agentName);
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/getUpGradePackage.do',
        dataType: 'json',
        data:{skuId:skuId, agentLevelId:agentLevelId, userPid:userPid, skuName:skuName, agentName:agentName},
        success: function(data){
            if (data){
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
    alert(data);
    //alert(data[0] + "-" + data[1] + "-" + data[2] + "-" + data[3] + "-" + data[4]);
}