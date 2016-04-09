$(function(){
    $(".j_qu").click(function(){
        alert("1111");
        $(".back").attr('style', 'display:none');
        $(".back_j").attr('style', 'display:none');
    });
});

/**
 * 用户提现
 */
function withdraw(userId,extractableFee){
    alert(extractableFee);
    if (extractableFee == 0){
        alert("暂无可提现额度");
        $(".back").attr('style', 'display:block');
        $(".back_j").attr('style', 'display:block');
        return;
    }
    //判断该用户是否已经绑定
    $.ajax({
        type:"POST",
        async:true,
        url : basepath+"/sfuser/checkBinding.do",
        data:{userId:userId},
        dataType:"Json",
        success:function(data){
            if(data.isTrue == "false"){
                alert(data.message);
            }else {
                $(".back").attr('style', 'display:block');
                $(".back_j").attr('style', 'display:block');
            }
        },
        error: function(){
            //请求出错处理
            alert("请求出错，请稍后再试");
        }
    });
}