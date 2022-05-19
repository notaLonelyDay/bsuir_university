<?php
$arrContextOptions=array(
    "ssl"=>array(
        "verify_peer"=>false,
        "verify_peer_name"=>false,
    ),
);
$main_rul = "https://snipp.ru/tags/svg";
$ch = curl_init($main_rul);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_HEADER, false);
$html = curl_exec($ch);
curl_close($ch);


$path = dirname(__FILE__) . '/download';
 
$external = true;
 
preg_match_all('/<img.*?src=["\'](.*?)["\'].*?>/i', $html, $images, PREG_SET_ORDER);
 
$url = parse_url($main_rul);
$path = rtrim($path, '/');
 
foreach ($images as $image) { 
 
	$ext = strtolower(substr(strrchr($image[1], '.'), 1));
	if (in_array($ext, array('jpg', 'jpeg', 'png', 'gif'))) {
		$img = parse_url($image[1]);

		$path_img = $path . '/' .  dirname($img['path']);
		if (!is_dir($path_img)) {
			mkdir($path_img, 0777, true);
		}
 
		if (empty($img['host']) && !empty($img['path'])) {
			copy($url['scheme'] . '://' . $url['host'] . $img['path'], $path . $img['path'], stream_context_create($arrContextOptions));
		} elseif ($external || ($external == false && $img['host'] == $url['host'])) {
			copy($image[1], $path . $img['path'], stream_context_create($arrContextOptions));
		}
	}
}