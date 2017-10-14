<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var dataDictionaryTreeGrid;
    $(function() {
        dataDictionaryTreeGrid = $('#dataDictionaryTreeGrid').treegrid({
            url : '${path }/dataDictionary/treeGrid',
            idField : 'id',
            treeField : 'name',
            parentField : 'pid',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : '编号',
                field : 'id',
                width : 40
            } ] ],
            columns : [ [ {
                field : 'name',
                title : '资源名称',
                width : 150
            }, {
                field : 'code',
                title : '类型编码',
                width : 100
            }, {
                field : 'seq',
                title : '排序',
                width : 40
            }, {
                field : 'remake',
                title : '备注',
                width : 250
            }, {
                field : 'pid',
                title : '上级资源ID',
                width : 150,
                hidden : true
            }, {
                field : 'status',
                title : '状态',
                width : 40,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '正常';
                    case 1:
                        return '停用';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="dataDictionary-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editdataDictionaryFun(\'{0}\');" >编辑</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="dataDictionary-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletedataDictionaryFun(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
            	 $(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
                 $(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed"); 
            
                $('.dataDictionary-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.dataDictionary-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#dataDictionaryToolbar'
        });
    });
  
    function editdataDictionaryFun(id) {
        if (id != undefined) {
            dataDictionaryTreeGrid.treegrid('select', id);
        }
        var node = dataDictionaryTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title : '编辑',
                width : 500,
                height : 350,
                href : '${path }/dataDictionary/editPage?id=' + node.id,
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = dataDictionaryTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#dataDictionaryEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }

    function deletedataDictionaryFun(id) {
        if (id != undefined) {
            dataDictionaryTreeGrid.treegrid('select', id);
        }
        var node = dataDictionaryTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
                if (b) {
                    progressLoad();
                    $.post('${path }/dataDictionary/delete', {
                        id : node.id
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataDictionaryTreeGrid.treegrid('reload');
                            parent.layout_west_tree.tree('reload');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }

    function adddataDictionaryFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 350,
            href : '${path }/dataDictionary/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = dataDictionaryTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#dataDictionaryAddForm');
                    f.submit();
                }
            } ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="dataDictionaryTreeGrid"></table>
    </div>
</div>
<div id="dataDictionaryToolbar" style="display: none;">
        <a onclick="adddataDictionaryFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
</div>