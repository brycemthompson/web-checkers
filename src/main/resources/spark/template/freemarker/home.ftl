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


    <#if amountOfPlayersPlaying??>
        currently playing: ${amountOfPlayersPlaying}
    </#if>

    <#if playerIsSignedIn??>
        List of Users:
        <#list list as children>
            <a href="/game"</a> ${children}
        </#list>
    </#if>

    <#if failUserNameMessage??>
        <br>
        <br>Error Message: ${failUserNameMessage}
    </#if>

  </div>

</div>
</body>

</html>
