
var userNo; // 본인 번호

// 글의 상세정보를 받아온다.
function Search(no)
{
     var boardNo =  getNo();
     userNo = no;
     $.ajax({
            url : "/Board/Info",
            data:{ "boardNo" :  boardNo},
            method : "GET",
            success : success,
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}

// 받아오기 성공했을때
function success(info)
{
    // 실패했다면??
    if(info.flag == "false") location.href = "/Board/Main";

    // 제목과 내용을 표시해준다.
    $("#title").html(info.title);
    $("#content").html(info.content);

    // 기타 정보를 표시해준다.
    var etc = "추천 " + info.likes + "  |  " + "조회수 " + info.views + "  |  " + "작성일 " + info.dateTime;
    $("#etc").html(etc);
    $("#likes").html("추천 " + info.likes);

    // 만약, 세션에 담겨있는 유저 번호와 글쓴이 번호가 같은 경우, 수정/삭제 버튼을 활성화 시켜준다.
    if(info.writerNo == userNo)
    {
        $("#UD").show();
        $("#update").attr("href", "/Board/" + getNo() + "/Modify");
        //$("#delete").click(delete);
    }
    else $("#UD").hide();

}

function getNo()
{
    return  document.location.href.split("/")[4]; // 번호를 따로 따서 컨트롤러에 전달
}
     // 글 정보를 받아서
