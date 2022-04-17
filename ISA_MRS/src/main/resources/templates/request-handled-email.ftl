<html>
    <head>
        <title>Request Responded</title>
    </head>
    <body style="color: #f7f7f2 <#if response?contains("rejected")> background-color: red<#else> green</#if>">
        <h1 style="text-transform: capitalize">${status}</h1>
        <p>On ${date} at ${time} You have submitted a ${requestType} request.<br/>
            Our administrator has seen your request and ${response} it<#if response?contains("rejected")>
            with the explanation: <br/>
            <p>
            ${explanation}
            </p>
            <#else>
                . You may join our community. Enjoy!
            </#if>
        <p>Responder: ${adminMail}</p>
        </p>
    </body>
</html>