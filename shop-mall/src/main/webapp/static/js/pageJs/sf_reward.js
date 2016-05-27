$(function(){
    $(".j_qu").click(function(){
        $(".back").attr('style', 'display:none');
        $(".back_j").attr('style', 'display:none');
    });
});

/**
 * 加载更多佣金记录
 * @param userId
 * @param length
 */
function viewMore(){
    var totalCount = parseInt($("#totalCount").val());
    //获取当前页面的佣金记录数
    var count = $(".sec2").length;
    if (totalCount == 0){
        alert("当前没有记录");
        return;
    }
    if (count == totalCount){
        alert("已经是全部记录");
        return;
    }
    //当前页面记录数小于总记录数
    if (count < totalCount){
        var currentPage = parseInt($("#currentPage").val());
        $.ajax({
            type:"POST",
            async:true,
            url : basepath+"/sfaccount/moreCommission.do",
            data:{currentPage:currentPage,count:count},
            dataType:"Json",
            success:function(data){
                var arr=eval(data);
                var html = "";
                for(var i=0;i<arr.length;i++)
                {
                    html += "<div class=\"sec2\"><h1>";
                    html += "<b>￥"+arr[i].distributionAmount+"</b>";
                    html += "<span>"+arr[i].orderTime+"</span>";
                    html += "</h1><p><b>";
                    html += "<span>"+arr[i].nkName+"</span>在您的分享<a href=\"#\">"+arr[i].skuName+"</a>中产生了购买";
                    html += "</b></p></div>"
                }
                $("#itemDistributions").append(html);
                $("#currentPage").val(currentPage + 1);

                if (totalCount <= $(".sec2").length){
                    $("#showMore").html("");
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
}
function withdrawRequest(isBuy,hasOrder,extractableFee){

    if (parseInt(extractableFee) == 0 && /^0\.[1-9]\d*$/.test(extractableFee) == false){
        alert("亲，您的可提现佣金为0。赶快去分享赚佣金吧~");
        return;
    }
    if (parseInt(hasOrder) == 0){
        alert("您需要至少购买一件商品，才可提现。去店铺看看吧~");
        return;
    }
    if (parseInt(isBuy) == 0){
        alert("您购买的商品还没超过7天，暂时无法提现，请耐心等待。");
        return;
    }
    validateCodeJS.applyTrial('withdrawRequest');
}

