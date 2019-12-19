<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1">Add new user</div> <#-- Margin bottom = 1 -->
    ${message!}
    <#--
    добавляем восклицательный знак - вывод будет только когда юзер существует
    или
    ?ifExists
    -->
    <@l.login "/registration" true /> <#-- передается булевый параметр что это форма регистрации -->
</@c.page>
