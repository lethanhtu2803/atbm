<%@page import="java.net.http.HttpRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RSA Key Management</title>
<style>
body {
	font-family: Arial, sans-serif;
	padding: 20px;
}

.container {
	display: flex;
	gap: 20px;
}

.key-box {
	border: 1px solid #ccc;
	padding: 10px;
	border-radius: 5px;
	width: 300px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.key-box h3 {
	margin: 0 0 10px;
	font-size: 16px;
}

textarea {
	width: 100%;
	height: 100px;
	resize: none;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
	padding: 5px;
	font-family: inherit;
}

button {
	padding: 8px 12px;
	background-color: #007BFF;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 14px;
}

button:hover {
	background-color: #0056b3;
}

.generate-key {
	margin-top: 20px;
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<!-- Public Key Section -->
			<form action="${pageContext.request.contextPath}/key" method="post">
			<div class="key-box">
				<h3>Public Key</h3>
				<textarea id="publicKey" name="publicKey"
					placeholder="Public Key will appear here...">${publicKey}</textarea>
				<label for="publicKeyFile">Upload Public Key:</label> <input
					type="file" id="publicKeyFile" accept=".txt"
					onchange="loadPublicKey(event)">
			</div>
		
				<input type="hidden" name="action" value="saveKey"> <input
					type="submit" value="Lưu key và đến trang đăng nhập">
			</form>
			<!-- Private Key Section -->
			<div class="key-box">
				<h3>Private Key</h3>
				<textarea id="privateKey" name="privateKey"
					placeholder="Private Key will appear here...">${privateKey}</textarea>
				<label for="privateKeyFile">Upload Private Key:</label> <input
					type="file" id="privateKeyFile" accept=".txt"
					onchange="loadPrivateKey(event)">
					<button onclick="downloadPrivateKey()">Download Private Key</button>
			</div>
	</div>

	

	<form action="${pageContext.request.contextPath}/key" method="get">
		<input type="hidden" name="action" value="genKey"> <input
			type="submit" value="GEN KEY">
	</form>

	<script>
		// Load the content of the uploaded public key file into the textarea
		function loadPublicKey(event) {
			const file = event.target.files[0]; // Get the selected file
			if (!file) {
				alert("No file selected!");
				return;
			}

			const reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('publicKey').value = e.target.result; // Set the file content in the textarea
			};
			reader.onerror = function() {
				alert("Failed to read the file!");
			};

			reader.readAsText(file); // Read the file as text
		}

		// Load the content of the uploaded private key file into the textarea
		function loadPrivateKey(event) {
			const file = event.target.files[0]; // Get the selected file
			if (!file) {
				alert("No file selected!");
				return;
			}

			const reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('privateKey').value = e.target.result; // Set the file content in the textarea
			};
			reader.onerror = function() {
				alert("Failed to read the file!");
			};

			reader.readAsText(file); // Read the file as text
		}

		function saveKey(type) {
			const key = type === 'public' ? document
					.getElementById('publicKey').value : document
					.getElementById('privateKey').value;

			if (!key) {
				alert(`No ${type} key to save!`);
				return;
			}

			const blob = new Blob([ key ], {
				type : 'text/plain'
			});
			const link = document.createElement('a');
			link.href = URL.createObjectURL(blob);
			link.download = `${type}_key.txt`;
			link.click();
		}

		function generateKeyPair() {
			// Simulate key generation (backend is required for real generation)
			document.getElementById('publicKey').value = "Generated Public Key...";
			document.getElementById('privateKey').value = "Generated Private Key...";
			alert("RSA Key Pair has been generated!");
		}
		function downloadPrivateKey() {
			const privateKey = document.getElementById('privateKey').value;
			
			if (!privateKey) {
				alert('No private key to download!');
				return;
			}

			const blob = new Blob([privateKey], { type: 'text/plain' });
			const link = document.createElement('a');
			link.href = URL.createObjectURL(blob);
			link.download = 'privateKey.txt';
			link.click();
		}
	</script>
</body>
</html>
