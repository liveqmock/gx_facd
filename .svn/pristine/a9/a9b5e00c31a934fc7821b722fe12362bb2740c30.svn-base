<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var scoreDataGrid;
    $(function() {
        scoreDataGrid = $('#scoreDataGrid').datagrid({
        url : '${path}/score/dataGrid',
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
        }, 
        {
            width : '140',
            title : '考试标题',
            field : 'title',
            sortable : true
        }, {
            field : 'action1',
            title : '试卷内容',
            width : 200,
            formatter : function(value, row, index) {
                var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="score-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="scoreinfo(\'{0}\');" >试卷内容</a>', row.id);
                return str;
            }
        } , {
            field : 'action2',
            title : '参考人数',
            width : 200,
            formatter : function(value, row, index) {
                var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="score-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="scoreuser(\'{0}\');" >'+row.total+'</a>', row.id);
                return str;
            }
        }, 
        {
            width : '140',
            title : '通过人数',
            field : 'pass',
            sortable : true
        }, 
        {
            width : '140',
            title : '未通过人数',
            field : 'npass',
            sortable : true
        }] ],
        toolbar : '#scoreToolbar'
    });
});

/**
 * 添加框
 * @param url
 */
function scoreAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/score/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = scoreDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#scoreAddForm');
                f.submit();
            }
        } ]
    });
}



/**
 * 查看
 */
function scoreuser(id) {
    if (id == undefined) {
        var rows = scoreDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        scoreDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/score/seeuser?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = scoreDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}
/**
 * 查看
 */
function scoreinfo(id) {
    if (id == undefined) {
        var rows = scoreDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        scoreDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/score/seeinfo?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = scoreDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}

/**
 * 清除
 */
function scoreCleanFun() {
    $('#scoreSearchForm input').val('');
    scoreDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function scoreSearchFun() {
     scoreDataGrid.datagrid('load', $.serializeObject($('#scoreSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="scoreSearchForm">
            <table>
                <tr>
                    <th>标题:</th>
                    <td><input name="title" placeholder="搜索条件"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="scoreSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="scoreCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="scoreDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
