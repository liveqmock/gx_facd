<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var videoFileDataGrid;
    $(function() {
        videoFileDataGrid = $('#videoFileDataGrid').datagrid({
        url : '${path}/videoFile/dataGrid',
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
            width : '5%',
            title : '编号',
            field : 'id',
            sortable : true
        }, {
            width : '16%',
            title : '文件名称',
            field : 'fileName',
            sortable : true
            
        }, {
            width : '25%',
            title : '文件路径',
            field : 'fileUrl',
            sortable : true
        }, {
            width : '10%',
            title : '创建时间',
            field : 'createTime',
            sortable : true
        },
       /* {
            width : '5%',
            title : '直播人',
            field : 'username',
            sortable : true
        }, */
        {
            width: '12%',
            title: '信息类型',
            field: 'video_type',
            sortable: true,
            formatter: function (value, row, index) {
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
        },
        {
            width : '10%',
            title : '备注',
            field : 'remarks',
            sortable : true
        },
        {
            field : 'action',
            title : '操作',
            width : '20%',
            formatter : function(value, row, index) {
                var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="videoFile-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="videoFileEditFun(\'{0}\');" >编辑</a>', row.id);
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="videoFile-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="videoFileDeleteFun(\'{0}\');" >删除</a>', row.id);
                    return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.videoFile-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.videoFile-easyui-linkbutton-del').linkbutton({text:'删除'});
        },
        toolbar : '#videoFileToolbar'
    });
});

    /**
     * 删除
     */
     function videoFileDeleteFun(id) {
         if (id == undefined) {//点击右键菜单才会触发这个
             var rows = videoFileDataGrid.datagrid('getSelections');
             id = rows[0].id;
         } else {//点击操作里面的删除图标会触发这个
        	 videoFileDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
         }
         parent.$.messager.confirm('询问', '您是否要删除当前信息？', function(b) {
             if (b) {
                 progressLoad();
                 $.post('${path}/videoFile/delete', {
                     id : id
                 }, function(result) {
                     if (result.success) {
                         parent.$.messager.alert('提示', result.msg, 'info');
                         videoFileDataGrid.datagrid('reload');
                     }else{
                    	 parent.$.messager.alert('提示', result.msg, 'info');
                    	 videoFileDataGrid.datagrid('reload');
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
function videoFileAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/videoFile/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = videoFileDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#videoFileAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function videoFileEditFun(id) {
    if (id == undefined) {
        var rows = videoFileDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        videoFileDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 750,
        height : 600,
        href :  '${path}/videoFile/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = videoFileDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#videoFileEditForm');
                f.submit();
            }
        } ]
    });
}
function see(id) {
    if (id == undefined) {
        var rows = videoFileDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
    	videoFileDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '查看',
        width : 700,
        height : 600,
        href :  '${path}/videoFile/see?id=' + id,
        buttons : [ {
            text : '关闭',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = videoFileDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        } ]
    });
}




/**
 * 清除
 */
function videoFileCleanFun() {
    $('#videoFileSearchForm input').val('');
    $('#videoFileSearchForm select').val('');
    videoFileDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function videoFileSearchFun() {
     videoFileDataGrid.datagrid('load', $.serializeObject($('#videoFileSearchForm')));
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="videoFileSearchForm">
            <table>
                <tr>
                    <th style="display:none">直播人:</th>
                    <td style="display:none"><input name="videoName" placeholder="搜索条件"/></td>
                    
                    <th>操作时间:</th>
                    <td colspan="3" >
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                    <th>信息类型:</th>
                    <td>
	                    <select name="videotype"  id="videotype" >
						   
						    <option value="" selected="selected">全部</option>
						    <option value="0">基础设施</option>
						    <option value="1">涉外事件</option>
						    <option value="2">走私事件</option>
						    <option value="3">突发事件</option>
						</select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="videoFileSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="videoFileCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="videoFileDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<!-- 
<div id="videoFileToolbar" style="display: none;">
 
        <a onclick="videoFileAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
</div>
-->