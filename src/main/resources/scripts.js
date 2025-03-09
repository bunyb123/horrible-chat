console.log("working");

var box = document.getElementsByClassName("username-box")[0];

box.addEventListener("submit", changePage);

function changePage() {
  event.preventDefault();
  console.log("function entered");
    if (event.keyCode == 13) {
      console.log("event working");
      window.location.replace("./chat.html");
  }
}





















































