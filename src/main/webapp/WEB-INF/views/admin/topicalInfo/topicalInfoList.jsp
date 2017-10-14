<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var topicalInfoDataGrid;
    $(function() {
        topicalInfoDataGrid = $('#topicalInfoDataGrid').datagrid({
        url : '${path}/topicalInfo/dataGrid',
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
            width : '35%',
            title : '标题',
            field : 'title',
            sortable : true
        },
        {
            width : '60',
            title : '创建人',
            field : 'name',
            sortable : true
        },
        {
            width : '140',
            title : '创建时间',
            field : 'createTime',
            sortable : true
        },
        {
            width : '60',
            title : '状态',
            field : 'status',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 0:
                    return '未发布';
                case 1:
                    return '已发布';
                }
            }
        }, {
            field : 'action',
            title : '操作',
            width : 280,
            formatter : function(value, row, index) {
                var str = '';
                  
                    if(row.status==0){
                    str += $.formatString('<a href="javascript:void(0)" class="topicalInfo-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="topicalInfoEditFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                      str += $.formatString('<a href="javascript:void(0)" class="topicalInfo-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-red\'" onclick="topicalInfocheckFun(\'{0}\');" >发布</a>', row.id);
                     str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                     str += $.formatString('<a href="javascript:void(0)" class="topicalInfo-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="topicalInfoDeleteFun(\'{0}\');" >删除</a>', row.id);
                    }else{
                    	   str += $.formatString('<a href="javascript:void(0)" class="topicalInfo-easyui-linkbutton-detail" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="getDetailFun(\'{0}\');" >查看</a>', row.id);
                          str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                          str += $.formatString('<a href="javascript:void(0)" class="topicalInfo-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="topicalInfoDeleteFun(\'{0}\');" >删除</a>', row.id);
                    }
                    
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.topicalInfo-easyui-linkbutton-detail').linkbutton({text:'查看'});
            $('.topicalInfo-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.topicalInfo-easyui-linkbutton-check').linkbutton({text:'发布'});
            $('.topicalInfo-easyui-linkbutton-del').linkbutton({text:'删除'});
        },
        toolbar : '#topicalInfoToolbar'
    });
});

    
    
    /**
     * 查看
     */
    function getDetailFun(id) {
        if (id == undefined) {
            var rows = topicalInfoDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
        	topicalInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '查看',
            width : 700,
            height : 600,
            href :  '${path}/topicalInfo/detail?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = topicalInfoDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }
            } ]
        });
    }
    
    /**
     * 发布
     */
    function topicalInfocheckFun(id) {
         if (id == undefined) {//点击右键菜单才会触发这个
             var rows = topicalInfoDataGrid.datagrid('getSelections');
             id = rows[0].id;
         } else {//点击操作里面的删除图标会触发这个
        	 topicalInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
         }
         parent.$.messager.confirm('询问', '您是否要发布当前信息？', function(b) {
             if (b) {
                 progressLoad();
                 $.post('${path}/topicalInfo/check', {
                     id : id
                 }, function(result) {
                     if (result.success) {
                         parent.$.messager.alert('提示', result.msg, 'info');
                         topicalInfoDataGrid.datagrid('reload');
                     }
                     progressClose();
                 }, 'JSON');
             }
         });
    }
/**
 * 添加框
 * @param url
 */
function topicalInfoAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/topicalInfo/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = topicalInfoDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#topicalInfoAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function topicalInfoEditFun(id) {
    if (id == undefined) {
        var rows = topicalInfoDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        topicalInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/topicalInfo/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = topicalInfoDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#topicalInfoEditForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 删除
 */
 function topicalInfoDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = topicalInfoDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         topicalInfoDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除这条信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/topicalInfo/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     topicalInfoDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}


/**
 * 清除
 */
function topicalInfoCleanFun() {
    $('#topicalInfoSearchForm input').val('');
    topicalInfoDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function topicalInfoSearchFun() {
     topicalInfoDataGrid.datagrid('load', $.serializeObject($('#topicalInfoSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="topicalInfoSearchForm">
            <table>
                <tr>
                    <th>标题:</th>
                    <td><input name="title" type="text" placeholder="搜索条件"/></td>
                    <th>创建人:</th>
                    <td><input name="createUser" type="text" placeholder="搜索条件"/></td>
                    <th>创建时间:</th>
                    <td colspan="3" >
                        <input name="startDate" id="startDate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="endDate" id = "endDate"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="topicalInfoSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="topicalInfoCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 <div id="topicalInfoToolbar" style="display: none;">
        <a onclick="topicalInfoAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>
    <div data-options="region:'center',border:false">
        <table id="topicalInfoDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<!-- <div id="topicalInfoToolbar" style="display: none;">
    <shiro:hasPermission name="/topicalInfo/add">
        <a onclick="topicalInfoAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
    </shiro:hasPermission>
</div>-->