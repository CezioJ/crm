<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://"+ request.getServerName()
+":"+ request.getServerPort() +request.getContextPath()+ "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<base href="<%=basePath%>">
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form id="createActivityForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${userList}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control date" name="date" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control date" name="date" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="saveCreateActivityBtn" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="edit-id">
					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveEditActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control date" type="text" id="query-startDate" readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control date" type="text" id="query-endDate" readonly/>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityByConditionBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody"></tbody>
				</table>
				<div id="page"></div>
			</div>

		</div>

	</div>
</body>
<script type="text/javascript">

	$(function(){
		//给"创建"按钮添加单击事件
		$("#createActivityBtn").click(function () {
			//初始化工作
			//重置表单
			$("#createActivityForm").get(0).reset();

			//弹出创建市场活动的模态窗口
			$("#createActivityModal").modal("show");
		});

		//给"保存"按钮添加单击事件
		$("#saveCreateActivityBtn").click(function () {
			//收集参数
			let owner=$("#create-marketActivityOwner").val();
			let name=$.trim($("#create-marketActivityName").val());
			let startDate=$("#create-startDate").val();
			let endDate=$("#create-endDate").val();
			let cost=$.trim($("#create-cost").val());
			let description=$.trim($("#create-description").val());
			//表单验证
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				//使用字符串的大小代替日期的大小
				if(endDate<startDate){
					alert("结束日期不能比开始日期小");
					return;
				}
			}

			let regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本只能为非负整数");
				return;
			}
			//发送请求
			$.ajax({
				url:'workbench/activity/saveCreateActivity.do',
				data:{
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.status=="1"){
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
						//刷新市场活动列，显示第一页数据，保持每页显示条数不变
						queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#createActivityModal").modal("show");//可以不写。
					}
				}
			});

		});

		//日历控件显示中文
		$.fn.datetimepicker.dates['zh-CN'] = {
			days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
			daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
			daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
			months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
			today: "今天",
			clear: "清空",
			suffix: [],
			meridiem: ["上午", "下午"]
		};

		//当容器加载完成之后，对容器调用工具函数
		//$("input[name='mydate']").datetimepicker({
		$(".date").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month', //可以选择的最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，是否自动关闭日历
			todayBtn:true,//设置是否显示"今天"按钮,默认是false
			clearBtn:true//设置是否显示"清空"按钮，默认是false
		});
		//当市场活动主页面加载完成，查询所有数据的第一页以及所有数据的总条数,默认每页显示10条
		queryActivityByConditionForPage(1,10);

		//给"查询"按钮添加单击事件
		$("#queryActivityBtn").click(function () {
			//查询所有符合条件数据的第一页以及所有符合条件数据的总条数;
			queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption', 'rowsPerPage'));
		});

		//给"全选"按钮添加单击事件
		$("#checkAll").click(function () {
			//如果"全选"按钮是选中状态，则列表中所有checkbox都选中
			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});

		$("#tBody").on("click","input[type='checkbox']",function () {
			//如果列表中的所有checkbox都选中，则"全选"按钮也选中
			let allChecked = $("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size();
			$("#checkAll").prop("checked", allChecked);
		});

		//给"删除"按钮添加单击事件
		$("#deleteActivityBtn").click(function () {
			//收集参数
			//获取列表中所有被选中的checkbox
			let checkedIds=$("#tBody input[type='checkbox']:checked");
			if(checkedIds.size()==0){
				alert("请选择要删除的市场活动");
				return;
			}

			if(window.confirm("确定删除吗？")){
				let ids="";
				$.each(checkedIds,function () {//id=xxx&id=xxx&.....&id=xxx&
					ids+="id="+this.value+"&";
				});
				ids=ids.substr(0,ids.length-1);//id=xxx&id=xxx&.....&id=xxx

				//发送请求
				$.ajax({
					url:'workbench/activity/deleteActivityByIds.do',
					data:ids,
					type:'post',
					dataType:'json',
					success:function (data) {
						if(data.status == "1"){
							//刷新市场活动列表,显示第一页数据,保持每页显示条数不变
							queryActivityByConditionForPage(1,$("#page").bs_pagination('getOption', 'rowsPerPage'));
						}else{
							//提示信息
							alert(data.message);
						}
					}
				});
			}
		});

		//给"修改"按钮添加单击事件
		$("#editActivityBtn").click(function () {
			//收集参数
			//获取列表中被选中的checkbox
			let checkedIds=$("#tBody input[type='checkbox']:checked");
			if(checkedIds.size()==0){
				alert("请选择要修改的市场活动");
				return;
			}
			if(checkedIds.size()>1){
				alert("每次只能修改一条市场活动");
				return;
			}
			let id=checkedIds.val();
			//发送请求
			$.ajax({
				url:'workbench/activity/queryActivityById.do',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					//把市场活动的信息显示在修改的模态窗口上
					$("#edit-id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.endDate);
					$("#edit-cost").val(data.cost);
					$("#edit-description").val(data.description);
					//弹出模态窗口
					$("#editActivityModal").modal("show");
				}
			});
		});

		//给"更新"按钮添加单击事件
		$("#saveEditActivityBtn").click(function () {
			//收集参数
			let id=$("#edit-id").val();
			let owner=$("#edit-marketActivityOwner").val();
			let name=$.trim($("#edit-marketActivityName").val());
			let startDate=$("#edit-startTime").val();
			let endDate=$("#edit-endTime").val();
			let cost=$.trim($("#edit-cost").val());
			let description=$.trim($("#edit-description").val());
			//表单验证
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				//使用字符串的大小代替日期的大小
				if(endDate<startDate){
					alert("结束日期不能比开始日期小");
					return;
				}
			}

			let regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本只能为非负整数");
				return;
			}
			//发送请求
			$.ajax({
				url:'workbench/activity/saveEditActivity.do',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.status == "1"){
						//关闭模态窗口
						$("#editActivityModal").modal("hide");
						//刷新市场活动列表,保持页号和每页显示条数都不变
						queryActivityByConditionForPage($("#page").bs_pagination('getOption', 'currentPage'),$("#page").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#editActivityModal").modal("show");
					}
				}
			});
		});

		//给"批量导出"按钮添加单击事件
		$("#exportActivityAllBtn").click(function () {
			//发送同步请求
			window.location.href="workbench/activity/exportAllActivity.do";
		});

		//给"选择导出"按钮添加单击事件
		$("#exportActivityByConditionBtn").click(function (){
			//发送同步请求
			let checkedIds = $("#tBody input[type='checkbox']:checked");
			if(checkedIds.size() < 1){
				alert("请至少选中一项");
				return;
			}
			let ids = '';
			$.each(checkedIds,function (){
				ids+="id="+this.value+"&";
			})
			ids = ids.substr(0,ids.length-1);
			window.location.href="workbench/activity/exportActivityByCondition.do?"+ids;
		})

		//给"导入"按钮添加单击事件
		$("#importActivityBtn").click(function () {
			//收集参数
			let activityFileName = $("#activityFile").val();
			let suffix = activityFileName.substr(activityFileName.lastIndexOf(".") + 1).toLocaleLowerCase();//xls,XLS,Xls,xLs,....
			if (suffix != "xls") {
				alert("只支持xls文件");
				return;
			}
			let activityFile = $("#activityFile")[0].files[0];
			if (activityFile.size > 5 * 1024 * 1024) {
				alert("文件大小不超过5MB");
				return;
			}

			//FormData是ajax提供的接口,可以模拟键值对向后台提交参数;
			//FormData最大的优势是不但能提交文本数据，还能提交二进制数据
			let formData = new FormData();
			formData.append("activityFile", activityFile);
			formData.append("userName", "张三");

			//发送请求
			$.ajax({
				url: 'workbench/activity/importActivity.do',
				data: formData,
				processData: false,//设置ajax向后台提交参数之前，是否把参数统一转换成字符串：true--是,false--不是,默认是true
				contentType: false,//设置ajax向后台提交参数之前，是否把所有的参数统一按urlencoded编码：true--是,false--不是，默认是true
				type: 'post',
				dataType: 'json',
				success: function (data) {
					//显示反馈信息
					alert(data.message);
					if (data.status == "1") {
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						//刷新市场活动列表,显示第一页数据,保持每页显示条数不变
						queryActivityByConditionForPage(1, $("#page").bs_pagination('getOption', 'rowsPerPage'));
					} else {
						//模态窗口不关闭
						$("#importActivityModal").modal("show");
					}
				}
			});
		});

		function queryActivityByConditionForPage(pageNo,pageSize) {
			//收集参数
			let name = $("#query-name").val();
			let owner = $("#query-owner").val();
			let startDate = $("#query-startDate").val();
			let endDate = $("#query-endDate").val();
			//发送请求
			$.ajax({
				url: 'workbench/activity/queryActivityByConditionForPage.do',
				data: {
					name: name,
					owner: owner,
					startDate: startDate,
					endDate: endDate,
					pageNo: pageNo,
					pageSize: pageSize
				},
				type: 'post',
				dataType: 'json',
				success: function (data) {
					//显示市场活动的列表
					//遍历activityList，拼接所有行数据
					let htmlStr = "";
					$.each(data.activityList, function (index, obj) {
						htmlStr += "<tr class=\"active\">";
						htmlStr += "<td><input type=\"checkbox\" value=\"" + obj.id + "\"/></td>";
						htmlStr += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id=" + obj.id + "'\">" + obj.name + "</a></td>";
						htmlStr += "<td>" + obj.owner + "</td>";
						htmlStr += "<td>" + obj.startDate + "</td>";
						htmlStr += "<td>" + obj.endDate + "</td>";
						htmlStr += "</tr>";
					});
					$("#tBody").html(htmlStr);
					//取消"全选"按钮
					$("#checkAll").prop("checked", false);

					//计算总页数
					let totalPages = 1;
					if(data.totalRows%pageSize==0){
						totalPages=data.totalRows/pageSize;
					}else{
						totalPages=parseInt(data.totalRows/pageSize)+1;
					}

					//对容器调用bs_pagination工具函数，显示翻页信息
					$("#page").bs_pagination({
						currentPage:pageNo,//当前页号,相当于pageNo
						rowsPerPage:pageSize,//每页显示条数,相当于pageSize
						totalRows:data.totalRows,//总条数
						totalPages:totalPages,  //总页数,必填参数.
						visiblePageLinks:5,//最多可以显示的卡片数
						showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
						showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
						showRowsInfo:true,//是否显示记录的信息，默认true--显示

						//用户每次切换页号，都自动触发本函数;
						//每次返回切换页号之后的pageNo和pageSize
						onChangePage: function(event,pageObj) {
							queryActivityByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
						}
					});

				}
			});
		}


	});

</script>
</html>
