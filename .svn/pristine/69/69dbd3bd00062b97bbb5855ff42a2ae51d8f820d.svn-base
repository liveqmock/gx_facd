<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#roadEditForm').form({
            url : '${path}/road/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#roadEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#editStatus").val('${road.dataStatus}'); 
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="roadEditForm" method="post">
            <input name="id" type="hidden" value="${road.id}" />
            <input name="code" type="hidden" value="${road.code}">
            <table class="grid">
               <tr>
                    <td>名称</td>
                    <td>
                        <input name="name" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${road.name}">
                    </td>
                    <td>状态</td>
                    <td >
                        <select id="editStatus" name="dataStatus" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option ${road.dataStatus==0?'selected':''} value="0">正常</option>
                            <option ${road.dataStatus==1?'selected':''} value="1">停用</option>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td>长度</td>
                    <td>
                        <input name=distance type="text" placeholder="请输入长度" class="easyui-validatebox span2" value="${road.distance}">
                                                                          千米</td>
                    <td>全程用时</td>
                    <td>
                        <input name="time" type="text" placeholder="请输入用时" class="easyui-validatebox span2"  value="${road.time}">
                                                                     分钟</td>
                </tr> 
               
            </table>
        </form>
    </div>
</div>