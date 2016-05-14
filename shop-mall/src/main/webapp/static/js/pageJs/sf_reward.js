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
function withdrawRequest(isBuy){
    if (parseInt(isBuy) != 1){
        alert("您需要至少购买一件商品，确认收货7天后即可提现。");
        return;
    }
    validateCodeJS.applyTrial('withdrawRequest');
}

