<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
    <p>Current Players:</p>
     <#if currentUser??>
       <#if !users??>
           <p>No one else is available to play against right now.</p>
       <#else>
        <#list users as user>
          <a href="/startgame?desiredOpponent=${user}">${user}</a>
        </#list>
          </#if>
        <#else>
        <p>${Num}</p>
      </#if>



    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
