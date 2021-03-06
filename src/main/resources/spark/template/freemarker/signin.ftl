<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | SignIn</title>
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

    <#if failUserNameMessage??>
               <br>
               <br>Error Message: ${failUserNameMessage}
    </#if>


   <div class="Form">
           <form action="./" method="POST">
             Username:
             <input name = "username" />
             <button type = "submit">Enter</button>
           </form>
   </div>

  </div>

</div>
</body>

</html>
