<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广西边海防信息平台</title>
<script type="text/javascript">
    var index_tabs;
    var layout_west_tree;
    var indexTabsMenu;
    var baseDataGrid;
    $(function() {
        $('#index_layout').layout({fit : true});
        baseDataGrid = $('#baseDataGrid').datagrid({
        	url: '${path}/positionMark/getBaseInfo',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            frozenColumns: [[{
                width: '4%',
                title: '编号',
                field: 'id',
                sortable: true
            }, {
                width: '6%',
                title: '上报人',
                field: 'userName',
                sortable: true
            }, {
                width: '10%',
                title: '标题',
                field: 'name',
                sortable: true
            }, 
            {
                width: '12%',
                title: '报送时间',
                field: 'createTime',
                sortable: true
            },
            {
                width: '28%',
                title: '报送内容',
                field: 'info',
                sortable: true
            },
            {
                width: '10%',
                title: '报送信息类型',
                field: 'type',
                sortable: true,
                formatter: function (value, row, index) {
                	if(row.data==1){
                		return "界碑";
                	}else{
                    //报送信息类型：0-基础设施；1-涉外时间；2-走私事件；3-突发事件
                    switch (value) {
                        case 0:
                            return '基础设施';
                        case 1:
                            return '涉外事件';
                        case 2:
                            return '走私事件';
                        case 3:
                            return '突发事件';
                    }
                	}
                }
            },
            {
                width: '10%',
                title: '紧急程度',
                field: 'lvlel',
                sortable: true,
                formatter: function (value, row, index) {
                	if(row.data==1){
                		return "";
                	}else{
                    switch (value) {
                        case 0:
                            return '一般';
                        case 1:
                            return '紧急';
                        case 2:
                            return '特急';
                       
                    }
                	}
                }
            }
            , {
                width: '10%',
                title: '信息状态',
                field: 'infoStatus',
                sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '未查看';
                        case 1:
                            return '已查看';
                        case 2:
                            return '已审核';
                        case 3:
                            return '未审核';
                    }
                }
            }, {
                field: 'action',
                title: '操作',
                width: '10%',
                formatter: function (value, row, index) {
                    var str = '';
                    str = $.formatString('<a href="javascript:void(0)" class="base-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="baseEditFun(\'{0}\',\'{1}\');" >审核</a>', row.id,row.data);
                    if (str == '') {
                        str = '当前无权限';
                    }
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.base-easyui-linkbutton-edit').linkbutton({text: '审核'});
            },
            toolbar: '#baseToolbar'
    });
        
        index_tabs = $('#index_tabs').tabs({
            fit : true,
            border : false,
            onContextMenu : function(e, title) {
                e.preventDefault();
                indexTabsMenu.menu('show', {
                    left : e.pageX,
                    top : e.pageY
                }).data('tabTitle', title);
            },
            tools : [{
                iconCls : 'fi-home',
                handler : function() {
                    index_tabs.tabs('select', 0);
                }
            }, {
                iconCls : 'fi-loop',
                handler : function() {
                    refreshTab();
                }
            }, {
                iconCls : 'fi-x',
                handler : function() {
                    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                    var tab = index_tabs.tabs('getTab', index);
                    if (tab.panel('options').closable) {
                        index_tabs.tabs('close', index);
                    }
                }
            } ]
        });
        // 选项卡菜单
        indexTabsMenu = $('#tabsMenu').menu({
            onClick : function(item) {
                var curTabTitle = $(this).data('tabTitle');
                var type = $(item.target).attr('type');
                if (type === 'refresh') {
                    refreshTab();
                    return;
                }
                if (type === 'close') {
                    var t = index_tabs.tabs('getTab', curTabTitle);
                    if (t.panel('options').closable) {
                        index_tabs.tabs('close', curTabTitle);
                    }
                    return;
                }
                var allTabs = index_tabs.tabs('tabs');
                var closeTabsTitle = [];
                $.each(allTabs, function() {
                    var opt = $(this).panel('options');
                    if (opt.closable && opt.title != curTabTitle
                            && type === 'closeOther') {
                        closeTabsTitle.push(opt.title);
                    } else if (opt.closable && type === 'closeAll') {
                        closeTabsTitle.push(opt.title);
                    }
                });
                for ( var i = 0; i < closeTabsTitle.length; i++) {
                    index_tabs.tabs('close', closeTabsTitle[i]);
                }
            }
        });
        
        layout_west_tree = $('#layout_west_tree').tree({
            url : '${path }/resource/tree',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                var opts = {
                    title : node.text,
                    border : false,
                    closable : true,
                    fit : true,
                    iconCls : node.iconCls
                };
                var url = node.attributes;
                if (url && url.indexOf("http") == -1) {
                    url = '${path }' + url;
                }
                if (node.openMode == 'iframe') {
                    opts.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
                    addTab(opts);
                } else if (url) {
                    opts.href = url;
                    addTab(opts);
                }
            }
        });
    });

    function addTab(opts) {
        var t = $('#index_tabs');
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
        }
    }
    
    function refreshTab() {
        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
        var tab = index_tabs.tabs('getTab', index);
        var options = tab.panel('options');
        if (options.content) {
            index_tabs.tabs('update', {
                tab: tab,
                options: {
                    content: options.content
                }
            });
        } else {
            tab.panel('refresh', options.href);
        }
    }
    
    function logout(){
        $.messager.confirm('提示','确定要退出?',function(r){
            if (r){
                progressLoad();
                $.post('${path }/logout', function(result) {
                    if(result.success){
                        progressClose();
                        window.location.href='${path }';
                    }
                }, 'json');
            }
        });
    }
    /**
     * 审核
     */

	
	function baseEditFun(id, data) {
		if (id == undefined) {
			var rows = baseDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			baseDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}

		if (data == 1) {
			parent.$.modalDialog({
				title : '审核',
				width : 700,
				height : 600,
				href : '${path}/positionMark/editPage?id=' + id,
				buttons : [ {
					text : '确定',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = baseDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#positionMarkEditForm');
						f.submit();
					}
				} ]
			});
		} else {
			parent.$.modalDialog({
				title : '审核',
				width : 700,
				height : 600,
				href : '${path}/sendInfo/verify?id=' + id,
				buttons : [ {
					text : '确定',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = baseDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#sendInfoEditForm');
						f.submit();
					}
				} ]
			});
		}
	}
	function editUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${path }/user/editPwdPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var f = parent.$.modalDialog.handler
							.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}
</script>
</head>
<body>
    <div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
        <img src="${staticPath }/static/style/images/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
    </div>
    <div id="index_layout">
        <div data-options="region:'north',border:false" style="overflow: hidden;">
            <div>
                <span style="float: right; padding-right: 20px; margin-top: 15px; color: #333">
                    <i class="fi-torso"></i>
                    <b><shiro:principal></shiro:principal></b>&nbsp;&nbsp; 
                    <shiro:hasPermission name="/user/editPwdPage">
                        <a href="javascript:void(0)" onclick="editUserPwd()" class="easyui-linkbutton" plain="true" icon="fi-unlock" >修改密码</a>
                    </shiro:hasPermission>&nbsp;&nbsp;
                    <a href="javascript:void(0)" onclick="logout()" class="easyui-linkbutton" plain="true" icon="fi-x">安全退出</a>
                </span>
                <img src="${staticPath }/static/style/images/u4.png" style="float: left; height: 50px;">
                <span class="header">广西边海防信息平台</span>
            </div>
        </div>
        <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden;overflow-y:auto; padding:0px">
            <div class="well well-small" style="padding: 5px 5px 5px 5px;">
                <ul id="layout_west_tree"></ul>
            </div>
        </div>
        <div data-options="region:'center'" style="overflow: hidden;">
            <div id="index_tabs" style="overflow: hidden;">
                <div title="首页" data-options="iconCls:'fi-home',border:false" style="overflow: hidden;">
	                <div class="easyui-layout" data-options="fit:true,border:false">
					    <div data-options="region:'center',border:false">
					        <table id="baseDataGrid" data-options="fit:true,border:false"></table>
					    </div>
					</div>
                </div>
            </div>
        </div>
        <div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >Copyright © 2017 power by <a href="http://www.sirbox.cn" target="_blank">sirbox.cn</a></div>
    </div>
    <div id="tabsMenu">
        <div data-options="iconCls:'fi-loop'" type="refresh" style="font-size: 12px;">刷新</div>
        <div class="menu-sep"></div>
        <div data-options="iconCls:'fi-x'" type="close" style="font-size: 12px;">关闭</div>
        <div data-options="iconCls:''" type="closeOther">关闭其他</div>
        <div data-options="iconCls:''" type="closeAll">关闭所有</div>
    </div>
    
    <!--[if lte IE 7]>
    <div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
    <![endif]-->
    <style>
        /*ie6提示*/
        #ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
        #ie6-warning p{width:960px;margin:0 auto;}
    </style>
</body>
</html>