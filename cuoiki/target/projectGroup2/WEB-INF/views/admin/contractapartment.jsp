<%@page import="com.demo.models.OwnerModel"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.demo.entities.Systemapartment"%>
<%@page import="com.demo.entities.Contract"%>
<%@page import="com.demo.models.BranchModel"%>
<%@page import="com.demo.models.SystemApartmentModel"%>
<%@page import="com.demo.models.ContractModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%
	ContractModel contractModel = new ContractModel();
	OwnerModel ownerModel = new OwnerModel();
	SystemApartmentModel systemApartmentModel = new SystemApartmentModel();
	BranchModel branchModel = new BranchModel();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
%>
  <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <div class="container-fluid">
          <div class="row mb-2">
            <div class="col-sm-6">
              <h1>Danh sách hợp đồng</h1>
            </div>
            
          </div>
        </div><!-- /.container-fluid -->
      </section>
  
      <!-- Main content -->
      <section class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-12">
             
              <!-- /.card -->
  
              <div class="card">
                
                <!-- /.card-header -->
                <div class="card-body">
                  <table style="text-align: center;" id="example1" class="table table-bordered table-striped">
                    <thead>
                   <tr>
                      <th style="vertical-align: top;">Mã hợp đồng</th>
                      <th style="vertical-align: top;">Mã căn hộ</th>
                      <th style="vertical-align: top;">Ngày tạo hợp đồng</th>
                      <th style="vertical-align: top;">Mã chủ sở hữu</th>
                      <th style="vertical-align: top;">Trạng thái
                        <br>
                        <select id="inputStatus" class="form-control custom-select">
                          <option disabled selected>Loại hợp đồng</option>
                          <option>Tất cả</option>
                          <option>Còn hiệu lực</option>
                          <option>Hết hiệu lực</option>
                          
                        </select>
                      </th>
                      
                      <th>&nbsp;</th>
                      <th style="vertical-align: top;">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for(Contract contract : contractModel.findAll()) {%>
                    <tr>
                      <td><%= contract.getId() %></td>
                      <td><%= contract.getSystemapartmentid() + " - " +
                      		branchModel.findBranchByID(systemApartmentModel.findSystemApartmentByID(contract.getSystemapartmentid()).getBranchid()).getName() + " Phòng " + 
                      		systemApartmentModel.findSystemApartmentByID(contract.getSystemapartmentid()).getFloorid() + "0" + systemApartmentModel.findSystemApartmentByID(contract.getSystemapartmentid()).getRoomid()
                      %></td>
                      <td>23/01/2024</td>
                      <td><%= contract.getOwnerid() + " - " + ownerModel.findById(contract.getOwnerid()).getName() %></td>
                      <td><%= contract.isStatus() ? "Có hiệu lực" : "Không có hiệu lực"%></td>
                      <td><a href="${pageContext.request.contextPath}/admin/contractdetails?id=<%= contract.getId() %>">Xem chi tiết</a></td>
                      <td> <a onclick="return confirm('Vô hiệu hóa?')" href=""><i class="fa-solid fa-xmark"></i></a></td>
                    </tr>
                   	
                   	<% } %> 
                   
                    </tbody>
                  </table>
                </div>
                <!-- /.card-body -->
              </div>
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