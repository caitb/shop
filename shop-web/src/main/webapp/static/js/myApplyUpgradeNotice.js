function blackShow(){
    $(".black").show();

}
function blackHide(){
    $(".black").hide();
}
$(".zhifu").on("click",function(){
    window.location.href = basePath + "upgrade/skipOrderPageGetNoticeInfo.html?upgradeNoticeId="+$("#upgradeId").val();
});
$(".que_que").on("click",function(){
    if ($("#isClick").val() == "true"){
        return;
    }
    $("#isClick").val("true");
    var upgradeId = $("#upgradeId").val();
    $.ajax({
        type: 'POST',
        url: basePath + 'upgrade/cannelUpgrade.do',
        dataType: 'json',
        data:{upgradeId:upgradeId },
        success: function(data){
            if (data){
                alert(data.message);
                if (data.isTrue == "true"){
                    blackHide();
                }
            }
            $("#isClick").val("false");
        },
        error: function(xhr, type){
            alert("网络错误");
        },
    });
});