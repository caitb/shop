
function queryFans(condition){
    var shopId = $("#goods").val();
    var fansLevel = $("#level").val();
    $.ajax({
        type:"POST",
        url : basepath+"/distribution/fans.do",
        data:{shopId:shopId,fansLevel:fansLevel},
        dataType:"Json",
        success:function(data){
            var arr=eval(data);
            var firstCount = arr[0].firstCount;
            var secondCount = arr[0].secondCount;
            var thirdCount = arr[0].thirdCount;
            var totalCount = arr[0].totalCount;
            var threeSum = arr[0].threeSum;
            var totalPage = arr[0].totalPage;
            var cur = arr[0].currentPage;
            if (parseInt(condition) == 1&&parseInt(fansLevel) == 0){
                $("#first").text(firstCount);
                $("#second").text(secondCount);
                $("#third").text(thirdCount);
                $("#total").text(totalCount);
            }
            $("#currentPage").val(cur);
            $("#totalPage").val(totalPage);
            $("#totalCount").val(threeSum);
            var html = createHtml(arr[0].infos);
            $("#distributions").html(html);
            if (threeSum <= $(".sec1").length){
                $("#showMore").html("");
            }else {
                $("#showMore").html("<a href=\"#\" onclick=\"viewMore()\">查看更多></a>");
            }
        },
        error: function(){
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}

function viewMore(){
    var shopId = $("#goods").val();
    var fansLevel = $("#level").val();
    var currentPage = $("#currentPage").val();
    $.ajax({
        type:"POST",
        url : basepath+"/distribution/moreFans.do",
        data:{shopId:shopId,fansLevel:fansLevel,currentPage:currentPage},
        dataType:"Json",
        success:function(data){
            var arr=eval(data);
            var html = createHtml(arr[0].infos);
            var cur = arr[0].currentPage;
            $("#currentPage").val(cur);
            $("#distributions").append(html);
            if ($("#totalCount").val() <= $(".sec1").length){
                $("#showMore").html("");
            }else {
                $("#showMore").html("<a href=\"#\" onclick=\"viewMore()\">查看更多></a>");
            }
        },
        error: function(){
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}

function createHtml(infos){
    var html = "";
    for (var i = 0; i < infos.length; i++){
        html += "<div class=\"sec1\" >";
        html += "<h1 style=\"background:url('"+ infos[i].headImg +"');background-size:100% 100%;\"></h1>";
        html += "<div>";
        html += "<h2>" + infos[i].wxName + "<span>" + infos[i].userLevelView + "</span> <b>" + infos[i].sopkenManView + "</b></h2>";
        html += "<p>";
        html += "<span>ID:" + infos[i].ID + "</span>";
        html += "<span>" + infos[i].createTimeView + "</span>";
        html += "</p></div></div>";
    }
    return html;
}