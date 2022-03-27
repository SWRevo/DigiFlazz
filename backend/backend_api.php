<?php

$urlhost = $_POST['url_host'];
$userakun = $_POST['username'];
$keyakun = $_POST['key'];
$cmd = $_POST['cmd'];
$stringbuildsign = $_POST['str_buildsign'];
$signakun = $userakun.$keyakun.$stringbuildsign;
$signmd5 = md5($signakun);


$data = array("cmd" => $cmd,"username" => $userakun,"sign"=>$signmd5);
$postdata = json_encode($data);

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => $urlhost,
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => $postdata,
  CURLOPT_HTTPHEADER => array(
    "cache-control: no-cache",
    "content-type: application/json"
  ),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
  echo "cURL Error #:" . $err;
} else {
  echo $response;
}