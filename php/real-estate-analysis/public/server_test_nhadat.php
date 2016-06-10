<?php
define("HTTP_USER", sha1("nhadat@nhadat.com"));
define('HTTP_PWD', null);
define('BASE_URL', 'http://d.me2be.vn');

set_time_limit(1000000);
$uri = '/rest/v1/city/cityFilter';
$arg = array(
    'city'=>'Hải',
    'district'=>'Quận Ngô Quyền',
    'ward'=>'Phường',
    'street'=>''
);
HttpHelper('GET',$arg,$uri,HTTP_USER,HTTP_PWD); die();


$uri = '/rest/v1/city/searchByKey';
$arg = array(
    'key'=>'Hải',
);
//HttpHelper('GET',$arg,$uri,HTTP_USER,HTTP_PWD); die();

$uri = '/rest/v1/city/searchByUser';
$arg = array(
    'name'=>'tran dinh danh',
    'phone'=>'0978505xxx',
);
HttpHelper('GET',$arg,$uri,HTTP_USER,HTTP_PWD); die();
function makeSignature($method, $url, $Args = array())
{
    ksort($Args);

    $method = strtoupper($method);
    $signature = '';
    return rawurlencode(base64_encode($signature));
}

function HttpHelper($method, $args, $uri, $user, $pwd)
{
//    echo $user.'-'.$pwd;
    $method = strtoupper($method);

    $signature = makeSignature($method, $uri, $args);
//    echo $signature;
    $url_signature = BASE_URL . $uri;

    $args['signature'] = $signature;

    $fields_string = http_build_query($args);


    //echo $fields_string;
    if ($method == 'GET')
        $url_signature .= '?' . $fields_string;
    //$url_signature .='?'.$fields_string;
    // var_dump($url_signature);die;

    $curl = curl_init($url_signature);

//    echo $url_signature;
    // BaoKim with Signture
    // $curl = curl_init($url . 'signature='.$signature);


    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_TIMEOUT, 30);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
    // curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($curl, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($curl, CURLOPT_USERPWD, $user . ':' . $pwd);

    if ($method == 'POST') {
        curl_setopt($curl, CURLOPT_POST, sizeof($args));
        curl_setopt($curl, CURLOPT_POSTFIELDS, $fields_string);
    }
    if ($method == 'PUT') {
        curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT");
        curl_setopt($curl, CURLOPT_POSTFIELDS, $fields_string);
    }

    //@curl_setopt($curl, CURLOPT_HTTPHEADER, "Authorization: Basic " . base64_encode(HTTP_USER . ":" . HTTP_PWD));
    // Lấy Data
    $data = curl_exec($curl);
    $error = curl_error($curl);
    //var_dump($error);
    $httpStatus = curl_getinfo($curl);
//    echo '<pre>';print_r($httpStatus);echo '</pre>';
    print_r($data);
}

function HttpHelperUpload($method, $args, $uri, $user, $pwd, $file)
{
    $tmpfile = $file['tmp_name'];
    $filename = basename($file['name']);
    $filetype = $file['type'];
    $filesize = $file['size'];
    $args['file'] = $tmpfile;
    $args['fileName'] = $filename;
    $method = strtoupper($method);
    $headers = array("Content-Type:multipart/form-data");
    $signature = makeSignature($method, $uri, $args);
    $url_signature = BASE_URL . $uri;
    $args['signature'] = $signature;

    $curl = curl_init();
    //echo $url_signature;

    // Vì chúng ta đang gửi file nên header của nó
    // phải ở dạng Content-Type:multipart/form-data
    curl_setopt($curl, CURLOPT_URL, $url_signature);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($curl, CURLOPT_TIMEOUT, 30);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);

    // Thiết lập có gửi file và thông tin file 
    curl_setopt($curl, CURLOPT_INFILESIZE, $filesize);

    // Cấu hình return
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

    curl_setopt($curl, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($curl, CURLOPT_USERPWD, $user . ':' . $pwd);

    if ($method == 'POST') {
        curl_setopt($curl, CURLOPT_POST, 1);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $args);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    }
    // Lấy Data
    $data = curl_exec($curl);
    $error = curl_error($curl);
    //var_dump($error);
    $httpStatus = curl_getinfo($curl);
    //echo '<pre>';print_r($httpStatus);echo '</pre>';
//    print_r($data);
    $data = json_decode($data);
    print_r($data->data);
}

?>