$(function(){
    $(".j_qu").click(function(){
        $(".back").attr('style', 'display:none');
        $(".back_j").attr('style', 'display:none');
    });
});

/**
 * 用户提现
 */
function withdraw(userId,extractableFee){
    alert(extractableFee);
    if (extractableFee == 0){
        alert("暂无可提现额度");
        return;
    }
    //判断该用户是否已经绑定
    $.ajax({
        type:"POST",
        async:true,
        url : basepath+"/sfuser/checkBinding.do",
        data:{userId:userId},
        dataType:"Json",
        success:function(data){
            if(data.isTrue == "false"){
                //先绑定用户
                alert(data.message);
                $(".back").attr('style', 'display:block');
                $(".back_j").attr('style', 'display:block');
            }else {
                fullShow();
                window.location.href = basepath + "withdraw/withdrawRequest.shtml"
            }
        },
        error: function(){
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}

/**
 * 加载更多佣金记录
 * @param userId
 * @param length
 */
function viewMore(userId){
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
        var currentPage = $("#currentPage").val();
        $.ajax({
            type:"POST",
            async:true,
            url : basepath+"/sfaccount/moreCommission.do",
            data:{userId:userId,currentPage:currentPage,count:count},
            dataType:"Json",
            success:function(data){
                var arr=eval(data);
                var html = "";
                for(var i=0;i<arr.length;i++)
                {
                    html += "<div class='sec2'><p>";
                    html += "<b>￥"+arr[i].distributionAmount+"</b>"
                    html += "<b><span>"+arr[i].nkName+"</span>在您的分享 <a href='#'>"+arr[i].skuName+"</a> 中产生了购买 </b></p>";
                    html += "<h1><span>"+arr[i].orderTime+"</span></h1></div>";
                }
                $("#itemDistributions").append(html);
                $("#currentPage").attr(currentPage + 1);

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

function showDetail(){
    $("#detail").show();
    $(".back").show();
}
function hideDetail(){
    $("#detail").hide();
    $(".back").hide();
}
