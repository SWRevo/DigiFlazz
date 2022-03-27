<?php
// membaca data1, data2, dan data3 dari form
$urlhost = $_POST['url_host'];
$commands = $_POST['commands'];
$customer_no = $_POST['customer_no'];

$data = array("commands" => $commands,"customer_no" => $customer_no);
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