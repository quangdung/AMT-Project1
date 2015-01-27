var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');
var sleep = require('sleep');


// Number of observations to be sent = NB_SENSOR * NB_OBSERVATION
var NB_SENSOR = 4; 
var NB_OBSERVATION = 10;

// Range of observations' value
var MAX_OBSREVATION_VALUE = 100;
var MIN_OBSERVATION_VALUE = 0;

// Range of observations' day
var START_DATE = new Date(2015, 1, 10);
var AFTER_END_DATE = new Date(2015, 1, 20);

// Error tolerated due to decimal value
var ERROR_TOLERATED = 0.03;

// To send observations after a lap of time
//var TIMER = 10;

// Change the format of day
Date.prototype.formatYYYYMMDD = function(){
    return this.getFullYear() + "-"
    	+ (this.getMonth() <= 9 ? "0" : "")
    	+ (this.getMonth() + 1) + "-"
    	+  this.getDate();
}

// Truncate the number
Number.prototype.toFixedDown = function(digits) {
    var re = new RegExp("(\\d+\\.\\d{" + digits + "})(\\d)");
    var m = this.toString().match(re);

    return m ? parseFloat(m[1]) : this.valueOf();
};

// Compare two numbers with an error tolerate
Number.prototype.isDifferent = function(number, tolerate) {
	return Math.abs(this - number) > tolerate ? true : false;
}

/*
	These maps keep track of the observations posted by the client, even if 
	they result in an error (if two parallel requests try to create a new fact).
	In this case, the client is informed that the observation has failed and 
	it would be his responsibility to retry.

 */
var submittedCounter = {}
var submittedDaily = {}


/*
	These maps keep track of the observations posted by the client, but only 
	if the server has confirmed their processing with a successful status code. 
	In this case, the client can assume that the observations has been successfully 
	processed.	
 */
var processedCounter = {};
var processedDaily = {};


function logCounter(stats, observation) {
	
	var factStats = stats[observation.sensorId] || {
		sensorId: observation.sensorId,
		totNbObs: 0,
	};

	factStats.totNbObs += 1;
	stats[observation.sensorId] = factStats;
}

function logDaily(stats, observation) {

	// Apply the format to calculate the facts locally
	var index = observation.sensorId + "." 
		+ observation.creationDate.formatYYYYMMDD();

	console.log("logDaily: " + index);

	var factStats = stats[index] || {
		sensorId: observation.sensorId,
		nbVal: 0,
		sumVal: 0,
		minVal: MAX_OBSREVATION_VALUE,
		maxVal: MIN_OBSERVATION_VALUE,
		avVal: 0
	};

	factStats.nbVal += 1;

	factStats.sumVal = (factStats.sumVal + observation.obsValue);

	factStats.avVal = factStats.sumVal / factStats.nbVal;

	if (factStats.minVal > observation.obsValue) {
		factStats.minVal = observation.obsValue;
	}

	if (factStats.maxVal < observation.obsValue) {
		factStats.maxVal = observation.obsValue;
	}

	stats[index] = factStats;
}


/*
 * This function returns a function that we can use with the async.js library. 
 */ 
function getObservationPOSTRequestFunction(sensorId) {
		
	return function(callback) {
		var requestData = {
			headers:{
				"Content-Type": "application/json"
			},
			data: {
				'creationDate' : new Date(), // we will generate a random date below
				'name' : 'obs for sensor' + sensorId,			
				'obsValue': 0, // we will generate a random value below
				'sensorId': sensorId
			}
		};
		
		requestData.data.creationDate = new Date(START_DATE.getTime() 
			+ Math.random() * (AFTER_END_DATE.getTime() - START_DATE.getTime()));
		
		console.log("\nrandom creationDate: " + requestData.data.creationDate);

		requestData.data.obsValue = (MIN_OBSERVATION_VALUE 
			+ Math.random() * (MAX_OBSREVATION_VALUE - MIN_OBSERVATION_VALUE)).toFixedDown(2);

		logCounter(submittedCounter, requestData.data);
		
		logDaily(submittedDaily, requestData.data);
		
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

for (var sensor = 1; sensor <= NB_SENSOR; sensor++) {
	for (var observation = 0; observation < NB_OBSERVATION; observation++) {
		requests.push(getObservationPOSTRequestFunction(sensor));		
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
	Call the InitData servlet to post and create some organizations, users and sensors
 */
function initData(callback) {
	console.log("\n\n==========================================");
	console.log("GETing INIT_DATA command.");
	console.log("------------------------------------------");
	client.post("http://localhost:8080/AMT_Project/InitData", function(data, response) {
		console.log("INIT response status code: " + response.statusCode);

		callback(null, "The INIT operation has been processed (status code: " + response.statusCode + ")");
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

		console.log("\nprocessedDaily");
		console.log("sensorId.creationDate")

		for (var i=0; i < results.length; i++) {

			// To send after a lap of time
			//sleep.sleep(timer);
			
			if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
				console.log("Result " + i + ": " + results[i].response.statusCode);
				numberOfUnsuccessfulResponses++;
			} 
			else {
				logCounter(processedCounter, results[i].requestData.data);
				logDaily(processedDaily, results[i].requestData.data);
			}
		}

		callback(null, results.length + " observations POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
	});
} 


/*
	Fetch server-side state (list of facts). The list of facts is passed to 
	the callback function.
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

	/*
		"counter" facts
	 */
	client.get("http://localhost:8080/AMT_Project/api/facts/counter", requestData, function(data, response) {
		var nbErrorsCounter = 0;

		var clientSideFacts = Object.keys(submittedCounter).length;
		
		var serverSideFacts = data.length;

		console.log("\n\n------------------------------------------");
		console.log("Number of counter facts on the client side: " + clientSideFacts);
		console.log("Number of counter facts on the server side: " + serverSideFacts);
		console.log("\n");

		if (clientSideFacts !== serverSideFacts) {
			nbErrorsCounter++;
		}
		
		
		for (var i=0; i < data.length; i++) {

			var sensorIdTemps = data[i].sensorId;

			var serverSideCounterTotNbObs = data[i].totNbObs;
			var clientSideCounterTotNbObs = processedCounter[sensorIdTemps].totNbObs;

			if (serverSideCounterTotNbObs !== clientSideCounterTotNbObs) {
				nbErrorsCounter++;

				console.log("counter fact of sensor " + sensorIdTemps 
					+ " --> Server/Client number of observations: " 
					+ serverSideCounterTotNbObs + "/" + clientSideCounterTotNbObs + "  X");
			} 
			else {
				/* To show which fact is OK
				console.log("counter fact of sensor " + sensorIdTemps 
					+ " : OK, " + serverSideCounterTotNbObs + " observations");				
				*/
			}
		}

		console.log("\nNumber of corrupted counter facts: " + nbErrorsCounter + "\n");		
	});


	/*
		"dailyStats facts"
	 */
	client.get("http://localhost:8080/AMT_Project/api/facts/dailyStats", requestData, function(data, response) {
		var nbErrorsDaily = 0;

		var clientSideFacts = Object.keys(submittedDaily).length;
		
		var serverSideFacts = data.length;

		console.log("\n\n------------------------------------------");
		console.log("Number of dailyStats facts on the client side: " + clientSideFacts);
		console.log("Number of dailyStats facts on the server side: " + serverSideFacts);
		console.log("Error tolerated : " + ERROR_TOLERATED)
		console.log("\n");

		if (clientSideFacts !== serverSideFacts) {
			nbErrorsDaily++;
		}
		
		
		for (var i=0; i < data.length; i++) {

			var sensorIdTemps = data[i].sensorId;

			// Extract the format "YYYY-MM-DD"
			var factDate = data[i].date.substring(0, 10);

			var index = sensorIdTemps + "." + factDate;

			var serverSideNbVal = data[i].nbVal.toFixedDown(2);
			var serverSideSumVal = data[i].sumVal.toFixedDown(2);
			var serverSideMinVal = data[i].minVal.toFixedDown(2);
			var serverSideMaxVal = data[i].maxVal.toFixedDown(2);
			var serverSideAvVal = data[i].avVal.toFixedDown(2);

			//console.log("processedDaily: " + processedDaily[index]);

			var clientSideNbVal = processedDaily[index].nbVal.toFixedDown(2);
			var clientSideSumVal = processedDaily[index].sumVal.toFixedDown(2);
			var clientSideMinVal = processedDaily[index].minVal.toFixedDown(2);
			var clientSideMaxVal = processedDaily[index].maxVal.toFixedDown(2);
			var clientSideAvVal = processedDaily[index].avVal.toFixedDown(2);

			if (serverSideNbVal.isDifferent(clientSideNbVal, 0)
				|| serverSideSumVal.isDifferent(clientSideSumVal, ERROR_TOLERATED)
				|| serverSideMinVal.isDifferent(clientSideMinVal, ERROR_TOLERATED)
				|| serverSideMaxVal.isDifferent(clientSideMaxVal, ERROR_TOLERATED)
				|| serverSideAvVal.isDifferent(clientSideAvVal, ERROR_TOLERATED)) {

				nbErrorsDaily++;
				console.log("dailyStast fact of sensor " + sensorIdTemps 
					+ " at " + factDate + ": X\n"
					+ "--> Server/Client number of observations: " 
					+ serverSideNbVal + " / " + clientSideNbVal 
					+ (serverSideNbVal.isDifferent(clientSideNbVal, 0) ? " X" : "") + " \n"
					+ "--> Server/Client total of values: "
					+ serverSideSumVal + " / " + clientSideSumVal
					+ (serverSideSumVal.isDifferent(clientSideSumVal, ERROR_TOLERATED) ? " X" : "") + " \n"
					+ "--> Server/Client min value: "
					+ serverSideMinVal + " / " + clientSideMinVal
					+ (serverSideMinVal.isDifferent(clientSideMinVal, ERROR_TOLERATED) ? " X" : "") + " \n"
					+ "--> Server/Client max value: "
					+ serverSideMaxVal + " / " + clientSideMaxVal
					+ (serverSideMaxVal.isDifferent(clientSideMaxVal, ERROR_TOLERATED) ? " X" : "") + " \n"
					+ "--> Server/Client average value: "
					+ serverSideAvVal + " / " + clientSideAvVal
					+ (serverSideAvVal.isDifferent(clientSideAvVal, ERROR_TOLERATED) ? " X" : "") + " \n");
			}
			else {

				/* To show which fact is OK
				console.log("dailyStast fact of sensor " + sensorIdTemps 
					+ " at " + factDate + ": OK\n");
				*/
			}
		}

		console.log("\nNumber of corrupted dailyStats facts: " + nbErrorsDaily + "\n");
	});
	
	callback(null, "The client side and server side values have been compared.");
}


/*

 */	
async.series([	
	resetServerState,	
	//initData,
	postObservationRequestsInParallel,	
	checkValues	
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	console.log(err);
	console.log(results);
}); 

