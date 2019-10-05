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
            console.log(result);
        },
        dataType:"json"}
    );
}