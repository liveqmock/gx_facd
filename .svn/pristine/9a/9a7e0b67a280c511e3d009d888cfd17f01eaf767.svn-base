<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#roadid').combobox({
			url : '${path }/dailyPatrol/tree',
			multiple : false,
			required : true,
			panelHeight : 'auto',
		});
        $('#sendInfoAddForm').form({
            url : '${path}/sendInfo/add',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
            	console.info(result)
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    //之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#sendInfoAddForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="sendInfoAddForm" method="post">
            <table class="grid">
                <tr>
                    <th width="20%" >报送信息标题</th>
                    <td width="30%"><input name="sendInfoName" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                    <th width="20%">报送信息类型</th>
                    <td width="30%"><select name="sendInfoType" class="easyui-combobox"  >
					    <!-- ：0-基础设施；1-涉外时间；2-走私事件；3-突发事件 -->
					    <option value="0">基础设施</option>
					    <option value="1">涉外事件</option>
					    <option value="2">走私事件</option>
					    <option value="3">突发事件</option>
					</select> </td>
                </tr>
                <tr>
                    <th width="20%" >报送信息内容</th>
                    <td colspan="3" ><textarea name="sendInfo" placeholder="请输入内容" class="easyui-validatebox span2" data-options="required:true" cols="50" rows="4" ></textarea></td>
                </tr>
                <tr>
                <td>巡防选择</td>
				<td><select name="pid" id="roadid" class="easyui-combobox"
					data-options="width:140,height:29,editable:false,panelHeight:'auto'"></select>
				</td>
                <td>紧急程度</td>
                <td><select name="urgencyLevel">
                <option value="0" >一般</option>
                <option value="1" >紧急</option>
                <option value="2" >特急</option>
                </select></td>
                </tr>
                <tr>
                <td>经度</td>
                <td><input name="longitude" type="text" class="easyui-validatebox span2"/> </td>
                <td>纬度</td>
                <td><input name="latitude" type="text" class="easyui-validatebox span2"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>