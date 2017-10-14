<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var feedBackDataGrid;
    $(function() {
        feedBackDataGrid = $('#feedBackDataGrid').datagrid({
        url : '${path}/feedBack/dataGrid',
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
            width : '60',
            title : '建议人',
            field : 'uid',
        }, {
            width : '60',
            title : '建议',
            field : 'question',
        }, {
            width : '60',
            title : '反馈',
            field : 'answer',
        }, {
            field : 'action',
            title : '操作',
            width : 200,
            formatter : function(value, row, index) {
                var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="feedBack-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="feedBackEditFun(\'{0}\');" >反馈</a>', row.id);
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.feedBack-easyui-linkbutton-edit').linkbutton({text:'反馈'});
        },
        toolbar : '#feedBackToolbar'
    });
});




/**
 * 编辑
 */
function feedBackEditFun(id) {
    if (id == undefined) {
        var rows = feedBackDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        feedBackDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/feedBack/addPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = feedBackDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#feedBackAddForm');
                f.submit();
            }
        } ]
    });
}



 function feedBackstopFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = feedBackDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         feedBackDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/feedBack/stop', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     feedBackDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}



</script>

<div class="easyui-layout" data-options="fit:true,border:false">
  
    <div data-options="region:'center',border:false">
        <table id="feedBackDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
