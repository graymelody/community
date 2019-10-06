function submitComment() {
    var id = $("input[type='hidden']").val();
    var content = $("#commentContent").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify({
            "parentId":id,
            "type":1,
            "content":content
        }),
        success:function (result) {
            if (result.code === 200) {
                $(".comment").hide();
            } else if (result.code === 2003) {
                var dowhat = confirm(result.message);
                if (dowhat) {
                    window.open("https://github.com/login/oauth/authorize?client_id=9aeb86d8b6e63deff761&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.localStorage.setItem("closable","true");
                }
            } else {
                alert(result.message)
            }
        },
        dataType:"json"}
    );
}