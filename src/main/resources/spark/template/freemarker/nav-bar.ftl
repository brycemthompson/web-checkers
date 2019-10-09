 <div class="navigation">
  <#if currentUser??>
    <#if users??>
        <#list users as user>${user}<#sep>, </#list>
    </#if>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
