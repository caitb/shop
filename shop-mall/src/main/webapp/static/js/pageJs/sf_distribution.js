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
    var count = $(".record").length;
    var year = $("#year").val();
    var month = $("#month").val();
    if (currentPage < totalPage) {
        $.ajax({
            type: "POST",
            url: basepath + "distribution/moreDistribution.do",
            data: {currentPage: currentPage, count: count, year: year, month: month},
            dataType: "Json",
            success: function (data) {
                if (data.isTrue == "false") {
                    alert(data.message);
                } else {
                    $("#divall").append(data.message);
                    $("#currentPage").val(currentPage + 1);
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
    $.ajax({
        type: "POST",
        url: basepath + "distribution/moreDistribution.do",
        data: {currentPage: 0, count: 0, year: year, month: month},
        dataType: "Json",
        success: function (data) {
            $("#divall").empty();
            if (data.isTrue == "false") {
                alert(data.message);
            } else {
                $("#divall").html(data.message);
            }
            $("#currentPage").val(1);
        },
        error: function () {
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}

