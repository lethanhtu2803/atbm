<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page import="com.demo.entities.Systemapartment"%>
<%@page import="com.demo.models.SystemApartmentModel"%>
<%@page import="com.demo.models.BranchModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
         <%
	
	HttpSession httpSession = request.getSession();
	if(httpSession.getAttribute("language")== null){
		request.getSession().setAttribute("language", "vi");
		
	}
	String language = httpSession.getAttribute("language").toString();
	ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(language));
%>
 <%
   BranchModel branchModel = new BranchModel();
   SystemApartmentModel systemapartment = new SystemApartmentModel();

%>
 <div class="page-heading header-text">
    <div class="container">
      <div class="row">
        <div class="col-lg-12">
          <span class="breadcrumb"><a href="${pageContext.request.contextPath}/home"><%= messages.getString("trang_chu") %></a> / <%= messages.getString("can_ho_he_thong") %></span>
          <h3><%= messages.getString("can_ho_he_thong") %></h3>
        </div>
      </div>
    </div>
  </div>
<script>
$(document).ready(function(){
	// Lọc theo vị trí
	$('#district1').click(function() {
		var district = $(this).val();
		console.log(district);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByDistrict',
				district : district
			},
			success: function(systemapartment) {
				var branchName = '';
				var s = '';
				for(var i = 0;i < systemapartment.length;i++) {
					if(systemapartment[i].branchid == 1) {
						branchName = '123 Nguyễn Văn Cừ';
					}
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#district2').click(function() {
		var district = $(this).val();
		console.log(district);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByDistrict',
				district : district
			},
			success: function(systemapartment) {
				var branchName = '';
				var s = '';
				for(var i = 0;i < systemapartment.length;i++) {
					if(systemapartment[i].branchid == 2) {
						branchName = '456 Hoàng Văn Thụ';
					}
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#district3').click(function() {
		var district = $(this).val();
		console.log(district);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByDistrict',
				district : district
			},
			success: function(systemapartment) {
				var branchName = '';
				var s = '';
				for(var i = 0;i < systemapartment.length;i++) {
					if(systemapartment[i].branchid == 3) {
						branchName = '35 Hoàng Diệu';
					}
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#district4').click(function() {
		var district = $(this).val();
		console.log(district);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByDistrict',
				district : district
			},
			success: function(systemapartment) {
				var branchName = '';
				var s = '';
				for(var i = 0;i < systemapartment.length;i++) {
					if(systemapartment[i].branchid == 4) {
						branchName = '11 Võ Văn Ngân';
					}
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});
	
	$('#district5').click(function() {
		var district = $(this).val();
		console.log(district);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByDistrict',
				district : district
			},
			success: function(systemapartment) {
				var branchName = '';
				var s = '';
				for(var i = 0;i < systemapartment.length;i++) {
					if(systemapartment[i].branchid == 5) {
						branchName = '88 Hoàng Hoa Thám';
					}
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});
	// Lọc theo giá
	$('#price1').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPrice',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#price2').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPrice',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#price3').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPrice',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#price4').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPrice',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#price5').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPrice',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#priceOver5').click(function() {
		var price = $(this).val();
		price = price.substring(4);
		price = price.substring(1,3);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByPriceOver5',
				price : price
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	// Lọc theo phòng ngủ 
	$('#bedroom1').click(function() {
		var bedroom = $(this).val();
		bedroom = bedroom.substring(0,1);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByBedroom',
				bedroom : bedroom
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#bedroom2').click(function() {
		var bedroom = $(this).val();
		bedroom = bedroom.substring(0,1);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByBedroom',
				bedroom : bedroom
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	$('#bedroom3').click(function() {
		var bedroom = $(this).val();
		bedroom = bedroom.substring(0,1);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByBedroom',
				bedroom : bedroom
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});

	// Lọc theo diện tích
	$('#rangeArea').click(function() {
		var area = $(this).val();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			url: '${pageContext.request.contextPath}/systemapartment',
			data: {
				action: 'searchByArea',
				area : area
			},
			success: function(systemapartment) {
				var branchName = '';
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
					s+= '<div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">';
					s+= '<div class="item">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/'+ systemapartment[i].avatar +'" alt=""></a>';
					s+= '<a href="${pageContext.request.contextPath}/wishlist?action=wishlist&id='+ systemapartment[i].id +'"><span class="category"><i class="fa-solid fa-heart-circle-plus" style="color:#f35525;font-size: 20px;"></i></span></a>';
					s+= '<h6>'+ systemapartment[i].price +' tỷ VNĐ</h6>';
					s+= '<h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">'+ systemapartment[i].subject +'</a></h4>';
					s+= '<ul>';
					s+= '<li>Số phòng: <span>'+ systemapartment[i].floorid + ' 0 '+ systemapartment[i].roomid + '</span></li>';
					s+= '<li>Phòng ngủ: <span>'+ systemapartment[i].bedroom + '</span></li>';
					s+= '<li>Phòng tắm: <span>'+ systemapartment[i].bathroom + '</span></li>';
					s+= '<li>Diện tích: <span>'+ systemapartment[i].area + ' m2</span></li>';
					s+= '<li>Địa chỉ: <span>'+ branchName + '</span></li>';
					s+= '</ul>';
					s+= '<div class="main-button">';
					s+= '<a href="${pageContext.request.contextPath}/systemapartmentdetails?id='+ systemapartment[i].id +'">Chi tiết</a>';
					s+= '</div>';
					s+= '</div>';
					s+= '</div>';
				}
				if(systemapartment.length == 0) {
					s += '<h2 style="display: flex;justify-content: center;margin-top: 120px;">Không tìm thấy căn hộ !</h2>';
				}
				$('#result').html(s);
				
			}
		});
	});
	
	$(document).ready(function() {
		$('#buttonReload').click(function() {
			location.reload();
		});
	});
});
</script>
  <div class="section properties">
    <div class="container" id="toHeader" >
      <div style="display: flex; margin-left: 250px; margin-bottom: 20px;" >
        <div class="dropdown">
          <button style="width: 160px;" class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            <%= messages.getString("vi_tri") %>
          </button>
          <ul class="dropdown-menu">
            <li><input type="button" class="dropdown-item" value="Quận 1" id="district1"></li>
            <li><input type="button" class="dropdown-item" value="Quận 2" id="district2"></li>
            <li><input type="button" class="dropdown-item" value="Quận 3" id="district3"></li>
            <li><input type="button" class="dropdown-item" value="Quận 4" id="district4"></li>
            <li><input type="button" class="dropdown-item" value="Quận 5" id="district5"></li>
          </ul>
        </div>
        <div class="dropdown" style="margin-left: 50px;">
          <button style="width: 160px;" class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            <%= messages.getString("theo_gia") %>
          </button>
          <ul class="dropdown-menu">
            <li><input type="button" class="dropdown-item" value="Dưới 1 tỷ" id="price1"></li>
            <li><input type="button" class="dropdown-item" value="Dưới 2 tỷ" id="price2"></li>
            <li><input type="button" class="dropdown-item" value="Dưới 3 tỷ" id="price3"></li>
            <li><input type="button" class="dropdown-item" value="Dưới 4 tỷ" id="price4"></li>
            <li><input type="button" class="dropdown-item" value="Dưới 5 tỷ" id="price5"></li>
            <li><input type="button" class="dropdown-item" value="Trên 5 tỷ" id="priceOver5"></li>
          </ul>
        </div>
        <div class="dropdown" style="margin-left: 50px;">
          <button style="width: 160px;" class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            <%= messages.getString("phong_ngu") %>
          </button>
          <ul class="dropdown-menu">
            <li><input type="button" class="dropdown-item" value="1 <%= messages.getString("phong") %>" id="bedroom1"></li>
            <li><input type="button" class="dropdown-item" value="2 <%= messages.getString("phong") %>" id="bedroom2"></li>
            <li><input type="button" class="dropdown-item" value="3 <%= messages.getString("phong") %>" id="bedroom3"></li>
          </ul>
        </div>
       <label for="customRange2" class="form-label text-secondary fs-5 mx-4" style="position: relative;top: -35px;left: 90px;"><%= messages.getString("dien_tich") %></label>
	   <input id="rangeArea" class="range" type="range" min="0" max="100" value="0" step="1" onmousemove="rangevalue1.value=value" style="width: 300px;margin-left:-10px;"/>
	   <output id="rangevalue1" style="margin-top: 21px;margin-left: 2px; "></output>
			
			<button id="buttonReload" class="btn" style="margin-left: 8px;border: none; "><i class="fa-solid fa-rotate"></i></button>
      </div>
      <div class="row properties-box" id="result">
			<% for(Systemapartment system : systemapartment.findAll()){ %>
        	  <div class="col-lg-4 col-md-6 align-self-center mb-30 properties-items col-md-6">
                <div class="item">
                  <a href="${pageContext.request.contextPath}/systemapartmentdetails?id=<%= system.getId() %>"><img src="${pageContext.request.contextPath}/assets/user/images/150canho/<%=system.getAvatar() %>" alt=""></a>
                  <span class="category"><i class="fa-solid fa-building" style="color:#f35525;font-size: 20px;"></i></span></a>
                  
                  <h6><%= system.getPrice() %> <%= messages.getString("ty") %> VNĐ</h6>
                  <h4><a href="${pageContext.request.contextPath}/systemapartmentdetails?id=<%= system.getId() %>"><%= system.getSubject() %></a></h4>
                  <ul>
                  <li><%= messages.getString("so_phong") %>: <span><%= system.getFloorid() %> 0 <%= system.getRoomid() %></span></li>
                     <li><%= messages.getString("phong_ngu") %>: <span><%= system.getBedroom() %></span></li>
              <li><%= messages.getString("phong_tam") %>: <span><%= system.getBathroom() %></span></li>
              <li><%= messages.getString("dien_tich") %>: <span><%= system.getArea() %> m2</span></li>
              <li><%= messages.getString("dia_chi") %>: <span><%= branchModel.findBranchByID(system.getBranchid()).getAddress() %></span></li>
                  </ul>
                  <div class="main-button">
                    <a href="${pageContext.request.contextPath}/systemapartmentdetails?id=<%= system.getId() %>"><%= messages.getString("chi_tiet") %></a>
                  </div>
                </div>
          </div>
        	<% } %>
      </div>
    
        </div>
        <div class="row">
          <div class="col-lg-12">
            <ul class="pagination">
              <!-- <li><a class="is_active" href="#">1</a></li>
              <li><a  href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">>></a></li> -->
            </ul>
          </div>
        </div>
    </div>
  </div>
 