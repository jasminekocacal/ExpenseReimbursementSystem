console.log("in finance manager js");

window.onload = function() {
	
	getAllPending();
	
}


let myUserInformation ={};

function getAllPending() {
	fetch(
			'http://localhost:9001/Project1/ajax/fmpending')
			.then(function(responseObject) {
				const convertedResponse = responseObject.json();
				return convertedResponse;
			}).then(function(daSecondResponse) {
				console.log(daSecondResponse);
				myUserInformation=daSecondResponse;
				ourDOMManipulation(daSecondResponse);
			})

}

function ourDOMManipulation(ourJSON) {

	for (let i = 0; i < ourJSON.length; i++) {

        let submitDate = new Date(ourJSON[i].dateSubmitted);
        let approvedDate = new Date(ourJSON[i].dateResolved);

		let newTR = document.createElement("tr");
		let newTH = document.createElement("th");
		
		let newTD1 = document.createElement("td");
		let newTD2 = document.createElement("td");
        let newTD3 = document.createElement("td");
        let newTD4 = document.createElement("td");
        let newTD5 = document.createElement("td");
        let newTD6 = document.createElement("td");
        let newTD7 = document.createElement("td");
		
		// population creations
		newTH.setAttribute("scope", "row")
        let myText1 = document.createTextNode(ourJSON[i].reimbId);
        let myText2 = document.createTextNode(ourJSON[i].employeeId);
        let myText3 = document.createTextNode(ourJSON[i].employeeFirstName + " " + ourJSON[i].employeeLastName);
		let myText4 = document.createTextNode(ourJSON[i].amount);
		let myText5 = document.createTextNode(ourJSON[i].description);
        let myText6 = document.createTextNode(ourJSON[i].type);
        let myText7 = document.createTextNode(ourJSON[i].status);
        let myText8 = document.createTextNode(submitDate.toLocaleDateString());
        

		// newDiv.appendChild(newDiv);
		
		
		///all appendings
		newTH.appendChild(myText1);
		newTD1.appendChild(myText2);
		newTD2.appendChild(myText3);
        newTD3.appendChild(myText4);
        newTD4.appendChild(myText5);
        newTD5.appendChild(myText6);
        newTD6.appendChild(myText7);
        newTD7.appendChild(myText8);
		
		newTR.appendChild(newTH);
		newTR.appendChild(newTD1);
		newTR.appendChild(newTD2);
        newTR.appendChild(newTD3);
        newTR.appendChild(newTD4);
        newTR.appendChild(newTD5);
        newTR.appendChild(newTD6);
        newTR.appendChild(newTD7);
		let newSelectionTwo = document.querySelector("#pendingTableBody");
		newSelectionTwo.appendChild(newTR);

	}
}

