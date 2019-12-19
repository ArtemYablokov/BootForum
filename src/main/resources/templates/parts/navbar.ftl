<#include "security.ftl">
<#import "login.ftl" as l>


<nav class="navbar navbar-expand-lg navbar-light bg-light"> <#-- LG - размер экрана -->
    <a class="navbar-brand" href="/">Forum</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span> <#-- кнопка меню, появляется на экранах меньше чем LG-->
    </button>

    <#--
    collapse - схлопываемое
    -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users">User List</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3">${name}</div> <#-- переменная из "security.ftl -->
        <@l.logout />

    </div>
</nav>