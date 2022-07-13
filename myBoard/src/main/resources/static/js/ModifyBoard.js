
function Load()
{
     var boardNo =  getNo();

     $.ajax({
            url : "/Board/Info",
            data:{ "boardNo" :  boardNo},
            method : "GET",
            success :
            function (info)
            {
                if(info.flag == "false") location.href = "/Board/Main";

                $("#title").val(info.title);
                $(".ql-editor").html(info.content);

            },
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}

function Update()
{
    var boardNo =  getNo();
    var title = $("#title").val();
    var content = $(".ql-editor").html();

    $.ajax({
           url : "/Board/Update",
           data:{ "boardNo" :  boardNo, "title" : title, "content" : content},
           method : "GET",
           success :
           function (message)
           {
                if(message == "Success") location.href = "/Board/" + boardNo;
                else alert(message);
           },
           error :
           function(e)
           {
               alert(e.responseText);
           }
    });


}


function getNo()
{
    return  document.location.href.split("/")[4]; // 번호를 따로 따서 컨트롤러에 전달
}