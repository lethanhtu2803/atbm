package com.demo.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.demo.entities.Account;
import com.demo.entities.Key;
import com.demo.helpers.MD5;
import com.demo.helpers.RSA;
import com.demo.models.KeyModel;

/**
 * Servlet implementation class RSAServlet
 */
@WebServlet("/key")
@MultipartConfig
public class KeyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public KeyServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
        	   Account account = (Account) request.getSession().getAttribute("account");
        		if(account == null) {
        			request.getSession().setAttribute("msg", "Bạn chưa đăng nhập tài khoản");
        			response.sendRedirect("login");
        		} else {
        			request.getRequestDispatcher("/WEB-INF/views/login/key.jsp").forward(request, response);
        		}
        
        } else {
            try {
                if ("genKey".equals(action)) {
                    doGet_GenKey(request, response);
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet_GenKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048); // Kích thước khóa: 2048 bit
        
        // Tạo cặp khóa (KeyPair)
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        // Lấy Public Key và Private Key
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        // Mã hóa Public Key và Private Key thành Base64
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        
        // Set Public Key và Private Key vào request attributes để hiển thị trên JSP
        request.setAttribute("publicKey", publicKeyString);
        request.setAttribute("privateKey", privateKeyString);
        
        request.getRequestDispatcher("/WEB-INF/views/login/key.jsp").forward(request, response);
    }

    protected void doPost_SavePrivateKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	KeyModel keyModel = new KeyModel();
        // Lấy private key từ session hoặc request attributes (giả sử đã được tạo từ doGet_GenKey)
        String privateKeyString = (String) request.getParameter("privateKey");
        String publicKey = (String) request.getParameter("publicKey");
      
        // Kiểm tra nếu private key không có giá trị
        if (privateKeyString == null || privateKeyString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Private Key chưa được tạo");
            return;
        }

        // Chuyển đổi private key sang định dạng byte
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);

        // Đặt các header để trình duyệt tải tệp về
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=privateKey.txt");

        // Ghi private key vào output stream của response (tạo tệp tải về)
        try (OutputStream os = response.getOutputStream()) {
            // Chuyển private key bytes sang định dạng String (dạng Base64) trước khi ghi vào file
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
            os.write(privateKeyBase64.getBytes());  // Ghi Base64 vào file dưới dạng chuỗi
            os.flush();
        
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tạo file private key");
        }
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        	String action = request.getParameter("action");
        	if(action.equals("loadPrivateKey")) {
        		doPost_UploadPrivateKey(request, response);
        	} else if(action.equals("savePrivateKey")){
        		doPost_SavePrivateKey(request, response);
        	} else if(action.equals("encryptText")) {
        		doPost_EncryptText(request, response);
        	} else if(action.equals("decryptTextWithPublicKey")) {
        		doPost_DecryptTextWithPublicKey(request, response);
        	} else if(action.equals("hashMD5")) {
        		doPost_HashMD5(request, response);
        	} else if(action.equals("saveKey")) {
        		doPost_SaveKey(request, response);
        	}
            	
            
        }
        protected void doPost_UploadPrivateKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
            
        	System.out.println("Load private Key");
            // Đọc private key từ tệp tải lên
            Part filePart = request.getPart("privateKeyFile");
            if (filePart != null) {
                // Đọc dữ liệu từ tệp và chuyển đổi thành chuỗi
                InputStream fileContent = filePart.getInputStream();
                String privateKey = new String(fileContent.readAllBytes(), StandardCharsets.UTF_8);
                
                // Gửi private key về trang JSP để hiển thị
                request.setAttribute("privateKeyUpload", privateKey);
                request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
            }
        
    }
        protected void doPost_EncryptText(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");

            if ("encryptText".equals(action)) {
                String textToEncrypt = request.getParameter("textToEncrypt");
                String privateKey = request.getParameter("privateKey"); // Nhận private key từ request

                try {
                    RSA rsa = new RSA();
                    String encryptedText = rsa.encryptWithPrivateKey(textToEncrypt, privateKey); // Dùng private key để mã hóa
                    request.setAttribute("encryptedText", encryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Forward lại về JSP để hiển thị kết quả
                request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
            }
        }

        protected void doPost_DecryptTextWithPublicKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            RSA rsa = new RSA();
            // Handle Decrypt Text with Public Key

            String dataToDecrypt = request.getParameter("dataToDecrypt");
            String publicKey = request.getParameter("publicKey"); // Nhận public key từ request
            String decryptedText = null;

            try {
                decryptedText = rsa.decryptWithPublicKey(dataToDecrypt, publicKey); // Dùng public key để giải mã
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("decryptedText", decryptedText); // Set kết quả giải mã làm attribute
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response); // Forward tới JSP
        }
        protected void doPost_HashMD5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        	MD5 md5 = new MD5();
            String dataToEncrypt = request.getParameter("dataToEncrypt");
          
            String encryptText = null;

            try {
                encryptText = md5.hashMD5(dataToEncrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("hashMD5Text", encryptText); // Set kết quả giải mã làm attribute
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response); // Forward tới JSP
        }
        protected void doPost_SaveKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String publicKey = request.getParameter("publicKey");
            Account account = (Account) request.getSession().getAttribute("account");

            System.out.println(publicKey);
            KeyModel keyModel = new KeyModel();

            List<Key> existKey = keyModel.findKeysByAccountID(account.getId());
            boolean isUpdated = false;

            if (existKey != null && !existKey.isEmpty()) {
                Key lastKey = existKey.get(existKey.size() - 1);
                lastKey.setStatus(false);
                lastKey.setEndTime(new Date());

                System.out.println(lastKey);

                if (!keyModel.updateKey(lastKey)) {
                    System.out.println("Lỗi: Không thể cập nhật key");
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể cập nhật key");
                    return;
                }
                isUpdated = true;
            }

            Key newKey = new Key(account.getId(), publicKey, new Date(), null, true);
            if (keyModel.create(newKey)) {
                System.out.println("Key mới đã được tạo" + (isUpdated ? " và cập nhật thành công" : ""));
                response.sendRedirect("login");
            } else {
                System.out.println("Lỗi: Không thể tạo key mới");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo key mới");
            }
        }

    

}
