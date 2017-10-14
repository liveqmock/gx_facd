<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var appVersionDataGrid;
    $(function() {
        appVersionDataGrid = $('#appVersionDataGrid').datagrid({
        url : '${path}/appVersion/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        idField : 'id',
        sortName : 'id',
        sortOrder : 'asc',
        pageSize : 20,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
        frozenColumns : [ [ {
            width : '60',
            title : '编号',
            field : 'id',
            sortable : true,
            hidden : true
        }, {
            width : '150',
            title : '名称',
            field : 'name',
            sortable : true
        }, {
            width : '60',
            title : '版本',
            field : 'version',
            sortable : true
        }, {
            width : '140',
            title : '类型',
            field : 'type',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 1:
                    return '移动端';
                case 2:
                    return '电视端';
                case 3:
                    return '移动端插件';
                case 4:
                    return '电视端插件';
                }
            }
        }, {
            width : '60',
            title : '状态',
            field : 'status',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 0:
                    return '未启用';
                case 1:
                    return '启用';
                case 2:
                    return '停用';
                }
            }
        }, {
            width : '140',
            title : '创建时间',
            field : 'createTime',
            sortable : true
        }, {
            field : 'action',
            title : '操作',
            width : 200,
            formatter : function(value, row, index) {
                var str = $.formatString('<a href="javascript:void(0)" class="appVersion-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="appVersionEditFun(\'{0}\');" >编辑</a>', row.id);
   	                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="appVersion-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="appVersionDeleteFun(\'{0}\');" >删除</a>', row.id);
                if (row.status!=1) {
   	                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="appVersion-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'fi-check icon-red\'" onclick="appVersionopenFun(\'{0}\');" >启用</a>', row.id);
				}else {
   	                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="appVersion-easyui-linkbutton-del1" data-options="plain:true,iconCls:\'fi-x-circle icon-red\'" onclick="appVersionstopFun(\'{0}\');" >停用</a>', row.id);
				}
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.appVersion-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.appVersion-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.appVersion-easyui-linkbutton-del1').linkbutton({text:'停用'});
            $('.appVersion-easyui-linkbutton-ok').linkbutton({text:'启用'});
        },
        toolbar : '#appVersionToolbar'
    });
});

/**
 * 添加框
 * @param url
 */
function appVersionAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/appVersion/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = appVersionDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#appVersionAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function appVersionEditFun(id) {
    if (id == undefined) {
        var rows = appVersionDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        appVersionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/appVersion/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = appVersionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#appVersionEditForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 删除
 */
 function appVersionDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = appVersionDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         appVersionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前app版本？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/appVersion/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     appVersionDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
 function appVersionstopFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = appVersionDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         appVersionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要停用当前版本？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/appVersion/stop', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     appVersionDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
 function appVersionopenFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = appVersionDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         appVersionDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要启用当前版本？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/appVersion/start', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     appVersionDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}


/**
 * 清除
 */
function appVersionCleanFun() {
    $('#appVersionSearchForm input').val('');
    appVersionDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function appVersionSearchFun() {
     appVersionDataGrid.datagrid('load', $.serializeObject($('#appVersionSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="appVersionSearchForm">
            <table>
                <tr>
                    <th>名称:</th>
                    <td><input name="name" placeholder="搜索条件"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="appVersionSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="appVersionCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="appVersionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="appVersionToolbar" style="display: none;">
        <a onclick="appVersionAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>