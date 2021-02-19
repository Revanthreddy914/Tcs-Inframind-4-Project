

function retriveData() {
    
            var db=firebase.firestore();
            var val=document.getElementById("f1").value;
            const list_div=document.querySelector("#list_div");

            db.collection(val).get().then((querySnapshot) => {
                querySnapshot.forEach((doc) => {
                    list_div.innerHTML+="<div class='list-item'><h3>"+doc.data().glucose+"<h3></div>"
                    list_div.innerHTML+="<div class='list-item1'><h3>"+doc.data().bp+"<h3></div>"
                    
                    

                
                });
            });
}
