<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists} <#-- ?ifExists -->
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
                <#-- ${email!''} если нет email - отображается пустая строчка -->
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"> Edit </button>
    </form>
</@c.page>