$(function(){
    listMessage();

    $("#more").unbind("click").on("click", moreClick);

    $("#remore").unbind("click").on("click", moreClick);

    $(document).on("click", "#mlist .sec1", viewDetail);
});
var currentPageNum = -1;
var pageNums = 0;
var size = 0;

function viewDetail(){
    $("#mlist .sec1").unbind("click");
    window.location.href = path + "/message/detail.shtml?userId=" + $(this).attr("id");
    $(document).on("click", "#mlist .sec1", viewDetail);
}

function moreClick(){
    document.getElementById("more").onclick = "";
    listMessage();
    document.getElementById("more").onclick = moreClick;
}

function listMessage(){
    var options = {
        url:path + "/message/centerlist.do",
        type:"post",
        dataType:"json",
        async:true,
        data:{cur:currentPageNum + 1},
        success:function(data){
            if(data.resCode == "success"){
                $("#remore").hide();
                currentPageNum = data.cur;
                pageNums = data.totalPage;
                size = data.pageSize;
                if(data.hasData == true){
                    // 追加数据
                    for(var i=0; i < data.data.length; i++){
                        var ele = "<div id='" + data.data[i].fromUserId + "' class=\"sec1\">"
                                + "<h1><img src=\"" + data.data[i].headUrl + "\">";
                                if(data.data[i].isSeeNum > 0) {
                                    ele += "<span>" + data.data[i].isSeeNum + "</span></h1>";
                                } else {
                                    ele += "</h1>";
                                }
                                ele += "<div>"
                                    + "<h2>" + data.data[i].fromUserName + "</h2>"
                                    + "<p>" + data.data[i].latestMessage + "</p>"
                                + "</div>"
                            + "</div>";
                        $("#mlist").append($(ele));
                    }
                } else {
                    // 去掉加载更多
                    $("#more").hide();
                    if(data.cur == 0){
                        // 显示暂无数据
                        $("#nomore").show();
                    }
                }
                if(data.isLast == true){
                    // 去掉加载更多
                    $("#more").hide();
                    $("#nomore").show();
                } else {
                    // 显示加载更多
                    $("#more").show();
                    $("#nomore").hide();
                }
            } else {
                // 网络错误,显示重新加载
                $("#remore").show();
            }
        },
        error:function(data){
            $("#more").hide();
            $("#nomore").hide();
            $("#remore").show();
        }
    };
    $.ajax(options);
}