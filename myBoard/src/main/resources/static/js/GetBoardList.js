
var mode = ""; // 추천 수 (0/10)
var key = ""; // 어떤 주제로 검색?
var keyword = ""; // 어떤 문자열로 검색?
var page = 0; // 현재 몇 페이지?

// 키워드로 검색.
function Search()
{
     $.ajax({
            url : "/Board/GetBoardList",
            data:{"mode" : this.mode, "key" :  this.key  , "keyword" : this.keyword , "page" :  this.page },
            method : "GET",
            success : success,
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}


// 모드를 바꾼다. 모드를 바꿀때는 모든 키/키워드/페이지가 초기화 된다.
function modeChange(pMode)
{
    mode = pMode;
    page = 0;
    key = "title";
    keyword = "";

    Search();

}

function keyChange()
{
    page = 0;
    key = $("#key").val();
    keyword = $("#keyword").val();

    Search();

}

function pageChange(pPage)
{
    page = pPage;

    Search();
}


function success(boardlist)
{
                 let html = '<thead class="table-dark">' +
                                    '<th width="10%">no</th>' +
                                    '<th width="30%">제목</th>' +
                                    '<th width="20%">작성자</th>' +
                                    '<th width="20%">게시일</th>' +
                                    '<th width="10%">조회수</th>' +
                                    '<th width="10%">추천수</th>' +
                             '</thead>';

                if( boardlist.data.length == 0 ){ // 검색 결과가 존재하지 않으면
                          html +=
                                '<tr>'+
                                        '<td colspan="5">No result</td> '+
                                 '</tr>';
                }
                else{
                        for( let i = 0 ; i<boardlist.data.length ; i++ ){
                            html +=
                                    '<tr style="cursor:pointer;" onclick="goto(' + boardlist.data[i].boardNo + ')">'+
                                            '<td>'+boardlist.data[i].boardNo+'</td> ' +
                                            '<td>';
                                            if(boardlist.data[i].likes >= 10) html+='★ ';
                            html +=
                                            boardlist.data[i].title+'</td> '+
                                            '<td>'+boardlist.data[i].writer+'</td>'+
                                            '<td>'+boardlist.data[i].dateTime+'</td>'+
                                            '<td>'+boardlist.data[i].views+'</td>'+
                                            '<td>'+boardlist.data[i].likes+'</td>'+
                                     '</tr>';
                        }
                 }
//////////////////////////////////////////////////////////////////////////////////////// 페이징 버튼 생성 코드 ///////////////////////////////////////////////////////////////////////
                 let pagehtml = "";
                 ////////////////////////////////////////////  이전 버튼 ////////////////////////////////////////////////
                 if( page == 0 ){   // 현재 페이지가 첫페이지 이면
                        pagehtml +=
                         '<li class="page-item"> '+
                                     '<button class="page-link" onclick="pageChange('+ (page)  +')"> << </button>'+  // 검색 없음
                          '</li>';
                 }else{  // 현재 페이지가 첫페이지가 아니면
                     pagehtml +=
                        '<li class="page-item"> '+
                                    '<button class="page-link" onclick="pageChange('+ (page-1)  +')"> << </button>'+  // 검색 없음
                         '</li>';
                  }
                 ////////////////////////////////////////////  ////////////////////////////////// ////////////////////////////////////////////////
                ////////////////////////////////////////// 가운데에 들어가는 페이징 버튼수 //////////////////////////////////////////
                 for( let i = boardlist.startbtn ; i<=boardlist.endhtn ; i++ ){
                    pagehtml +=
                          '<li class="page-item"> '+
                            '<button class="page-link" onclick="pageChange('+(i-1)+')"> '+i+' </button>'+  // 검색 없음
                          '</li>';
                 }
                ///////////////////////////////////////// ///////////////////////////////////////  //////////////////////////////////////////
                ////////////////////////////////////////////  다음 버튼 ////////////////////////////////////////////////
                if( page == boardlist.totalpages -1 ){ // 현재 페이지가 마지막 페이지이면
                     pagehtml +=
                            '<li class="page-item"> '+
                                        '<button class="page-link" onclick="pageChange('+ (page)  +')"> >> </button>'+  // 검색 없음
                             '</li>';
                }else{ // 아니면
                     pagehtml +=
                        '<li class="page-item"> '+
                                    '<button class="page-link" onclick="pageChange('+ (page+1)  +')"> >> </button>'+  // 검색 없음
                         '</li>';
                }

                ////////////////////////////////////////////  ////////////// ////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

                $("#BoardTable").html( html ); // 테이블에 html  넣기
                $("#PageBtnBox").html( pagehtml); // 페이징버튼 html 넣기

}

function goto(boardNo)
{

    location.href = "/Board/" + boardNo ;
}


