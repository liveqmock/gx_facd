<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
    $(function() {
        $('#userEditorganizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.organizationId}'
        });

        $('#userEditRoleIds').combotree({
            url : '${path }/role/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            multiple : true,
            required : true,
            cascadeCheck : false,
            value : ${roleIds }
        });
		/*
        $('#roadIds').combotree({
            url: '${path }/road/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            multiple : true,
            required : true,
            cascadeCheck : false,
            value:${user.road}
        });
        */
        $('#userEditForm').form({
            url : '${path }/user/edit',
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
                    var form = $('#userEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        $("#userEditSex").val('${user.sex}');
        $("#userEditUserType").val('${user.userType}');
        $("#userEditStatus").val('${user.status}');
        $("#politicsid").val('${user.politics}');
        $("#isGrantid").val('${user.isGrant}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="userEditForm" method="post" enctype="multipart/form-data" >
			<div class="light-info" style="overflow: hidden; padding: 3px;">
				<div>密码不修改请留空。</div>
			</div>
			<table class="grid">
				<tr>
					<td>登录名</td>
					<td><input name="id" type="hidden" value="${user.id}">
						<input name="loginName" type="text" placeholder="请输入登录名称"
						class="easyui-validatebox" data-options="required:true"
						value="${user.loginName}"></td>
					<td>姓名</td>
					<td><input name="name" type="text" placeholder="请输入姓名"
						class="easyui-validatebox" data-options="required:true"
						value="${user.name}"></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input type="text" name="password" /></td>
					<td>性别</td>
					<td><select id="userEditSex" name="sex"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input type="text" name="age" value="${user.age}"
						class="easyui-numberbox" /></td>
					<td>用户类型</td>
					<td><select id="userEditUserType" name="userType"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" >管理员</option>
							<option value="1" >移动设备用户</option>
							<option value="2" >智能电视用户</option>
					</select></td>
				</tr>
				<tr>
					<td>部门</td>
					<td><select id="userEditorganizationId" name="organizationId"
						style="width: 140px; height: 29px;" class="easyui-validatebox"
						data-options="required:true"></select></td>
					<td>角色</td>
					<td><input id="userEditRoleIds" name="roleIds"
						style="width: 140px; height: 29px;" /></td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" name="phone" class="easyui-numberbox"
						value="${user.phone}" /></td>
					<td>用户类型</td>
					<td><select id="userEditStatus" name="status"
						value="${user.status}" class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">正常</option>
							<option value="1">停用</option>
					</select></td>
				</tr>
				<tr>
					<td>路段分配</td>
					<td>
					
					<input id="roadIds" name = "road" class="easyui-combotree" 
					data-options="url:'${path }/road/tree',
					method:'post',
					 parentField: 'pid',
            		lines : true,
            		panelHeight: 'auto',
            		multiple: false,
            		required: true,
            		cascadeCheck: false," style="width: 140px; height: 29px;" value='${user.road }'>
					</td>
					<td>政治面貌</td>
                    <td><select name="politics" id="politicsid" class="easyui-combobox"
                        data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value=""></option>
                            <option value="01">中共党员</option>
                            <option value="02">中共预备党员</option>
                            <option value="03">共青团员</option>
                            <option value="04">民革党员</option>
                            <option value="05">民盟盟员</option>
                            <option value="06">民建会员</option>
                            <option value="07">民进会员</option>
                            <option value="08">农工党党员</option>
                            <option value="09">致公党党员</option>
                            <option value="10">九三学社社员</option>
                            <option value="11">台盟盟员</option>
                            <option value="12">无党派人士</option>
                            <option value="13">群众</option>
                    </select></td>
				</tr>
				<tr>
                    <td>生日</td>
                    <td><input name="birthday" type="text" placeholder="点击选择生日"
                       onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" value="${user.birthday}"></td>
                    <td>家庭住址</td>
                    <td><input name="homeAdress" type="text" placeholder="家庭住址"
                        class="easyui-validatebox" value="${user.homeAdress}"></td>
                </tr>
                <tr>
					<td>头像</td>
					<td><input name="file" type="file" ></td>
<!-- 					<td>家庭住址</td> -->
<!-- 					<td><input name="homeAdress" type="text" placeholder="家庭住址" -->
<!-- 						class="easyui-validatebox"></td> -->
				 <td>原头像</td>
				 <td><img alt="${user.name}头像" src="${user.icon}" style="width: 150px;height: 150px"></td>
				</tr>
                <c:if test="${user.userType!=0 }">
				<tr>
                    <td>设备号</td>
                    <td><input type="text" value="${user.devicenum}" name="devicenum" class="easyui-validatebox" /> </td>
                    <td>登陆授权</td>
                    <td><select name="isGrant" id="isGrantid" class="easyui-combobox"
                        data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">未授权</option>
                            <option value="1">授权</option>
                    </select></td>
                </tr>
                </c:if>
			</table>
		</form>
	</div>
</div>