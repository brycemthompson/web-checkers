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

    <div class="Form">
           <form action="/game" method="POST">
                <#if currentUser??>
                    List of Users:
                    <#list allPlayers as player>
                        <button name = "opponentUsername" type = "submit" value = ${player}>${player}</button>
                    </#list>
                </#if>
           </form>
    </div>

  </div>

</div>
</body>

</html>
