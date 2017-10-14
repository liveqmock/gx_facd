<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" charset="utf-8" src="${staticPath }/static/ckplayer.js"></script>
<script type="text/javascript">
    $(function() {
        $('#videoFileEditForm').form({
            url : '${path}/videoFile/edit',
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
                    var form = $('#videoFileEditForm');
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        

        
    });
    
    
</script>
<script type="text/javascript">
    var flashvars={
        f:'http://${video_ip}:${video_http_port}${videoFile.fileUrl}',
        c:0
    };
    var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
    CKobject.embedSWF('${staticPath }/static/ckplayer.swf','a1','ckplayer_a1','400','250',flashvars,params);
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="videoFileEditForm" method="post">
         <input name="userId" value="${user.id }" type="hidden"/>
          <input name="id" value="${videoFile.id }" type="hidden"/>
          <input name="videoimg" value="${videoFile.videoimg }" type="hidden"/>
            <table class="grid">
                
                <tr>
                    <td>文件名称</td>
                    <td><input name="fileName" type="text"  value="${videoFile.fileName}">
                    </td>
                
                    <td>文件路径</td>
                    <td><input name="fileUrl" type="text"  value="${videoFile.fileUrl}">
                    </td>
                </tr>
                <tr>
                    <td>直播人</td>
                    <td>${user.name}
                    </td>
                    <td>信息类型:</td>
                    <td>
	                    <select name="videotype"  class="easyui-combobox"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'">
						   
						    <option value="" selected="selected">全部</option>
						    <option value="0">基础设施</option>
						    <option value="1">涉外事件</option>
						    <option value="2">走私事件</option>
						    <option value="3">突发事件</option>
						</select>
                    </td>
                </tr>
                 <tr>
                    <td>备注</td>
                    <td><input name="remarks" type="text"  value="${videoFile.remarks} " style="width: 350px;">
                    </td>
                </tr>
                <tr>
                   <td>视频截图</td>
                    <td><img src="${videoFile.videoimg}" alt="${user.name}的视频截图" width="150px" height="150px"></td>
                </tr>
                <tr>
                <td>视频</td>
                  <td>
                  <div id="a1"></div>
				 <!-- 
				 <object  type="application/x-shockwave-flash" width="400" height="225">
				  <param name="movie" value="${staticPath }/static/flvplayer.swf">
				  <param name="quality" value="high">
				  <param name="allowFullScreen" value="true">
				  <param name="autostart " value="false">
				  <param name="wmode" value="opaque" />
				  <param name="FlashVars" value="${videoFile.fileUrl}">  
				   <param name="FlashVars" value="vcastr_file=http://${video_ip}:${video_http_port}${videoFile.fileUrl}">
				  
				  <embed src="${staticPath }/static/flvplayer.swf" allowfullscreen="true" flashvars="http://${video_ip}:${video_http_port}${videoFile.fileUrl}" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="400" height="225"></embed>
				</object>
				-->
                  </td>
                </tr>
            </table>
        </form>
    </div>
</div>
