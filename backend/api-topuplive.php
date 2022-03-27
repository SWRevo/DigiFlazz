<?php

$urlhost = "https://api.digiflazz.com/v1/transaction";
$userakun = "puxokag4EVGo";
$keyakun = "35b91b44-baac-5d5d-a6ae-d7de41180abb";


$sku_code = $_GET['sku_code'];
$customer_no = $_GET['customer_no'];
$ref_id = $_GET['ref_id'];
$msg = $_GET['msg'];

$signmd5 = md5($userakun.$keyakun.$ref_id);


$data = array(
    "username"=>$userakun,
    "buyer_sku_code"=>$sku_code,
    "customer_no"=>$customer_no,
    "ref_id"=>$ref_id,
    "sign"=>$signmd5,
    "testing"=>false,
    "msg"=>$msg
    );
    
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

?>