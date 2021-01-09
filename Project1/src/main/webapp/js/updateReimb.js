console.log("In update reimb");

window.onload = function() {

    document.getElementById("reimbursementUpdateSubmit").addEventListener("click", async function(){
 
         const reimbId = document.getElementById("reimbId");
         const updateType = document.querySelector('input[name="type"]:checked');
    
    
            let requested_info = await fetch('http://localhost:9001/Project1/ajax/fmresolvereimb', 
               {
                   method : "post",
                   headers : { "Content-type": "application/x-www-form-urlencoded" },
                   body : `${reimbId.name}=${reimbId.value}&${updateType.name}=${updateType.value}`
               } )

           alert("Successfully updated reimbursement!");
    
    });
 
 }