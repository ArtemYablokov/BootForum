<#import "parts/common.ftl" as c>

<@c.page>
    <div>userList</div>
    <table>
        <thead>
        <th>Name</th>
        <th>Role</th>
        <th></th> <#--колонка для команд-->
        </thead>
        <tbody>

        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td>
                    <#list user.roles as role>
                        ${role}<#sep>, <#--вывод ролей через запятую-->
                    </#list>
                </td>
                <td>
                    <a href="/users/${user.id}">Редактировать юзера </a>
                </td> <#--на отображение ПОЛЬЗОВАТЕЛя со СПИСКом ролей -->

            </tr>
        <#else>
            No message
        </#list>


        </tbody>
    </table>

    <a href="/main">Main page</a>
</@c.page>