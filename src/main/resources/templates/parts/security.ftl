<#--
настройка чтобы не видно было ссылку на всех юзеров
в файде можно определять переменные !!!
-->

<#assign
<#--переменная-->
known = Session.SPRING_SECURITY_CONTEXT??

<#-- SpringSECURITY помещает в контекст ФРИМАРКЕРА объект для обращения с контекстом SpringSECURITY -->
>

<#if known> <#-- если сессия пользователя существует  -->
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>