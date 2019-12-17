<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <@l.logout />
        <span><a href="/users">ссылка на юзеров</a> </span>
    </div>
    <div>
        <form method="post" enctype="multipart/form-data"> <#--тип энкрипшн-->
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг">
            <input type="file" name="file"> <#--поле для загрузки файлов-->
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!}"> <#-- если есть фильтр то отображается -->
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <#--отображение картинки :-->

            <div>
                <#if message.fileName??> <#-- ?? - приведение к булевому типу-->
                    ЗДЕСЬ ФОТО
                    <img src="/img/${message.fileName}"> <#--дирректория хранения-->
                    <#--что такое  IMG ??? - этот УРЛ отображается для картинки-->
                </#if>

            </div>
        </div>
    <#else>
        No message
    </#list>
</@c.page>
