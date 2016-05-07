function showDetails(sfDistributionPersons){
    if (sfDistributionPersons.length == 0){
        return;
    }
    var html = "";
    for (var i = 0;i <sfDistributionPersons.length;i++){
        html = html + "<div><p>"+sfDistributionPersons[i].wxNkName+":</p><p>￥"+sfDistributionPersons[i].amount+"</p></div>";
    }
    html = html + "<button onclick=\"hideDetail()\">知道了</button>";
    $(".back_f").html(html);
    $(".back_f").show();
    $(".back").show();
}

function hideDetail(){
    $(".back_f").hide();
    $(".back").hide();
}

function showMore() {
    var currentPage = parseInt($("#currentPage").val());
    var totalPage = parseInt($("#totalPage").val());
    var totalCount = parseInt($("#totalCount").val());
    var count = $(".record").length;
    var year = $("#year").val();
    var month = $("#month").val();
    if (currentPage < totalPage) {
        $.ajax({
            type: "POST",
            url: basePath + "distribution/moreDistribution.do",
            data: {currentPage: currentPage, count: count, year: year, month: month},
            dataType: "Json",
            success: function (data) {
                if (data.isTrue == "false") {
                    alert(data.message);
                } else {
                    $("#divall").append(data.message);
                    $("#currentPage").val(currentPage + 1);
                }
                if($(".record").length >= totalCount){
                    $("#show").html("");
                }else {
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

function turnMonth(year,month){
    var yearLast = $("#year").val();
    var monthLast = $("#month").val();
    if (yearLast == year && monthLast == month){
        return;
    }
    $.ajax({
        type: "POST",
        url: basePath + "distribution/moreDistribution.do",
        data: {currentPage: 0, count: 0, year: year, month: month},
        dataType: "Json",
        success: function (data) {
            $("#divall").empty();
            if (data.isTrue == "false") {
                alert(data.message);
                $("#show").html("");
            } else {
                $("#totalCount").val(data.totalCount);
                $("#totalPage").val(data.totalPage);
                $("#divall").html(data.message);
                if($(".record").length >= data.totalCount){
                    $("#show").html("");
                }else {
                    $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                }
            }
            $("#currentPage").val(1);
            $("#year").val(year);
            $("#month").val(month);
        },
        error: function () {
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}

function viewOrder(orderId){
    if (orderId == ""){
        return;
    }
    window.location.href = basePath + "borderManage/borderDetils.html?id=" + orderId;
}

