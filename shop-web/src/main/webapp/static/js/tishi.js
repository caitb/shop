$("body").on("click",function(){
            $(".gao").hide();
        })
         $(".onc").eq(0).on("click",function(event){
             var event=event||event.window;
             event.stopPropagation();
            $(this).next().show().html("姓名格式不正确");
         })
         $(".onc").eq(1).on("click",function(event){
             var event=event||event.window;
             event.stopPropagation();
            $(this).next().show().html("手机号输入错误");
         })
         $(".tap").on("click",function(event){
             var event=event||event.window;
             event.stopPropagation();
            $(this).next().show().html("手机号输入错误");
         })
         $(".xin").on("click",function(event){
             var event=event||event.window;
             event.stopPropagation();
            $(this).next().show();
         })