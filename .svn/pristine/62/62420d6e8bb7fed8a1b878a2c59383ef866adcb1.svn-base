<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var borderKnowledgeDataGrid;
    $(function() {
    	$('#type2').combobox({
			url : '${path }/borderKnowledge/tree',
			multiple : false,
			panelHeight : 'auto',
			  valueField: 'name',  
			  textField: 'text', 
			 value : '${KnowledgeType.text}'
		});
        borderKnowledgeDataGrid = $('#borderKnowledgeDataGrid').datagrid({
        url : '${path}/borderKnowledge/dataGrid',
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
            width : '4%',
            title : '编号',
            field : 'id',
            sortable : true
        }, {
            width : '28%',
            title : '标题',
            field : 'title',
            sortable : true
        },
        {
            width : '8%',
            title : '类型名称',
            field : 'kname',
            sortable : true
        },
        {
            width : '5%',
            title : '状态',
            field : 'status',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 1:
                    return '草稿';
                case 2:
                    return '发布';
                }
            }
        }, {
            width : '5%',
            title : '创建人',
            field : 'name',
            sortable : true
          
        }, {
            width : '11%',
            title : '创建时间',
            field : 'createTime',
            sortable : true
        }, {
            field : 'action',
            title : '操作',
            width : 200,
            formatter : function(value, row, index) {
                var str = '';
                if (row.status==1) {
                    str += $.formatString('<a href="javascript:void(0)" class="borderKnowledge-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="borderKnowledgeEditFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="borderKnowledge-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="borderKnowledgeDeleteFun(\'{0}\');" >删除</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="borderKnowledge-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-red\'" onclick="borderKnowledgecheckFun(\'{0}\');" >发布</a>', row.id);
				}else{
					str += $.formatString('<a href="javascript:void(0)" class="borderKnowledge-easyui-linkbutton-see" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="borderKnowledgeseeFun(\'{0}\');" >查看</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" class="borderKnowledge-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="borderKnowledgeDeleteFun(\'{0}\');" >删除</a>', row.id);
				}
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.borderKnowledge-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.borderKnowledge-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.borderKnowledge-easyui-linkbutton-see').linkbutton({text:'查看'});
            $('.borderKnowledge-easyui-linkbutton-check').linkbutton({text:'发布'});
        },
        toolbar : '#borderKnowledgeToolbar'
    });
});

/**
 * 添加框
 * @param url
 */
function borderKnowledgeAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/borderKnowledge/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = borderKnowledgeDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#borderKnowledgeAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function borderKnowledgeEditFun(id) {
    if (id == undefined) {
        var rows = borderKnowledgeDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        borderKnowledgeDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/borderKnowledge/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = borderKnowledgeDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#borderKnowledgeEditForm');
                f.submit();
            }
        } ]
    });
}
/**
 * 查看
 */
function borderKnowledgeseeFun(id) {
    if (id == undefined) {
        var rows = borderKnowledgeDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        borderKnowledgeDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/borderKnowledge/see?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = borderKnowledgeDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}


/**
 * 删除
 */
 function borderKnowledgeDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = borderKnowledgeDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         borderKnowledgeDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/borderKnowledge/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     borderKnowledgeDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
/**
 * 发布
 */
function borderKnowledgecheckFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = borderKnowledgeDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         borderKnowledgeDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要发布当前信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/borderKnowledge/check', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     borderKnowledgeDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}


/**
 * 清除
 */
function borderKnowledgeCleanFun() {
    $('#borderKnowledgeSearchForm input').val('');
    $('#borderKnowledgeSearchForm select').val('');
    borderKnowledgeDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function borderKnowledgeSearchFun() {
     borderKnowledgeDataGrid.datagrid('load', $.serializeObject($('#borderKnowledgeSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="borderKnowledgeSearchForm" method="post">
            <table>
                <tr>
                    <th>标题:</th>
                    <td><input name="title" placeholder="搜索条件"/></td>
                     <th>类型:</th>
                    <td><select name="name" id="type2" class="easyui-combobox" data-options="width:140,height:29,editable:false,valueField:'id', textField:'text'"></select> </td>
                     <th>创建人:</th>
                    <td><input name="createUser" placeholder="搜索条件"/></td>
                    <th>创建时间:</th>
                    <td colspan="2">
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="borderKnowledgeSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="borderKnowledgeCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="borderKnowledgeDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="borderKnowledgeToolbar" style="display: none;">
        <a onclick="borderKnowledgeAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>