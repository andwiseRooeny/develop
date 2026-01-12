<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLConnection"%>
<%@ page import="java.net.HttpURLConnection "%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>

<%
	/*
	 * UCM Lite 연동
	 *
	 * 필수값 : 
	 *		  ssoUrl = "UCM Lite SSO URL"
	 * 		  key = "키값"
	 *		  userid = "계정" 	// 학교 ID ( 학번 / 교번 )
	 * 		  userclass = "F" 	// F(교직원) 또는 S(학생)
	 */

	/*
	 * ※담당자 연락처
	 * #클라우드솔루션팀 김광석 부장(kskim@corebd.co.kr)
	 * #개발팀 천 현 차장(seeno@corebd.co.kr)
	 */

	// [필수값, 고정] SSO 도메인 정보
	String ssoDomain = "ucm.u1.ac.kr";
	
	// [필수값, 고정] SSO 연동 url
	String ssoUrl = "https://" + ssoDomain;

	// [필수값, 고정] Key값
	String key = "fc1d57aa-33da-4b56-baee-ac5753b4ef82";
	
	// [필수값] 계정
	String userid = "test";

	// [필수값] 사용자구분
	String userclass = "F";
	
	// [선택값] office365 계정 생성 가능 여부  ( Y : 생성 가능 , N : 생성 불가능 )
	String office365 = "Y";
	
	// [선택값] gsuite 계정 생성 가능 여부 ( Y : 생성 가능 , N : 생성 불가능 )
	String gsuite = "Y";
	
	/*
	* 이하 소스 부분은 수정하지 마세요. 오류가 발생 할 수 있습니다.
	*/
	HttpURLConnection connection = null;
	BufferedReader reader = null;
	StringBuffer param = new StringBuffer();
	StringBuffer result = new StringBuffer();
	String line = null;

	String returnUrl = "";

	String err = "";
	boolean submitYn = true;
		
	try 
	{
		param.append("api_key=");
		param.append(key);
		param.append("&");
		param.append("user_id=");
		param.append(userid);
		param.append("&");
		param.append("user_class=");
		param.append(userclass);
		param.append("&");
		param.append("office365=");
		param.append(office365);
		param.append("&");
		param.append("gsuite=");
		param.append(gsuite);
		
		byte[] postDataBytes = param.toString().getBytes("UTF-8");

		URL url = new URL("https://" + ssoDomain + ":443");

		// JDK 버젼이 1.6 이하 또는 Handshake_failure 오류 , protocol version 오류 발생 시 위 값을 주석 처리 후 아래 값으로 변경해주세요.
		// URL url = new URL("http://" + ssoDomain + ":8001");

		// Connection 오류 또는 Time out 오류 일시 학교 측 WAS 서버에서 아웃바운드가 막힌 것이므로, 52.231.202.71 방화벽 허용 요청드립니다.
    	connection = (HttpURLConnection) url.openConnection();
    	connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.getOutputStream().write(postDataBytes); 

    	// for jdk 1.4 이하
    	//System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
    	//System.setProperty("sun.net.client.defaultReadTimeout", "5000");

    	// for jdk 1.5 이상
    	connection.setConnectTimeout(5000);
    	connection.setReadTimeout(5000);

    	reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

	    while ((line = reader.readLine()) != null) {
	    	result.append(line);
	    }

    	if(!result.toString().startsWith("<")){
    		returnUrl = result.toString().trim();
    	}
	} 
	catch (Exception e) 
	{
			// jdk 1.4 이상
	    	StringBuffer err1 = new StringBuffer();
			String separator = "<br />";

			StackTraceElement[] stes = e.getStackTrace();

			for (int i = 0; i < stes.length; i++) {
				// jdk 1.8 이상일시 if문 주석
				if (i == 0) {
					err1.append(separator);
					err1.append(e);
					err1.append(separator);
				}

				err1.append(stes[i]);
				err1.append(separator);
			}

			err = err1.toString();

			// jdk 1.3 이하
			//err = e.getMessage();

			// jdk 1.3 이하인 경우 에러 내용이 불명확 할 경우 아래 내용 주석풀고 해당 서버 log로 확인 해야한다.
			//e.printStackTrace();

			submitYn = false;
	} 
	finally 
	{
		if(reader != null){
			try{
				reader.close();
			} catch(Exception e){

			}
		}

	    if (connection != null) {
	        try{
	        	connection.disconnect();
	        } catch(Exception e){

	        }
	    }
	}

%>

<% if(submitYn) { %>
<!DOCTYPE HTML>
<html>

<head></head>

<body>
	<script type="text/javascript">
		location.href = "<%=ssoUrl%><%=returnUrl%>";
	</script>
</body>

</html>
<% } else { %>
<!DOCTYPE HTML>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>SSO Guide</title>
	<style>
		.Wrap {
			position: fixed;
			top: 40px;
			left: 50%;
			margin: 0 0 0 -400px;
			width: 800px;
		}

		.p_error {
			margin: 30px 0;
			padding: 0
		}

		.p_error_txt {
			font-size: 19px;
			text-align: center;
			color: #000;
			margin: 0 0 10px;
			font-weight: 600;
		}

		.p_error p {
			color: #d52f00;
			font-size: 17px;
			text-align: center
		}

		.text {
			max-height: 400px;
			overflow-y: auto;
			/*border-top:1px solid #ccc;*/
			border: 1px solid #ccc;
			border-color: #dcdcdc #e7e7e7 #e7e7e7 #dcdcdc;
			background: #efefef;
			margin: 45px 10px 0 10px;
			padding: 18px;
			line-height: 170%;
			font-size: 14px;
			color: #333;
			border-radius: 5px;
		}
	</style>
</head>

<body>
	<div class="Wrap">
		<div class="p_error">
			<div class="p_error_txt">웹 페이지에 오류가 발생하였습니다.</div>
			<div class="text">
				<%=err %>
			</div>
		</div>
	</div>
</body>

</html>
<% } %>