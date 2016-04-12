
function showMore() {
    var currentPage = $("#currentPage").val();
    var totalPage = $("#totalPage").val();
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
function showShop(shopId){
    alert("shopId:"+shopId);
}