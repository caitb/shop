function gohistory(){
    var cur = parseInt($("#currentPage").val());
    var num = $("#pageNums").val();
    var ID = parseFloat($("#ID").val());
    var hiddenID = $("#hiddenID").val();
    if (ID != hiddenID){
        ID = hiddenID;
    }
    ajaxQuery(cur - 1,1,num,ID);
}
function checkInfo(){
    var ID = parseFloat($("#ID").val());
    var totalCount = $("#totalNum").val();
    if (totalCount == 0){
        alert("暂无数据");
        return;
    }
    ajaxQuery(1,0,0,ID);
}
function nextPage(){
    var ID = parseFloat($("#ID").val());
    var hiddenID = $("#hiddenID").val();
    if (ID != hiddenID){
        ID = hiddenID;
    }
    var currentPage = $("#currentPage").val();
    var pageNums = $("#pageNums").val();
    ajaxQuery(currentPage,1,pageNums,ID)
}
function lastPage(){
    var ID = parseFloat($("#ID").val());
    var hiddenID = $("#hiddenID").val();
    if (ID != hiddenID){
        ID = hiddenID;
    }
    var currentPage = $("#currentPage").val();
    var pageNums = $("#pageNums").val();
    ajaxQuery(currentPage,2,pageNums,ID)
}
function ajaxQuery(currentPage,queryType,pageNums,ID){
    $.ajax({
        type: 'POST',
        async:true,
        url: basePath + 'distribution/findByID.do',
        dataType: 'json',
        data:{currentPage:currentPage, queryType:queryType, pageNums:pageNums, ID:ID},
        success: function(data){
            if (data.isTrue){
                $("#pageNums").val(data.totalPage);
                $("#currentPage").val(data.currentPage);
                if (queryType == 0){
                    $("#totalNum").val(data.totalCount);
                    $("#hiddenID").val(ID);
                }
                var html = createHtml(data.infos);
                $("#disSpokesMan").html(html);
                $("#lable").html("<label>"+data.currentPage+"/"+data.totalPage+"</label>");
            }else {
                alert(data.msg);
            }
        },
        error: function(){
            //alert("请稍后再试");
        }
    });
}

function createHtml(json){
    var html = "";
    for (var i = 0; i < json.length; i++){
        html += "<div class=\"sec1\" ><div class=\"s_t\">";
        html += "<p onclick=\"toDetail("+json[i].userId+")\" style=\"background:url('"+json[i].headImg+"');background-size:100% 100%;\"></p>";
        html += "<div>";
        html += "<p><span>"+json[i].wxName+"</span><span>"+json[i].isBuyView+"</span></p>"
        html += "<p><span>ID："+json[i].ID+"</span><span>"+json[i].createTimeView+"</span></p>"
        html += "</div></div>";
        html += "<p class=\"s_b\">";
        html += "<b>代言人数："+json[i].spokesManNum+"</b>";
        html += "<b>粉丝数："+json[i].fansNum+"</b></p></div>";
    }
    return html;
}

function toDetail(userId){
    fullShow();
    window.location.href = basePath + "distribution/spokesManDetail.shtml?showUserId="+userId;
}