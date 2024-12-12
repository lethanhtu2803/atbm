<%@page import="com.demo.models.AccountModel"%>
<%@page import="com.demo.models.AccountDetailsModel"%>
<%@page import="com.demo.models.DurationModel"%>
<%@page import="com.demo.entities.AccountService"%>
<%@page import="com.demo.models.AccountServiceModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.demo.entities.Service"%>
<%@page import="com.demo.models.ServiceModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    ServiceModel serviceModel = new ServiceModel();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    AccountServiceModel accountServiceModel = new AccountServiceModel();
    DurationModel durationModel = new DurationModel();
    AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
%>
<div class="content-wrapper">
    <!-- Tiêu đề nội dung -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-12">
                    <h1>Tổng quan dịch vụ và người dùng</h1>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Nội dung chính -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <!-- /.card-header -->
                        <div class="card-body">
                            <script>
                                $(document).ready(function() {
                                    $('#buttonReload').click(function() {
                                        location.reload();
                                    });
                                });
                            </script>

							<div id="serviceDialog" title="Nhắn tin với khách hàng"
								style="display: none;">
								<form method="post" action="${pageContext.request.contextPath }/admin/accountService?action=sendChat">
								<table>
									<tr>
										<td>Nhắn với tài khoản:</td>
										<td><input type="text" id="account" readonly></td>
									</tr>
									<tr>
										<td>Nội dung:</td>
										<td><textarea style="width: 205px;" name="message"></textarea></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><button id="submitChat"  type="submit">Gửi</button></td>
									</tr>
									<input type="hidden" name="accountID" id="accountID">
								</table>
									
									
								</form>
							</div>
							<script>
								$(document).ready(function() {
									
									$('#buttonReload').click(function() {
										location.reload();
									});
									 $('.sendChat').click(function() {
										 var row = $(this).closest('tr');
							             var account = row.find('td:eq(1)').text();
							             var accountID = account.split('-')[0].trim();
							             $('#account').val(account);
							             $('#accountID').val(accountID);
										 $("#serviceDialog").dialog({
											 width: 400,
											 height: 300
										 });
										 $('#serviceDialog').css('display', 'block');
									});
									
								});
							
							
							</script>
							<button id="buttonReload" class="btn"><i class="fa-solid fa-rotate"></i></button>
                            <table style="text-align: center;" id="example2" class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Tài khoản</th>
                                        <th>Thông tin</th>
                                        <th>Dịch vụ</th>
                                        <th>Thời hạn</th>
                                        <th>Mô tả</th>
                                        <th>Ngày bắt đầu</th>
                                        <th>Ngày hết hạn</th>
                                        <th>Mã sale</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (AccountService service : accountServiceModel.findAll()) { %>
                                        <tr>
                                            <td><%= service.getId() %></td>
                                            <td><%= accountDetailsModel.findAccountByAccountID(service.getAccountID()).getAccountid()%> - <%= accountDetailsModel.findAccountByAccountID(service.getAccountID()).getName() %></td>
                                            <td><%= accountDetailsModel.findAccountByAccountID(service.getAccountID()).getPhonenumber()%> - <%= new AccountModel().findAccountByAccountID(service.getAccountID()).getEmail() %></td>
                                            <td><%= serviceModel.findByID(service.getServiceID()).getName() %></td>
                                            <td><%= durationModel.findById(service.getDurationID()).getName() %></td>
                                            <td><%= service.getDescription() %></td>
                                            <td><%= service.getCreated() %></td>
                                            <td><%= service.getEndService() %></td>
                                            <td><%= String.valueOf(service.getSaleID()).equals(0) ? service.getSaleID() : "Không áp dụng"%></td>
                                            <td><a href="${pageContext.request.contextPath }/admin/accountService?action=cancelService&accountServiceID=<%= service.getId() %>">Hủy dịch vụ</a> | <button class="sendChat" style="border: none;background: transparent;transform: translateY(2px);"><i class="fa-brands fa-rocketchat"></i></button></td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
