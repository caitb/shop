$(function(){
    $('#beginTime').date(undefined,undefined,undefined,function(year, month){
        cur = 0;
        $.ajax({
            type: 'POST',
            url: basepath + 'extract/listmore',
            dataType: 'json',
            data:{time:(year + "-" + month), cur:(+cur) + 1},
            success: function(data){
                var _contain = $("#divall");
                if(data.isLast == true){
                    $(".dropload-down").remove();
                }
                if(data.resCode == "fail"){
                    _contain.empty();
                    alert(data.resMsg);
                } else if(data.resCode == "success"){
                    _contain.empty();
                    for(var i = 0; data.resData.length; i++){
                        _contain.append($("<div><p>" +
                            "<span class='sd'>" + data.resData[i].applyTimeView + "</span>" +
                            "<span>-" + data.resData[i].extractFeeView + "</span></p><h1>" +
                            "<span>银行卡号</span><span>" + data.resData[i].bankCardView + "</span>" +
                            "<b>" + data.resData[i].auditTypeView + "</b></h1></div>"));
                    }
                }
            },
            error: function(xhr, type){
                alert("网络错误");
            }
        });
    });
    $('#endTime').date({theme:"datetime"});

    $('body').dropload({
        scrollArea : window,
        loadDownFn : function(me){
            $.ajax({
                type: 'POST',
                url: basepath + 'extract/listmore',
                dataType: 'json',
                data:{time:$("#beginTime").val(), cur:(+cur) + 1},
                success: function(data){
                    if(data.isLast == true){
                        me.noData();
                        $(".dropload-down").remove();
                    }
                    if(data.resCode == "fail"){
                        alert(data.resMsg);
                    } else if(data.resCode == "success"){
                        var _contain = $("#divall");
                        for(var i = 0; data.resData.length; i++){
                            _contain.append($("<div><p>" +
                                "<span class='sd'>" + data.resData[i].applyTimeView + "</span>" +
                                "<span>-" + data.resData[i].extractFeeView + "</span></p><h1>" +
                                "<span>银行卡号</span><span>" + data.resData[i].bankCardView + "</span>" +
                                "<b>" + data.resData[i].auditTypeView + "</b></h1></div>"));
                        }
                    }
                },
                error: function(xhr, type){
                    alert("网络错误");
                },
                complete:function(){
                    me.resetload();
                }
            });
        },
        domDown:{
            domClass : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
            domUpdate : '<div class="dropload-update">↓释放加载</div>',
            domLoad : '<div class="dropload-load">加载中…</div>',
            domNoData:'<div class="dropload-noData">暂无更多数据</div>'
        }
    });
});