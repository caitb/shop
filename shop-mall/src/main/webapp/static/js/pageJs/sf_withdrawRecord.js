$(function(){
    $('#beginTime').date(undefined,undefined,undefined,function(year, month){
        getExtractApplyList(year,month)
    });
    $('#endTime').date({theme:"datetime"});
});
$('#divall').dropload({
    scrollArea : window,
    loadDownFn : function(me){
        $.ajax({
            type: 'GET',
            url: 'json/more.json',
            dataType: 'json',
            success: function(data){
                // 代码执行后必须重置
                me.resetload();
            },
            error: function(xhr, type){
                me.noData();
            }
        });
    }
});
function getMore(){
    var year = $("#year").val();
    var month = $("#month").val();
    getExtractApplyList(year,month);
}
function getExtractApplyList(year,month){
    var yearLast = $("#year").val();
    var monthLast = $("#month").val();
    if(yearLast==year && monthLast==month){
        var currentPage = $("#currentPage").val();
        var totalPage = $("#totalPage").val();
        if (currentPage >= totalPage){
            alert("已经加载全部数据");
        }else {
            $.ajax({
                type:"POST",
                async:true,
                url : basepath + "withdraw/ajaxExtractApplyList.do",
                data:{year:year, month:month, currentPage:currentPage + 1, pageSize:20},
                dataType:"Json",
                success:function(data){
                    var arr=eval(data);
                    for(var i=0;i<arr.length;i++)
                    {
                        $("#divall").append("<div><p><span class='sd'>"+month+"-"+arr[i].date+"</span><span>"+year+"</span><span>+"+arr[i].extractFee+"</span></p><h1><span>微信提现</span><span>"+arr[i].status+"</span></h1></div>");
                    }
                    $("#currentPage").val(currentPage + 1);
                },
                error: function(){
                    //请求出错处理
                    alert("请求出错，请稍后再试");
                }
            });
        }
    }else {
        $("#year").val(year);
        $("#month").val(month);
        $.ajax({
            type:"POST",
            url : basepath + "withdraw/ajaxExtractApplyList.do",
            data:{year:year,month:month,currentPage:1,pageSize:20},
            dataType:"Json",
            success:function(data){
                $("#divall").empty();
                var totalPage = 0;
                var arr=eval(data);
                for(var i=0;i<arr.length;i++)
                {
                    $("#divall").append("<div><p><span class='sd'>"+month+"-"+arr[i].date+"</span><span>"+year+"</span><span>+"+arr[i].extractFee+"</span></p><h1><span>微信提现</span><span>"+arr[i].status+"</span></h1></div>");
                    totalPage = arr[i].totalPage;
                }
                $("#totalPage").val(totalPage);
                $("#currentPage").val(currentPage);
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
}