<!DOCTYPE html>
<html>
<head>
	<title>Form DigiFlazz</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<br/>
	<br/>
	<center><h2>Cek Saldo DigiFlazz By INDOSW.COM</h2></center>	
	<br/>
	<div class="login">
	<br/>
		<form action="api_ceksaldo.php" method="get" onSubmit="return validasi()">
			<div>
				<label>URL Host:</label>
				<input type="text" name="url_host" id="url_host" />
			</div>
			<div>
				<label>Username:</label>
				<input type="text" name="username" id="username" />
			</div>
			<div>
				<label>Key:</label>
				<input type="text" name="key" id="key" />
			</div>			
			<div>
				<input type="submit" value="SUBMIT" class="tombol">
			</div>
		</form>
	</div>
</body>
 
<script type="text/javascript">
	function validasi() {
		var username = document.getElementById("username").value;
		var key = document.getElementById("key").value;		
		if (username != "" && key!="") {
			return true;
		}else{
			alert('Username dan Key harus di isi !');
			return false;
		}
	}
 
</script>
</html>