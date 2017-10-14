<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var dailyPatrolDataGrid;
    $(function() {
        dailyPatrolDataGrid = $('#dailyPatrolDataGrid').datagrid({
        url : '${path}/dailyPatrol/dataGrid',
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
            width : '100',
            title : '用户',
            field : 'uname',
            sortable : true,
        }, {
            width : '140',
            title : '路段',
            field : 'rname',
            sortable : true,
        }, {
            width : '140',
            title : '开始时间',
            field : 'startTime',
            sortable : true
        }, {
            width : '140',
            title : '结束时间',
            field : 'endTime',
            sortable : true
        }, {
            width : '60',
            title : '报送数量',
            field : 'reportcount',
            sortable : true
        }, {
            width : '60',
            title : '标记数量',
            field : 'tagcount',
            sortable : true
        }, {
            width : '60',
            title : '总用时',
            field : 'time',
            sortable : true,
            formatter : function(value, row, index) {
                return (value==''?0:value)+"分钟";
            }
        }, {
            width : '60',
            title : '状态',
            field : 'endStatus',
            sortable : true,
            formatter : function(value, row, index) {
                switch(value){
                case 0:return "巡防开始";
                case 1:return "正常结束";
                case 2:return "异常结束";
                }
            }
        }, {
            field : 'action',
            title : '操作',
            width : 220,
            formatter : function(value, row, index) {
                var str = '';
                if (row.endStatus==0) {
                	str += $.formatString('<a href="javascript:void(0)" class="dailyPatrol-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-save   icon-blue\'" onclick="dailyPatrolEditFun(\'{0}\');" >设置为标准库</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="dailyPatrol-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="dailyPatrolEditEnd(\'{0}\');" >结束巡防</a>', row.id);
                    }else{
					str += $.formatString('<a href="javascript:void(0)" class="dailyPatrol-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-save   icon-blue\'" onclick="dailyPatrolEditFun(\'{0}\');" >设置为标准库</a>', row.id);
					}
                    return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.dailyPatrol-easyui-linkbutton-check').linkbutton({text:'设置为标准库'});
            $('.dailyPatrol-easyui-linkbutton-del').linkbutton({text:'结束巡防'});
        },
        toolbar : '#dailyPatrolToolbar'
    });
});
    
    
function dailyPatrolEditFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = dailyPatrolDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            dailyPatrolDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '设置当前数据为标准数据？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path}/dailyPatrol/setbase', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        dailyPatrolDataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
   }
   
   
function dailyPatrolEditEnd(id) {
    if (id == undefined) {//点击右键菜单才会触发这个
        var rows = dailyPatrolDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {//点击操作里面的删除图标会触发这个
        dailyPatrolDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.messager.confirm('询问', '结束当前巡防？', function(b) {
        if (b) {
            progressLoad();
            $.post('${path}/dailyPatrol/dailyPatrolEditEnd', {
                id : id
            }, function(result) {
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    dailyPatrolDataGrid.datagrid('reload');
                }
                progressClose();
            }, 'JSON');
        }
    });
}

/**
 * 清除
 */
function dailyPatrolCleanFun() {
    $('#dailyPatrolSearchForm input').val('');
    dailyPatrolDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function dailyPatrolSearchFun() {
     dailyPatrolDataGrid.datagrid('load', $.serializeObject($('#dailyPatrolSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="dailyPatrolSearchForm">
            <table>
                <tr>
                    <th>用户:</th>
                    <td><input name="uname" placeholder="用户名"/></td>
                    <th>路段:</th>
                    <td><input name="rname" placeholder="路段名"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="dailyPatrolSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="dailyPatrolCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="dailyPatrolDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
