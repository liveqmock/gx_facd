<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>


<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="" method="post">
			<input name="id" type="hidden" value="${videoFile.id}" />
		<table class="grid">
                
                <tr>
                    <td>文件名称</td>
                    <td><input name="fileName" type="text"  value="${videoFile.fileName}">
                    </td>
                </tr>
                <tr>
                    <td>文件路径</td>
                    <td><input name="fileUrl" type="text"  value="${videoFile.fileUrl}">
                    </td>
                </tr>
                <tr>
                    <td>直播人</td>
                    <td><input name="userId" type="text"  value="${user.name}">
                    </td>
                </tr>
                 <tr>
                    <td>备注</td>
                    <td><input name="remarks" type="text"  value="${videoFile.remarks} " style="width: 350px;">
                    </td>
                </tr>
                
                
            </table>
		</form>
	</div>
</div>