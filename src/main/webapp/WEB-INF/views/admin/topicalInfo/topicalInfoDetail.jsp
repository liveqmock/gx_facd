<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;" >
         <table>
           <tr>
              <th>标题:</th>
              <td>${topicalInfo.title }</td>
           </tr>
           <tr>
              <th>正文:</th>
              <td>${topicalInfo.content}</td>
           </tr>
           <tr>
              <th>附件:</th>
              <td><img src="${itemFile.fileurl}"/></td>
           </tr>
       </table>
    	
    </div>
</div>