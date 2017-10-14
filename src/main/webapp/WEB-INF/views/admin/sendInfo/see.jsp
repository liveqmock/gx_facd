<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ckplayer.js"></script>
<script type="text/javascript">
    $(function () {
        $("#infoStatus").val('${SendInfo.infoStatus}');
    });
    
    function sendinfoFileseeFun(url,type) {
		if(type==1){
			 $('#sendinfoimg').html("<img alt='' src='"+url+"' width='394px' height='357px'>");
		}else{
			 var flashvars={
				        f:url,
				        c:0
				    };
				    var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
				    CKobject.embedSWF('${staticPath }/static/ckplayer.swf','sendinfoimg','ckplayer_a1','394','357',flashvars,params);
		}
   	 $('#sendinfoimg').dialog({  
            content: "",  
            title: '${file.filename}',  
            //iconCls: "icon-edit",  
            collapsible: false,  
            minimizable: false,  
            maximizable: false,  
            closable: false,  
            resizable: false,  
            top: 200,  
            width:400,  
            //height: 485,  
            height:400,  
            //left: auto,  
            modal: true,  
            buttons: [  
                {  
                    text: '关闭',  
                  //  iconCls: 'icon-ok',  
                    handler: function () {  
                        $('#sendinfoimg').dialog('close');  
                            }  
                }]
   	 })
   }
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow-y:scroll; overflow-x:scroll;padding: 3px;">
        <form id="sendInfoEditForm" method="post">
            <input name="id" type="hidden" value="${SendInfo.id}"/>
            <table class="grid">
                <tr>
                    <td width="20%;">报送信息名称</td>
                    <td width="30%;">${SendInfo.sendInfoName}</td>
                    <td width="20%;">报送信息类型</td>
                    <td width="30%;"><label>
                    <c:if test="${SendInfo.sendInfoType==0}">基础设施</c:if>
                    <c:if test="${SendInfo.sendInfoType==1}">涉外事件</c:if>
                    <c:if test="${SendInfo.sendInfoType==2}">走私事件</c:if>
                    <c:if test="${SendInfo.sendInfoType==3}">突发事件</c:if>
                    </label></td>
                </tr>
                <tr>
                    <td>报送人</td>
                    <td><p>${SendInfo.sendusername}</p></td>
                    <td>报送信息时间</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${SendInfo.receiveTime}"/></td>
                </tr>
                <tr>
                    <td>报送信息内容</td>
                    <td colspan="3">
                    	<p>${SendInfo.sendInfo}</p>
                    </td>
                </tr>
                <c:if test="${SendFile.size() >0}">
                    <tr>
                        <td>附件列表</td>
                        <td colspan="3"  >
                            <ul>
                               
                            <c:forEach items="${SendFile}" var="file">
                             <li>
								<a  href="javascript:void(0)"  onclick="sendinfoFileseeFun('${file.fileurl}','${file.filetype }');" >${file.filename}</a>
                             </li>
                            </c:forEach>
                                  
                            </ul>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${SendMsg.size() >0}">
                    <tr>
                        <td>追加内容</td>
                        <td colspan="3"  >
                            <ul>
                                <li>
                            <c:forEach items="${SendMsg}" var="msg">
                            		<p>${msg.msgInfo}</p><br/>
                            </c:forEach>
                                   </li>
                            </ul>
                        </td>
                    </tr>
                </c:if>
                 <c:if test="${SendFile1.size() >0}">
                <tr>
                        <td>追加附件列表</td>
                        <td colspan="3"  >
                            <ul>
                                
                            <c:forEach items="${SendFile1}" var="file">
                            <li>
								<a  href="javascript:void(0)"  onclick="sendinfoFileseeFun('${file.fileurl}','${file.filetype }');" >${file.filename}&nbsp;&nbsp;&nbsp;&nbsp;</a>
                            </li>
                            </c:forEach>
                                   
                            </ul>
                        </td>
                    </tr>
                    </c:if>
            </table>
            <div id="sendinfoimg" style="overflow: hidden">
			
            </div>
        </form>
    </div>
</div>