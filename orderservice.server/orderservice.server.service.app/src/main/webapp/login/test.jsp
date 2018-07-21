<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="jquery-2.1.4.js" type="text/javascript"></script>
<script type="text/javascript" src="socket.io.js"></script>
    <style>
        body { 
            padding:20px;
        }
        #console { 
            height: 400px; 
            overflow: auto; 
        }
        .username-msg {color:orange;}
        .connect-msg {color:green;}
        .disconnect-msg {color:red;}
        .send-msg {color:#888}
    </style>
</head>
<body>
    <h1>Netty-socketio示例</h1>
    <br/>
    <div id="console" class="well">
    </div>
        <form class="well form-inline" onsubmit="return false;">
           <input id="name" class="input-xlarge" type="text" placeholder="用户名称..."/>
           <input id="msg" class="input-xlarge" type="text" placeholder="发送内容..."/>
           <button type="button" onClick="sendMessage()" class="btn">Send</button>
           <button type="button" onClick="sendDisconnect()" class="btn">Disconnect</button>
        </form>
</body>
<script type="text/javascript">
		var url= 'http://192.168.124.8:10087/app/service?x=lslakl';
		
        var socket =  io.connect(url);
		
        socket.on('connect', function() {
            output('<span class="connect-msg">Client has connected to the server!</span>');
        });
        
        socket.on('login', function(data) {
            output('<span class="username-msg">' + data+JSON.stringify(data) + '</span> ' );
        });
        
        socket.on('disconnect', function() {
            output('<span class="disconnect-msg">The client has disconnected!</span>');
        });
        
        function sendDisconnect() {
            socket.disconnect();
        }
        
        function sendMessage() {
            var userName = $("#name").val()
            var message = $('#msg').val();
            $('#msg').val('');
            socket.emit('login', JSON.stringify({userName: userName, message: message}));
        }
        
        function output(message) {
            var currentTime = "<span class='time'>" +  new Date() + "</span>";
            var element = $("<div>" + currentTime + " " + message + "</div>");
            $('#console').prepend(element);
        }
    </script>
</html>