console.log("In adding reimbursements js");

window.onload = function() {

    document.getElementById("addReimbSubmit").addEventListener("click", async function(){
 
         const reimbAmount = document.getElementById("reimbAmount");
         const reimbDesc = document.getElementById("reimbDesc");
         const reimbType = document.querySelector('input[name="type"]:checked');


	         let requested_info = await fetch('http://localhost:9001/Project1/ajax/empaddreimb', 
	            {
	                method : "post",
	                headers : { "Content-type": "application/x-www-form-urlencoded" },
	                body : `${reimbAmount.name}=${reimbAmount.value}&${reimbDesc.name}=${reimbDesc.value}&${reimbType.name}=${reimbType.value}`
	            } )
	
			alert("Successfully requested a reimbursement!");
 
    });
 
 }


