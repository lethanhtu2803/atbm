<%@page import="com.demo.entities.Systemapartment"%>
<%@page import="com.demo.models.BranchModel"%>
<%@page import="com.demo.models.SystemApartmentModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%
 	SystemApartmentModel systemApartmentModel = new SystemApartmentModel();
 	BranchModel branchModel = new BranchModel();
 	
 %>
     <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <div class="container-fluid">
          <div class="row mb-2">
            <div class="col-sm-6">
              <h1>Danh sách căn hộ hệ thống</h1>
            </div>
            
          </div>
        </div><!-- /.container-fluid -->
      </section>

      <!-- Main content -->
      <section class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-12">
              <div class="card">
                
                <!-- /.card-header -->
                <script>
					$(document).ready(function() {
						$('#subject').keyup(function() {
							var subject = $(this).val();
							$.ajax({
								type: 'GET',
								dataType: 'json',
								contentType: 'application/json; charset=utf-8',
								url: '${pageContext.request.contextPath}/admin/systemapartment',
								data: {
									subject: subject,
									action: "searchBySubject"
								},
								success: function (systemapartment) {
									var s = '';
									for(var i = 0;i < systemapartment.length;i++) {
										if(systemapartment[i].branchid == 5) {
											branchName = '88 Hoàng Hoa Thám';
										} else if(systemapartment[i].branchid == 4) {
											branchName = '11 Võ Văn Ngân';
										} else if(systemapartment[i].branchid == 3) {
											branchName = '35 Hoàng Diệu';
										} else if(systemapartment[i].branchid == 2) {
											branchName = '456 Hoàng Văn Thụ';
										} else if(systemapartment[i].branchid == 1) {
											branchName = '123 Nguyễn Văn Cừ';
										}
										s+='<tr>';
										s+='<td>'+ systemapartment[i].id +'</td>';
										s+='<td>'+ systemapartment[i].subject +'</td>';
										s+='<td>'+ branchName +'</td>';
										s+='<td>'+ systemapartment[i].floorid + '0' + systemapartment[i].roomid + '</td>';										
										s+='<td>'+ systemapartment[i].bedroom +'</td>';
										s+='<td>'+ systemapartment[i].bathroom +'</td>';
										s+='<td>'+ systemapartment[i].price +'</td>';
										s+='<td>'+ systemapartment[i].deposit +'</td>';
										s+='<td>'+ systemapartment[i].area +'</td>';
										s+='<td>'+ systemapartment[i].description +'</td>';
										s+='<td><a href="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" target="_blank"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" height="100" width="90" alt=""></a></td>';
										s+=' <td><a onclick="return confirm(`Xóa căn hộ?`)" href="${pageContext.request.contextPath }/admin/systemapartment?action=deleteSystem&id='+ systemapartment[i].id +'"><i class="fa-solid fa-trash"></i></a></td>';										
										s+='</tr>';
									}

									$('#example2 tbody').html(s);
								}
							})
						});
					});
                </script>
                <div class="card-body">
                	<script>
								$(document).ready(function() {
									$('#buttonReload').click(function() {
										location.reload();
									});
								});
							
							
							</script>
						
				<%
                	HttpSession session2 = request.getSession();
                	String msg = (String) session2.getAttribute("msg");
                	String msg1 = msg;
                	session2.removeAttribute("msg");
                %>
                <c:if test="<%= msg1 != null %>">
                	<script>
                	alert('<%= msg1 %>');
                	</script>
                </c:if>
								<button id="buttonReload" class="btn" ><i class="fa-solid fa-rotate"></i></button>
                  <table style="text-align: center;" id="example2" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th style="vertical-align: top;">
                          Mã căn hộ
                        </th>
                        <th style="vertical-align: top;">
                          Tiêu đề
                          <br>
                          <input style="width: 140px;" type="text" name="subject" id="subject">
                        </th>
                        <th style="vertical-align: top;">Chi nhánh</th>
                        <th style="vertical-align: top;">Số phòng</th>
                        <th style="vertical-align: top;">Phòng ngủ</th>
                        <th style="vertical-align: top;">Phòng tắm</th>
                        <th style="vertical-align: top;">Giá</th>
                        <th style="vertical-align: top;">Đặt cọc</th>
                        <th style="vertical-align: top;">Diện tích</th>
                        <th style="vertical-align: top;">Mô tả</th>
                        <th style="vertical-align: top;">Ảnh căn hộ</th>
                        <th style="vertical-align: top;">Quản lý</th>
                       
                      </tr>
                    </thead>
                    <tbody>
                   <% for(Systemapartment systemapartment : systemApartmentModel.findAllInAdmin()) {%>
                    <tr>
                        <td><%= systemapartment.getId() %></td>
                        <td><%= systemapartment.getSubject() %></td>
                        <td><%= branchModel.findBranchByID(systemapartment.getBranchid()).getAddress() %></td>
                        <td><%= systemapartment.getFloorid() + "0" + systemapartment.getRoomid() %></td>
                        <td><%= systemapartment.getBedroom() %></td>
                        <td><%= systemapartment.getBathroom()%></td>
                        <td><%= systemapartment.getPrice() %> tỷ</td>
                        <td><%= systemapartment.getDeposit()%> triệu</td>
                        <td><%= systemapartment.getArea() %></td>
                        <td><%= systemapartment.getDescription() %></td>
                        <td><a href="${pageContext.request.contextPath}/assets/user/images/150canho/<%= systemapartment.getAvatar() %>" target="_blank"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/<%= systemapartment.getAvatar() %>" height="100" width="90" alt=""></a></td>
                        <td><a onclick="return confirm('Bạn có chắc muốn xóa căn hộ hệ thống ra khỏi danh sách?')" href="${pageContext.request.contextPath }/admin/systemapartment?action=deleteSystem&id=<%= systemapartment.getId() %>"><i class="fa-solid fa-trash"></i></a></td>
                      </tr>
                    
                      <% } %>
                      
                     </tbody>
                      
                  </table>
                </div>
                <!-- /.card-body -->
              </div>
              <!-- /.card -->


              <!-- /.card -->
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
      </section>
      <!-- /.content -->
    </div>