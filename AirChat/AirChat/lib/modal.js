//----------------
// モーダルダイアログの状態を変更する
//----------------
function changeModalbox(value){
	document.getElementById('overlay').style.display = value;
	document.getElementById('modalbox').style.display = value;
}

//----------------
// モーダルダイアログを表示する
//----------------
function dispModalbox() {
	changeModalbox('block');
}
//----------------
// モーダルダイアログを閉じる
//----------------
function closeModalbox() {
	changeModalbox('none');
}

//----------------
// モーダルダイアログを閉じる
//----------------
function insertModalBody(body) {
	var modal = document.getElementById('modalbox');
	modal.innerHTML = body;
}