<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Add new user
    ${message!} <#-- добавляем восклицательный знак - вывод будет только когда юзер существует -->
    <@l.login "/registration" />
</@c.page>
