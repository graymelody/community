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
                window.location.reload();
            } else if (result.code === 2003) {
                var dowhat = confirm(result.message);
                if (dowhat) {
                    window.open("https://github.com/login/oauth/authorize?client_id=9aeb86d8b6e63deff761&redirect_uri=http://localhost:8080/callback&scope=user&state=1https://github.com/login/oauth/authorize?client_id=9aeb86d8b6e63deff761&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.localStorage.setItem("closable","true");
                }
            } else {
                alert(result.message)
            }
        },
        dataType:"json"}
    );
}

function collapseComments(e) {
    var id = $(e).data("id");
    var $comments = $('#comment-' + id);
    var collapse = $(e).data("collapse");
    if (collapse) {
        $comments.removeClass("in");
        $(e).removeClass("active");
        $(e).removeData("collapse");
    } else {
        $comments.addClass("in");
        $(e).addClass("active");
        $.data(e,"collapse",true);
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) === -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}