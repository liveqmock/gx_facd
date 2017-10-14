<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var sendInfoDataGridbase;
    $(function () {
        sendInfoDataGridbase = $('#sendInfoDataGridbase').datagrid({
            url: '${path}/sendInfo/dataGrid?stype=1',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            frozenColumns: [[{
                width: '4%',
                title: '编号',
                field: 'id',
                sortable: true
            }, {
                width: '6%',
                title: '报送人',
                field: 'sendUserName',
                sortable: true
            }, {
                width: '10%',
                title: '报送标题',
                field: 'sendInfoName',
                sortable: true
            }, 
            {
                width: '14%',
                title: '报送时间',
                field: 'receiveTime',
                sortable: true
            },
            {
                width: '30%',
                title: '报送内容',
                field: 'sendInfo',
                sortable: true
            },
            {
                width: '12%',
                title: '报送信息类型',
                field: 'sendInfoType',
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
                width: '12%',
                title: '紧急程度',
                field: 'urgencyLevel',
                sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '一般';
                        case 1:
                            return '紧急';
                        case 2:
                            return '特急';
                       
                    }
                }
            },
            {
                width: '12%',
                title: '报送信息状态',
                field: 'infoStatus',
                sortable: true,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '未查看';
                        case 1:
                            return '已查看';
                        case 2:
                            return '已审核';
                        case 3:
                            return '未审核';
                    }
                }
//             }, {
//                 field: 'action',
//                 title: '操作',
//                 width: '10%',
//                 formatter: function (value, row, index) {
//                     var str =""; 
//                      str = $.formatString('<a href="javascript:void(0)" class="sendInfo-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="sendInfoEditFunbase(\'{0}\');" >审核</a>', row.id);
// 						return str;
//                 }
            }]],
            onLoadSuccess: function (data) {
                $('.sendInfo-easyui-linkbutton-edit').linkbutton({text: '审核'});
            },
            toolbar: '#sendInfoToolbarbase'
        });
    });
    /**
     *<shiro:hasPermission name="/sendInfo/delete">
     *</shiro:hasPermission>
     */
     /**
      * 添加框
      * @param url
      */
     function sendInfoAddFun() {
         parent.$.modalDialog({
             title : '添加',
             width : 700,
             height : 600,
             href : '${path}/sendInfo/addPage',
             buttons : [ {
                 text : '确定',
                 handler : function() {
                     parent.$.modalDialog.openner_dataGrid = sendInfoDataGridbase;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                     var f = parent.$.modalDialog.handler.find('#sendInfoAddForm');
                     f.submit();
                 }
             } ]
         });
     }
     function sendFileFun(){
            var rows = sendInfoDataGridbase.datagrid('getSelections');
            if (rows.length==0) {
                return;
            }
            var id = rows[0].id;
         parent.$.modalDialog({
             title: '绑定附件',
             width: 700,
             height: 600,
             href: '${path}/sendFile/addPage?id=' + id,
             buttons: [{
                 text: '确定',
                 handler: function () {
                     parent.$.modalDialog.openner_dataGrid = sendInfoDataGridbase;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                     var f = parent.$.modalDialog.handler.find('#sendFileAddForm');
                     f.submit();
                 }
             } ]
         });
     }
    /**
     * 审核
     */
    function sendInfoEditFunbase(id) {
        if (id == undefined) {
            var rows = sendInfoDataGridbase.datagrid('getSelections');
            id = rows[0].id;
        } else {
            sendInfoDataGridbase.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title: '审核',
            width: 700,
            height: 600,
            href: '${path}/sendInfo/verify?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = sendInfoDataGridbase;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#sendInfoEditForm');
                    f.submit();
                }
            }]
    	});
	}


/**
 * 清除
 */
function sendInfoCleanFunbase() {
	$('#sendInfoSearchFormbase input').val('');
	$('#sendInfoSearchFormbase select').val('');
   sendInfoDataGridbase.datagrid('load', {});
}
/**
 * 搜索
 */
function sendInfoSearchFunbase() {
     sendInfoDataGridbase.datagrid('load', $.serializeObject($('#sendInfoSearchFormbase')));
}

function exportSendInfo(){
	window.location.href="${path}/sendInfo/exportSendInFo?&stype=1&"+$("#sendInfoSearchFormbase").serialize();
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: auto; overflow: hidden;background-color: #fff">
        <form id="sendInfoSearchFormbase">
            <table>
                <tr>
                	<th>报送人:</th>
                    <td><input name="sendUserName" id="sendUserName" placeholder="输入上报人"/></td>
                    <th>报送标题:</th>
                    <td><input name="sendInfoName" id="sendInfoName" placeholder="输入报送标题"/></td>
                    <th>报送时间:</th>
                    <td colspan="3" >
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                </tr>
                
                <tr>
                	<th>报送信息类型:</th>
                    <td>
                    <select  name="sendInfoType"  >
					    <!-- ：0-基础设施；1-涉外时间；2-走私事件；3-突发事件 -->
					    <option value="" >全部</option>
					    <option value="0">基础设施</option>
					    <option value="1">涉外事件</option>
					    <option value="2">走私事件</option>
					    <option value="3">突发事件</option>
					</select>
                   
                    </td>
                    <th>紧急程度:</th>
                    <td>
                    <select name="urgencyLevel" >
					    <option value="" selected="selected">全部</option>
					    <option value="0">一般</option>
					    <option value="1">紧急</option>
					    <option value="2">特急</option>
					</select>
                    </td>
                    <th>报送信息状态:</th>
                    <td>
                    <select name="infoStatus"  >
					    <!-- 0-未查看；1-信息已查看；2-审核已通过；3-审核未通过 -->
					    <option value="" selected="selected">全部</option>
					    <option value="0">未查看</option>
					    <option value="1">已查看</option>
					    <option value="2">已审核</option>
					    <option value="3">未审核</option>
					</select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="sendInfoSearchFunbase();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="sendInfoCleanFunbase();">清空</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-download',plain:true" onclick="exportSendInfo();">导出</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="sendInfoDataGridbase" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="sendInfoToolbarbase" style="display: none;">
    <a onclick="sendInfoAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加报送</a>
</div>