(function () {
  $.definedAlertWindow = {
    alert: function (msg) {
      generateHtml(msg);
      loadCssFile();
    }
  }
  //生成Html
  var generateHtml = function (msg) {
    var _html = "";
    _html += '<div class="alert"><h1><img src="http://'+ window.location.host+'/static/images/alert.png" alt="">提示</h1>';
    _html += '<h2>' + msg + '</h2></div>';
    $("body").append(_html);
      hideWindow();
  }
  var hideWindow = function(){
        $('.alert').fadeIn("slow").delay(1000).fadeOut("slow");
      }
  var loadCssFile = function(){
        $("<link>").attr({
          rel:"stylesheet",
          type:"text/css",
          href:"http://"+window.location.host+"/static/css/alert.css"
        }).appendTo("head");
  }
})();