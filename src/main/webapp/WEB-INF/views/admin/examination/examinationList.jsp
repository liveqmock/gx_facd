<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var examinationDataGrid;
    $(function() {
        examinationDataGrid = $('#examinationDataGrid').datagrid({
        url : '${path}/examination/dataGrid',
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
            width : '27%',
            title : '标题',
            field : 'title',
            sortable : true
        }, {
            width : '7%',
            title : '选题类型',
            field : 'type',
            sortable : true,
            formatter : function(value, row, index) {
                switch (value) {
                case 1:
                    return '随机选题';
                case 2:
                    return '手动选题';
                }
            }
        }, {
            width : '6%',
            title : '创建人',
            field : 'name',
            sortable : true
        }
        , {
            width : '140',
            title : '创建时间',
            field : 'createTime',
            sortable : true
        },
        {
            field : 'action',
            title : '操作',
            width : 200,
            formatter : function(value, row, index) {
            	 var str = '';
            	if(row.status==1){
                    str += $.formatString('<a href="javascript:void(0)" class="examination-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="examinationEditFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="examination-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="examinationDeleteFun(\'{0}\');" >删除</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="examination-easyui-linkbutton-check" data-options="plain:true,iconCls:\'fi-check icon-red\'" onclick="examinationcheckFun(\'{0}\');" >发布</a>', row.id);
            	}else{
            		str += $.formatString('<a href="javascript:void(0)" class="examination-easyui-linkbutton-see" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="examinationseeFun(\'{0}\');" >查看</a>', row.id);
            	}
             return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.examination-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.examination-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.examination-easyui-linkbutton-see').linkbutton({text:'查看'});
            $('.examination-easyui-linkbutton-check').linkbutton({text:'发布'});
        },
        toolbar : '#examinationToolbar'
    });
});

/**
 * 添加框
 * @param url
 */
function examinationAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/examination/addPage'
        /*
        ,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = examinationDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#examinationAddForm');
                f.submit();
            }
        } ]
       */
    });
}


/**
 * 编辑
 */
function examinationEditFun(id) {
    if (id == undefined) {
        var rows = examinationDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        examinationDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/examination/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = examinationDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#examinationEditForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 删除
 */
 function examinationDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = examinationDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         examinationDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前信息？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/examination/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     examinationDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}
 /**
  * 查看
  */
 function examinationseeFun(id) {
     if (id == undefined) {
         var rows = examinationDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {
         examinationDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.modalDialog({
         title : '查看',
         width : 700,
         height : 600,
         href :  '${path}/examination/see?id=' + id,
         buttons : [ {
             text : '关闭',
             handler : function() {
                 parent.$.modalDialog.openner_dataGrid = examinationDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                 parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                 parent.$.modalDialog.handler.dialog('close');
             }
         } ]
     });
 }
 /**
  * 发布
  */
 function examinationcheckFun(id) {
      if (id == undefined) {//点击右键菜单才会触发这个
          var rows = examinationDataGrid.datagrid('getSelections');
          id = rows[0].id;
      } else {//点击操作里面的删除图标会触发这个
          examinationDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
      }
      parent.$.messager.confirm('询问', '您是否要发布当前信息？', function(b) {
          if (b) {
              progressLoad();
              $.post('${path}/examination/check', {
                  id : id
              }, function(result) {
                  if (result.success) {
                      parent.$.messager.alert('提示', result.msg, 'info');
                      examinationDataGrid.datagrid('reload');
                  }
                  progressClose();
              }, 'JSON');
          }
      });
 }

/**
 * 清除
 */
function examinationCleanFun() {
    $('#examinationSearchForm input').val('');
    $('#examinationSearchForm select').val('');
    examinationDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function examinationSearchFun() {
     examinationDataGrid.datagrid('load', $.serializeObject($('#examinationSearchForm')));
}

function exportexamination(){
	window.location.href="${path}/examination/exportexamination?"+$("#examinationSearchForm").serialize();
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="examinationSearchForm">
            <table>
                <tr>
                    <th>标题:</th>
                    <td><input name="title" placeholder="搜索条件"/></td>
                     <th>创建人:</th>
                    <td><input name="createUser" placeholder="搜索条件"/></td>
                    <th>选题类型:</th>
                    <td><select name="type" >
                    <option value="">请选择</option>
                    <option value="1">随机选题</option>
                    <option value="2">手动选题</option>
                    </select>
                   </td>
                    <th>创建时间:</th>
                    <td colspan="2">
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="examinationSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="examinationCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="examinationDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="examinationToolbar" style="display: none;">
        <a onclick="examinationAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>