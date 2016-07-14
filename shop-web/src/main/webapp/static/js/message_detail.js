$(function(){
    listMessage();

    $("#more").unbind("click").on("click", moreClick);

    $("#remore").unbind("click").on("click", moreClick);

});
var currentPageNum = -1;
var pageNums = 0;
var size = 0;

function moreClick(){
    document.getElementById("more").onclick = "";
    listMessage();
    document.getElementById("more").onclick = moreClick;
}

function listMessage(){
    var options = {
        url:path + "/message/detaillist.do",
        type:"post",
        dataType:"json",
        async:true,
        data:{cur:currentPageNum + 1, uid:uid},
        success:function(data){
            if(data.resCode == "success"){
                $("#remore").hide();
                currentPageNum = data.cur;
                pageNums = data.totalPage;
                size = data.pageSize;
                $("#fromUserName").html(data.fromUserName);
                if(data.hasData == true){
                    // 追加数据
                    for(var i=0; i < data.data.length; i++){
                        var ele =
                            "<div class=\"sec1\">"
                                + "<div class=\"s_b\">"
                                    + "<div class=\"b_b\">"
                                    + "<img src=\"" + path + "/static/images/message/massage_r1_c1.png\">"
                                    + "<h1>" + data.data[i].content + "</h1>"
                                    + "<p>"
                                        + "<a href=\"" + data.data[i].contentUrl + "\">点击查看</a>"
                                        + "<a>" + data.data[i].createTime + "</a>"
                                    + "</p>"
                                    + "</div>"
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