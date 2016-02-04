<#-- @ftlroot "" -->
<#import "/spring.ftl" as spring />

<html>
    <head>
        <title>
            Cuba Portal
        </title>
        <link rel="shortcut icon" type="image/x-icon" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="icon" type="image/gif" href="<@spring.url "/resources/favicon.ico"/>">
        <link rel="stylesheet" href="<@spring.url "/resources/css/common.css"/>"/>
    </head>
    <body>
        <h1> Cuba Portal </h1>

        <#if userSession?? && userSession.authenticated>
            User: ${userSession.user.login} 
            <br/>
            <form method="post" action="<@spring.url "/logout"/>" id="logoutForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <a href="#" onclick="document.getElementById('logoutForm').submit();">Logout</a>
            </form>
            
        <#else>
            <a href="login">Login</a>
        </#if>

        <#if userSession?? && userSession.authenticated>
        <h1>Users</h1>
        <ul>
        <#list users as user>
            <li>${user.login}</li>
        </#list>
        </ul>
        </#if>
    </body>
</html>