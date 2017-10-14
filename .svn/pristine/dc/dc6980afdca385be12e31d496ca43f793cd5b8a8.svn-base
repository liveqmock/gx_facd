<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;height:700px;padding: 3px;" >
       
       <table>
        <c:if test="${us.size()>0}">
           <tr>
              <th>考试用户:</th>
              
           </tr>
           <tr>
           <c:forEach items="${us}" var="u">
                <td>${u.name }&nbsp;&nbsp;&nbsp;</td>
              </c:forEach>
           </tr>
            <hr/>
           </c:if>
       </table>
       
       
    </div>
</div>