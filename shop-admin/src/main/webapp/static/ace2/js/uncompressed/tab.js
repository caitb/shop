jQuery.fn.extend({
    keys: ['home', 'messages'],
    tabParent: function(){
        return this.find('.nav.nav-tabs');
    },
    tabContentParent: function(){
        return this.find('.tab-content');
    },
    add: function(key, title, url){
        if(arguments.length != 3){
            window.console.error('参数不对!');
            return;
        }

        this.tabParent().children().removeClass('active');
        this.tabContentParent().children().removeClass('active in');

        //如果已存在key选项卡,则直接显示
        //for(var k in this.keys){
        //    if(key == this.keys[k]){
        //
        //        this.tabParent().children('[key='+key+']').addClass('active');
        //        this.tabContentParent().children('[key='+key+']').addClass('active in');
        //        return;
        //    }
        //}

        //如果不存在选项卡,则添加
        var tab =         '<li class="active" key="' + key + '">'                      +
                             '<a href="#' + key + '" data-toggle="tab">'               +
                                '<i class="green ace-icon fa fa-home bigger-120"></i>' +
                                 title                                                 +
                             '</a>'                                                    +
                            '<i class="red ace-icon fa fa-times end"></i>'
                          '</li>';

        var tabContent =  '<div class="tab-pane fade active in" id="' + key + '" key="' + key + '">' +
                             '<iframe src="' + url + '" scrolling="auto" frameborder="0"></iframe>'  +
                          '</div>';

        this.tabParent().append(tab);
        this.tabContentParent().append(tabContent);
        this.keys.push(key);

        this.tabContentParent().height($(window).height()-221);
        this.tabContentParent().find('iframe').css('width', ($('.tab-content').width()) + 'px');
        this.tabContentParent().find('iframe').css('height', ($('.tab-content').height()) + 'px');
    }
});