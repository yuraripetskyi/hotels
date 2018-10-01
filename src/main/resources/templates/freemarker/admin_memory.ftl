<#import "../parts/common.ftl" as c>

<@c.page>
<h1>Memory Admin</h1>

<form action="/findUsers" method="get">
    <input type="text" placeholder="Find user" name="user">
    <input type="submit" value="Submit!">
</form>
<div>
<#if users??>
<#list users as user>
    <div>
        <span>${user.username} ${user.name}</span>
        <span></span>
        <#if user.role == user_role>
            <span>Role: user</span>
        </#if>
        <#if user.role == role_hoteladmin>
            <span>Role: hotel admin</span>

        </#if>
        <#if user.role == admin_role>
            <span>Role: admin</span>
        </#if>
        <#if user.isEnabled()>
            <form action="/block/${user.username}" method="get">
                <button type="submit" value="Block"
            </form>
        </#if>
        <#if !user.isEnable()>
            <form action="/unblock/${user.username}" method="get">
                <button type="submit" value="unBlock"
            </form>
        </#if>

    </div>
</#list>
</#if>
</div>
<#--<ul each="user : ${users}">-->
    <#--<li>-->
        <#--<a href="'/guest/'+${user.username}" text="'Name - ' + ${user.name} + '; Surname - ' + ${user.surname} + '; Age - ' + ${user.age}"></a>-->
        <#--<h2 text="'Current role - ' + ${user.role}" > </h2>-->
        <#--<div if="${user.isEnabled()}">-->
            <#--<form action="@{/block/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Block User">-->
            <#--</form>-->

        <#--</div>-->

        <#--<div unless="${user.isEnabled()}">-->
            <#--<form action="@{/unblock/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Unblock User">-->
            <#--</form>-->
        <#--</div>-->

        <#--<div if="${user.getRole()==user_role}">-->
            <#--<form action="@{/makeHotelAdmin/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make hotel admin">-->
            <#--</form>-->
            <#--<form action="@{/makeAdmin/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make admin">-->
            <#--</form>-->
        <#--</div>-->

        <#--<div if="${user.getRole()==role_hoteladmin}">-->
            <#--<form action="@{/makeUser/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make user">-->
            <#--</form>-->
            <#--<form action="@{/makeAdmin/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make  admin">-->
            <#--</form>-->
        <#--</div>-->

        <#--<div if="${user.getRole()==admin_role}">-->
            <#--<form action="@{/makeUser/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make user">-->
            <#--</form>-->
            <#--<form action="@{/makeHotelAdmin/}+${user.username}" method="get">-->
                <#--<input type="submit" value="Make hotel admin">-->
            <#--</form>-->
        <#--</div>-->



    <#--</li>-->
<#--</ul>-->

</@c.page>