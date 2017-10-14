<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var useHelpDataGrid;
    $(function() {
        useHelpDataGrid = $('#useHelpDataGrid').datagrid({
        url : '${path}/useHelp/dataGrid',
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
            sortable : true
        }, {
            width : '140',
            title : '标题',
            field : 'title',
            sortable : true
        }, {
            width : '60',
            title : '状态',
            field : 'status',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 2:
                    return '删除';
                case 0:
                    return '草稿';
                case 1:
                    return '发布';
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
                var str = '';
                if (row.status==0) {
                    str += $.formatString('<a href="javascript:void(0)" class="useHelp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="useHelpEditFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="useHelp-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="useHelpDeleteFun(\'{0}\');" >删除</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="useHelp-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-red\'" onclick="useHelpcheckFun(\'{0}\');" >发布</a>', row.id);
				}else{
					str += $.formatString('<a href="javascript:void(0)" class="useHelp-easyui-linkbutton-see" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="useHelpseeFun(\'{0}\');" >查看</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" class="useHelp-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="useHelpDeleteFun(\'{0}\');" >删除</a>', row.id);
				}
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.useHelp-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.useHelp-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.useHelp-easyui-linkbutton-see').linkbutton({text:'查看'});
            $('.useHelp-easyui-linkbutton-check').linkbutton({text:'发布'});
        },
        toolbar : '#useHelpToolbar'
    });
});

/**
 * 添加框
 * @param url
 */
function useHelpAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/useHelp/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = useHelpDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#useHelpAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function useHelpEditFun(id) {
    if (id == undefined) {
        var rows = useHelpDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        useHelpDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/useHelp/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = useHelpDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#useHelpEditForm');
                f.submit();
            }
        } ]
    });
}
/**
 * 查看
 */
function useHelpseeFun(id) {
    if (id == undefined) {
        var rows = useHelpDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        useHelpDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/useHelp/see?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = useHelpDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}


/**
 * 删除
 */
 function useHelpDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = useHelpDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         useHelpDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/useHelp/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     useHelpDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
/**
 * 发布
 */
function useHelpcheckFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = useHelpDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         useHelpDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要发布当前信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/useHelp/check', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     useHelpDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}


/**
 * 清除
 */
function useHelpCleanFun() {
    $('#useHelpSearchForm input').val('');
    useHelpDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function useHelpSearchFun() {
     useHelpDataGrid.datagrid('load', $.serializeObject($('#useHelpSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="useHelpSearchForm">
            <table>
                <tr>
                    <th>标题:</th>
                    <td><input name="title" placeholder="搜索条件"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="useHelpSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="useHelpCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="useHelpDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="useHelpToolbar" style="display: none;">
        <a onclick="useHelpAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>