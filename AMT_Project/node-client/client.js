var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');

var nbSensor = 3;
var nbObservation = 10;

/*
	This map keeps track of the observations posted by the client, even if they result in an error (in which case ?)

 */


// This map keeps track of the transactions posted by the client, even if they result in an error (for instance if two parallel requests try to create a new account). In this case, the client is informed that the transaction has failed and it would be his responsibility to retry.
var submittedStats = {}


/*
	This map keeps track of the observations posted by the client, but only if the server has confirmed their processing with a successful status code. In this case, the client can assume that the observations has been successfully processed.	
 */

// This map keeps track of the transactions posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the transaction has been successfully processed.
var processedStats = {};

/*

 */
function logObservation(stats, observation) {
	
	var factStats = stats[observation.sensorId] || {
		sensorId: observation.sensorId,
		totNbObs: 0,
	};
	factStats.totNbObs += 1;
	stats[observation.sensorId] = factStats;
}


/*
 * This function returns a function that we can use with the async.js library. 
 */ 


/*

 */
function getObservationPOSTRequestFunction(sensorId) {
		
	return function(callback) {
		var requestData = {
			headers:{
				"Content-Type": "application/json"
			},
			data: {
				'creationDate' : new Date(2015, 1, 11),	
				'name' : 'obs for sensor' + sensorId,			
				'obsValue': 0, // we will generate a random value below
				'sensorId': sensorId
			}
		};
		
		requestData.data.obsValue = Math.floor((Math.random() * 100) - 0);

		logObservation(submittedStats, requestData.data);
		
		client.post("http://localhost:8080/AMT_Project/api/observations", requestData, function(data, response) {
			var error = null;
			var result = {
				requestData: requestData,
				data: data, 
				response: response
			};
			callback(error, result);
		});
	}
}


/*
 * Prepare an array of HTTP request functions. We will pass these to async.parallel
 */
var requests = [];

/*
	
 */
for (var sensor = 1; sensor <= nbSensor; sensor++) {
	for (var observation = 0; observation < nbObservation; observation++) {
		requests.push(
			getObservationPOSTRequestFunction(sensor)
		);
	}
}; 


/*
	Reset server side - this will delete all facts 	
 */
 
function resetServerState(callback) {
	console.log("\n\n==========================================");
	console.log("POSTing RESET command.");
	console.log("------------------------------------------");
	client.post("http://localhost:8080/AMT_Project/api/operations/reset", function(data, response) {
		console.log("RESET response status code: " + response.statusCode);

		callback(null, "The RESET operation has been processed (status code: " + response.statusCode + ")");
	});
};


/*
	POST observation requests in parallel
 */
function postObservationRequestsInParallel(callback) {
	console.log("\n\n==========================================");
	console.log("POSTing observation requests in parallel");
	console.log("------------------------------------------");

	var numberOfUnsuccessfulResponses = 0;

	async.parallel(requests, function(err, results) {
		for (var i=0; i < results.length; i++) {
			if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
				console.log("Result " + i + ": " + results[i].response.statusCode);
				numberOfUnsuccessfulResponses++;
			} else {
				logObservation(processedStats, results[i].requestData.data);
			}
		}

		callback(null, results.length + " observation POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
	});
} 


/*
	Fetch server-side state (list of facts). The list of facts is passed to the callback function.
 */
function checkValues(callback) {
	console.log("\n\n==========================================");
	console.log("Comparing client-side and server-side stats");
	console.log("------------------------------------------");

	var requestData = {
		headers:{
			"Accept": "application/json"
		}
	};

	client.get("http://localhost:8080/AMT_Project/api/facts", requestData, function(data, response) {
		var numberOfErrors = 0;

		var clientSideFacts = Object.keys(submittedStats).length;
		var serverSideFacts = data.length;

		console.log("Number of facts on the client side: " + clientSideFacts);
		console.log("Number of facts on the server side: " + serverSideFacts);

		if (clientSideFacts !== serverSideFacts) {
			numberOfErrors++;
		}
		
		for (var i=0; i < data.length; i++) {
			var factId = data[i].id;
			var serverSideValue = data[i].totNbObs;
			var clientSideValue = processedStats[factId].totNbObs;

			if (clientSideValue !== serverSideValue) {
				numberOfErrors++;
				console.log("Fact " + factId + " --> Server/Client number of observations: " + serverSideValue + "/" + clientSideValue + "  X");
			} else {
				console.log("Fact " + factId + " --> Server/Client balance: " + serverSideValue + "/" + clientSideValue);				
			}
			
		}
		
		callback(null, "The client side and server side values have been compared. Number of corrupted facts: " + numberOfErrors);
	});
}


/*

 */	
async.series([
	
	resetServerState,
	
	postObservationRequestsInParallel
	/*,
	checkValues
	*/
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	//console.log(err);
	console.log(results);
}); 

