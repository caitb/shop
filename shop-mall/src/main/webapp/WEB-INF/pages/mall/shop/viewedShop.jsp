<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/liulanguodedianpu.css">
    <script type="application/javascript">
        var path = "${path}";
        var basepath = "${basePath}";
    </script>
</head>
<body>
    <header>
              <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                <p>浏览过的店铺</p>            
        </header>
        <div class="wrap">
                <section class="sec1">
                    <p class="photo">
                                   <img src="${path}/static/images/haohuo.png" alt="">
                    </p>
                    <div>
                        <h2>王平的小店</h2>
                        <h1>
                            <img src="../images/a.png" alt="">
                            <img src="../images/b.png" alt="">
                            <img src="../images/c.png" alt="">
                            10000保证金
                        </h1>
                        <h3>主营各类化妆品，保健品等等等等等</h3>
                        <h2><span>7</span>天前浏览过<b>点击查看></b></h2>
                    </div>
                </section>
        </div>
</body>
</html>