var CommonTreePanel = function(init_params){
    if(init_params == undefined) init_params = {};
    var p = init_params;
    this.panelId    =  p.panelId || 'common_tree_panel';
    this.idKey      =  p.idKey || 'id';
    this.textKey    =  p.textKey || 'name';
    this.pidKey     =  p.pidKey || 'pid';
    this.hrefKey    =  p.hrefKey || 'href';
    this.targetKey  =  p.targetKey || 'target';
    this.targetDefaultValue = p.targetDefaultValue || '_blank';
    this.showRoot   =  p.showRoot || false;
    this.rootText   =  p.rootText || 'Root';
    this.rootValue  =  p.rootValue || '';
    this.checkbox   =  p.checkbox || false;
    this.removeMetaParamData = p.removeMetaParamData == undefined ? true : (p.removeMetaParamData ? true : false);
    this.defaultFirstLevelState =  p.defaultFirstLevelState || 'open';
    this.isAutoLink =  p.isAutoLink == undefined ? true : (p.isAutoLink ? true : false);
    this.isAllLink  =  p.isAllLink || false;
    this.splitStr   =  p.splitStr || ',';
    this.excludeId  = p.excludeId || '';
    this.nodeClick  = p.nodeClick || null;
    this.nodeLinkBaseUrl = p.nodeLinkBaseUrl || '';
    this.treeDom = null;
    this.init_params = init_params;
}

/***
 * @param params
 * method: 'GET' or 'POST',default 'GET'
 * url
 * data : request form datas
 * dataType : response data type,default 'json'
 * jsonRoot
 * nodataFound
 *
 * valueDomId : node value
 * defValue  :  default select one value
 */
CommonTreePanel.prototype.request = function(params){
    var me = this;

    //请求原始数据
    $.ajax({
        type    : params.method || 'GET',
        url     : params.url,
        data    : params.data,
        dataType: params.dataType || 'json',
        success : function(data){
            var v = null;
            if(data){
                if(params.jsonRoot){
                    v = data[params.jsonRoot];
                }else{
                    v = data;
                }
                me._treedata_gen(v, params);
            }else{
                if(params.nodataFound){
                    params.nodataFound.call(me, params, data);
                }
            }
        },
        error: function(xhr, status, errorThrown) {
            if(params.error){
                params.error.call(me, xhr, status, errorThrown);
            }
        }
    });
};

//显示静态数据
CommonTreePanel.prototype.show = function(data, params){
    this._treedata_gen(data, params);
};

//获取tree dom
CommonTreePanel.prototype.getTreeDom = function(){
    return this.treeDom;
};

//获取checked data, return arr
CommonTreePanel.prototype.getChecked = function(){
    if(this.treeDom == null) return [];
    return $(this.treeDom).tree('getChecked');
};

CommonTreePanel.prototype.getCheckedIdArray = function(){
    var ids = [];
    var nodes = this.getChecked();
    if(nodes.length){
        for(var i in nodes){
            ids.push(nodes[i].id);
        }
    }
    return ids;
};

CommonTreePanel.prototype.getCheckedIdString = function(){
    var ids = this.getCheckedIdArray();
    return ids.join(this.splitStr);
};

CommonTreePanel.prototype.getCheckedTextArray = function(){
    var texts = [];
    var nodes = this.getChecked();
    if(nodes.length){
        for(var i in nodes){
            texts.push(nodes[i].text);
        }
    }
    return texts;
};

CommonTreePanel.prototype.getCheckedTextString = function(){
    var texts = this.getCheckedTextArray();
    return texts.join(this.splitStr);
};


CommonTreePanel.prototype.getCheckedIdArrayIncludeParent = function(){
    if(this.treeDom == null) return [];
    var nodes = $(this.treeDom).tree('getChecked', 'checked') || [];
    var indeterminates =  $(this.treeDom).tree('getChecked', 'indeterminate') || [];
    if(indeterminates.length){
        nodes = nodes.concat(indeterminates);
    }
    var ids = [];
    if(nodes.length){
        for(var i in nodes){
            ids.push(nodes[i].id);
        }
    }
    return ids;
};

CommonTreePanel.prototype.getCheckedIdStringIncludeParent = function(){
    var ids = this.getCheckedIdArrayIncludeParent();
    return ids.join(this.splitStr);
};

//获取selected data, return object
CommonTreePanel.prototype.getSelected = function(){
    if(this.treeDom == null) return null;
    return $(this.treeDom).tree('getSelected');
};

CommonTreePanel.prototype.getSelectedId = function(){
    var node = this.getSelected();
    if(node){
        return node.id;
    }
    return '';
};

CommonTreePanel.prototype.getNode = function(nodeId){
    if(!this.treeDom) return null;
    return $(this.treeDom).tree('find',nodeId);
};

CommonTreePanel.prototype.isLeaf = function(nodeObjOrId){
    if(typeof nodeObjOrId == 'string'){
        return $(this.treeDom).tree('isLeaf', treePanel.getNode(nodeObjOrId).target);
    }
    return $(this.treeDom).tree('isLeaf',nodeObjOrId.target);
};

//选中节点
CommonTreePanel.prototype.selectNode = function(nodeObjOrId){
    if(typeof nodeObjOrId == 'string'){
        $(this.treeDom).tree('select', this.getNode(nodeObjOrId).target);
    }else{
        $(this.treeDom).tree('select', nodeObjOrId.target);
    }
};

CommonTreePanel.prototype.checkNode = function(nodeObjOrId){
    if(typeof nodeObjOrId == 'string'){
        $(this.treeDom).tree('check', this.getNode(nodeObjOrId).target);
    }else{
        $(this.treeDom).tree('check', nodeObjOrId.target);
    }
};

CommonTreePanel.prototype.uncheckNode = function(nodeObjOrId){
    if(typeof nodeObjOrId == 'string'){
        $(this.treeDom).tree('uncheck', this.getNode(nodeObjOrId).target);
    }else{
        $(this.treeDom).tree('uncheck', nodeObjOrId.target);
    }
};

CommonTreePanel.prototype._treedata_gen = function(data,params){
    var me = this;
    var datas = [];
    var d = null, pid = null;

    //生成一级节点数据
    for(var i in data){
        d = data[i];
        pid = d[me.pidKey];
        if(pid == undefined || pid == null || pid == ''){
            var node = {
                id      : d[me.idKey],
                state   : me.defaultFirstLevelState,
                checked : false
            };
            if(me.isAllLink){
                node.text = '<a href="'+ me.nodeLinkBaseUrl + (d[me.hrefKey] || '') +'" target="'+ (d[me.targetKey] || me.targetDefaultValue) +'">' + d[me.textKey] + '</a>';
            }else{
                if(me.isAutoLink){
                    if(d[me.hrefKey]){
                        node.text = '<a href="'+ me.nodeLinkBaseUrl + (d[me.hrefKey] || '') +'" target="'+ (d[me.targetKey] || me.targetDefaultValue) +'">' + d[me.textKey] + '</a>';
                    }else{
                        node.text = d[me.textKey];
                    }
                }else{
                    node.text = d[me.textKey];
                }
            }
            datas.push(node);

            if(me.removeMetaParamData){
                delete d;
            }
        }
    }

    //生成子节点数据
    for(var i in datas){
        me._childdata_gen(data, datas[i]);
    }

    var treeDatas = null;
    if(me.showRoot){
        treeDatas = [{
            id      : me.rootValue,
            text    : me.rootText,
            children : datas
        }];
    }else{
        treeDatas = datas;
    }

    //生成tree
    me.treeDom = $('#'+me.panelId).tree({
        data: treeDatas,
        checkbox : me.checkbox,
        onClick: function(node){
            if(params.valueDomId){
                $('#'+params.valueDomId).val(node.id);
            }
            if(me.nodeClick){
                me.nodeClick.call(this ,me ,node);
            }
        }
    });

    //默认选中node
    if(params.defValue != undefined){
        var node = me.getNode(params.defValue);
        if(node){
            me.selectNode(node);
        }
    }

    //默认check node
    var initDatas = [];
    if(params.initCheckedValues != undefined){
        initDatas = params.initCheckedValues;
    }else if(params.initCheckedValuesStr){
        initDatas = params.initCheckedValuesStr.split(me.splitStr);
    }
    if(initDatas && initDatas.length){
        var id = null, node = null;
        for(var i in initDatas){
            id = initDatas[i];
            node = me.getNode(id);
            if(node && me.isLeaf(node)){
                me.checkNode(node);
            }
        }

    }
};

CommonTreePanel.prototype._childdata_gen = function(data, obj){
    var me = this;
    var d = null, pid = null;
    for(var i in data){
        d = data[i];
        pid = d[me.pidKey];
        if(pid == obj['id']){
            if(!obj['children']){
                obj['children'] = [];
            }
            var node = {
                id      : d[me.idKey],
                checked : false
            };
            if(me.isAllLink){
                node.text = '<a href="'+ me.nodeLinkBaseUrl + (d[me.hrefKey] || '') +'" target="'+ (d[me.targetKey] || me.targetDefaultValue) +'">' + d[me.textKey] + '</a>';
            }else{
                if(me.isAutoLink){
                    if(d[me.hrefKey]){
                        node.text = '<a href="'+ me.nodeLinkBaseUrl + (d[me.hrefKey] || '') +'" target="'+ (d[me.targetKey] || me.targetDefaultValue) +'">' + d[me.textKey] + '</a>';
                    }else{
                        node.text = d[me.textKey];
                    }
                }else{
                    node.text = d[me.textKey];
                }
            }
            var len = obj['children'].push(node);

            if(me.removeMetaParamData){
                delete d;
            }

            me._childdata_gen(data, obj['children'][len-1]);
        }
    }
};