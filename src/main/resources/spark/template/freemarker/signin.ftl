<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>

<#if message??>
  <div id="message" class="${message.type}">${message.text}</div>
<#else>
  <div id="message" class="INFO" style="display:none">
    <!-- keep here for client-side messages -->
  </div>
</#if>

    <h1>${title}</h1>

<form id="signin" action="/signin" method="POST">
  <label for="PlayerName">Input Name:</label><br>
  <input type="text" id="PlayerName" name="PlayerName"><br>
  <button type="submit">Submit</button>
</form> 

</body>

</html>
