const deleteBtnList = document.querySelectorAll(".delete-btn");

for(let btn of deleteBtnList){
  btn.addEventListener("click", e => {
    if(!confirm("정말 탈퇴 하시겠습니까?")) return;
    const index = e.target.dataset.index;
    location.href = "/deleteMember?index=" + index;
  })
}