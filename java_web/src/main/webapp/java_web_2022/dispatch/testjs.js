function deleteFruit(fid){
    if(confirm("确定删除fid="+fid+"的水果？")){
        window.location.href = "fruit.do?action=delete&fid="+fid;
    }
}
function loadPage(page){
    window.location.href = "fruit.do?action=index&page="+page;
}
