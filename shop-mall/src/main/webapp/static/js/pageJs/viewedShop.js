
function showMore() {
    var currentPage = parseInt($("#currentPage").val());
    var totalPage = parseInt($("#totalPage").val());
    var totalCount = parseInt($("#count").val());
    var count = $(".shop").length;
    if (currentPage < totalPage) {
        $.ajax({
            type: "POST",
            url: basepath + "shopview/showMoreViewed.do",
            data: {currentPage: currentPage, count: count},
            dataType: "Json",
            success: function (data) {
                if (data.isTrue == "false") {
                    alert(data.message);
                } else {
                    $("#viewedShop").append(data.message);
                }
                $("#currentPage").val(currentPage + 1);
                if ($(".shop").length >= totalCount){
                    $("#show").html("");
                }else{
                    $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                }
            },
            error: function () {
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    } else {
        alert("已经加载全部数据");
    }
}

/**
 * 查看商铺详情
 * @param shopId
 */
function showShop(shopId,userId){
    fullShow();
    window.location.href=basepath+shopId+"/"+userId+"/shop.shtml";
}

function share(shopId){
    fullShow();
    window.location.href = basepath + "shop/getPoster?shopId=" + shopId;
}