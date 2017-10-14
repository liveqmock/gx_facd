<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ueditor/ueditor.all.min.js"> </script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;" >
       <table>
        <c:if test="${us.size()>0}">
           <tr>
              <th>公告用户:</th>
              <c:forEach items="${us}" var="u">
                <td>${u.name }&nbsp;&nbsp;&nbsp;</td>
              </c:forEach>
           </tr>
            <hr/>
           </c:if>
       </table>
      
    	${notice.info}
    </div>
</div>