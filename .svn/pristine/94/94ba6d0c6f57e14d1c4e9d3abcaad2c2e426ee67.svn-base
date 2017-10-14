<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
   var LogDataGrid;
    $(function () {
    	LogDataGrid = $('#LogDataGrid').datagrid({
            url: '${path }/OperateLog/dataGrid',
            striped: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                width: '120',
                title: '操作人',
                field: 'username',
            }, {
                width: '80',
                title: '操作类型',
                field: 'type'
            }, {
                width: '100',
                title: '操作平台',
                field: 'terrace',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return 'pc端';
                    case 1:
                        return '移动端';
                    case 2:
                        return 'TV端';
                    }
                    
                }
            }, {
                width: '200',
                title: '操作内容',
                field: 'msg'
            }, {
                width: '130',
                title: '创建时间',
                field: 'createtime',
            }]]
        });
    });
    
    function operateLogCleanFun() {
    	$('#LogDataGridForm input').val('');
    	$('#LogDataGridForm select').val('');
    	LogDataGrid.datagrid('load', {});
    }
    
    function operateLogSearchFun() {
    	LogDataGrid.datagrid('load', $.serializeObject($('#LogDataGridForm')));
   }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="LogDataGridForm">
            <table>
                <tr>
                	<th>操作人:</th>
                    <td><input name="userName" id="userName" placeholder="输入操作人"/></td>
                    <th>操作平台:</th>
                    <td><input name="terrace" id="terrace" placeholder="输入操作平台"/></td>
                     <th>操作内容:</th>
                    <td><input name="msg" id="msg" placeholder="输入操作内容"/></td>
                    <th>操作时间:</th>
                    <td colspan="3" >
                        <input name="recvDateStart" id="recvDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                        <span style="float: left;">至</span>
                        <input  name="recvDateEnd" id = "recvDateEnd"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="float: left;" />
                    </td>
                     <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="operateLogSearchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="operateLogCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
    <div data-options="region:'center',border:false">
        <table id="LogDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>