 <div class="navigation">
  <#if currentUser??>
    <#if users??>
        <#list users as user><a href="/game">${user}</a><#sep>, </#list>
    </#if>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form>
  <#else>
    <#if amountOfPlayersPlaying??>
        currently playing: ${amountOfPlayersPlaying}
    </#if>
    <a href="/signin">sign in</a>
  </#if>
 </div>
