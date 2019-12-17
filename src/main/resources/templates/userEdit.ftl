<#import "parts/common.ftl" as c>

<@c.page>

<form action="/users", method="post">

    <#--
    отображаем ИМЯ пользователя
    так же есть возможность имя изменить
    -->
    <input type="text", value="${user.username}", name="userName">

    <#-- отображаем роли пользователя-->
    <#list roles as role> <#--  передавались атрибутом - МАССИВ Здесь перечисляются все роли !!!-->
        <div>
            <label><input
                        type="checkbox"
                        name="${role}" <#--  само имя роли  -->
                        ${user.roles
                        ?seq_contains(role) <#-- если у пользователя есть такая роль -->
                        ?string("checked","")}> <#--тогда помеченно - иначе ничего -->
                ${role} <#--  само имя роли  -->
            </label>
            <#--проверяем у последовательности ролей ЮЗЕРА - есть ли у него эта роль - преобразовываем в строку (чекед или ничего ???)-->
        </div>

    </#list>

    <input type="hidden", value="${user.id}", name="userId">
    <input type="hidden", value="${_csrf.token}", name="_csrf">
    <button type="submit">Save UserChanges</button>



</form>

</@c.page>