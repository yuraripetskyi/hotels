<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Username :  </label>
            <div class="col-sm-4">
                <input class="form-control" type="text" name="username" placeholder="Username"/></div>
        </div>
        <div class="form-group row"><label class="col-sm-2 col-form-label"> Password:  </label>
            <div class="col-sm-4">
                <input class="form-control" type="password" name="password" placeholder="password"/></div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if isRegisterForm>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Your Name :  </label>
            <div class="col-sm-4">
    <input class="form-control" type="" name="name" placeholder="Name" required oninvalid="setCustomValidity('Введите свое Имя')"
           oninput="setCustomValidity('')">
        </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Your Surame :  </label>
            <div class="col-sm-4">
                <input class="form-control" type="" name="surname" placeholder="Surname" required oninvalid="setCustomValidity('Ведите свою фамилию')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Your Email :  </label>
            <div class="col-sm-4">
                <input class="form-control" type="email" name="email" placeholder="Email" required oninvalid="setCustomValidity('Введите свою почту')"
                       oninput="setCustomValidity('')">
            </div>
        </div>

        </#if>
        <button class="btn btn-primary" type="submit" >Sign in</button><br>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>

    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>

