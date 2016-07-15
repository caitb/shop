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
        url:path + "/shopmessage/mycluster.do",
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
                        var ele = "<div class='sec1'>"
                                    + "<p style=\"background:url('" + data.head_url + "');background-size:100% 100%;\"></p>"
                                    + "<div class=\"s_b\">"
                                        + "<h1>" + data.shop_name + "</h1>"
                                        + "<div class=\"b_b\">"
                                            + "<img src=\"" + path + "/static/images/message/massage_r1_c1.png\" alt=\"\">"
                                            + "<h1>" + data.data[i].content + "</h1>"
                                            + "<p>";
                                        if(data.data[i].contentUrl != "" && data.data[i].contentUrl != null && data.data[i].contentUrl != undefined){
                                            ele += "<a href=\"" + data.path + "/" + data.data[i].contentUrl + "\">点击查看</a>";
                                        }
                                            ele  += "<a class='f_right'>" + data.data[i].createTime + "</a>"
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
                if(data.isLast != undefined) {
                    if (data.isLast == true) {
                        // 去掉加载更多
                        $("#more").hide();
                        $("#nomore").show();
                    } else {
                        // 显示加载更多
                        $("#more").show();
                        $("#nomore").hide();
                    }
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