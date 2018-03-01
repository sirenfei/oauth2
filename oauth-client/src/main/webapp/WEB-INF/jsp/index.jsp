<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>11111</title>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<style>
  .hidden {
    display: none;
  }
</style>
</head>
<body>
<script type="text/javascript" charset="utf-8">
$(document).ready(function(){

    var extractToken = function(hash) {
        var match = hash.match(/access_token=(.+)&token_type/);
        return !!match && match[1];
    };

    var clientId = "123456";
    var AUTHORIZATION_ENDPOINT = "http://localhost:8888/oauth/authorize";
    var RESOURCE_ENDPOINT = "http://localhost:8889/client/implicitdemo";
    var token = extractToken(document.location.hash);
    
    if(!token) {
        $('div.authenticate').show();
        var str = AUTHORIZATION_ENDPOINT + "?response_type=token&client_id="+ clientId; 
        $("a.connect").attr("href", str);
     }
    else
    	{
        $('div.authenticated').show();

        $('span.token').text(token);

        $.ajax({
            url: RESOURCE_ENDPOINT,
            beforeSend: function (xhr) {
              xhr.setRequestHeader('Authorization', " Bearer " + token);
              xhr.setRequestHeader('Accept',        "application/json");
            }
          , success: function (response) {
              var container = $('span.user');
              if (response) {
                container.text(response.hello);
              } else {
                container.text("An error occurred.");
              }
            }
        });
    	}
    

});


</script>

<div class="authenticate hidden">
  <a class="connect" href="">Connect</a>
</div>

<div class="authenticated hidden">
  <p>
    You are using token
    <span class="token">[no token]</span>
  </p>

  <p>
    Your SoundCloud username is
    <span class="user">[no username]</span>
  </p>
</div>
</body>
</html>
