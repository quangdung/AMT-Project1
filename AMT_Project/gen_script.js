/*global require */

// inspiration : http://rapiddg.com/blog/calling-rest-api-nodejs-script

var querystring = require('querystring');
var http = require('http');

var host = 'localhost';
var port = '8080';
var username = '';
var password = '';
var apiKey = '';
var sessionId = null;
var deckId = '';

function performRequest(endpoint, method, data, success) {
    "use strict";
    var dataString = JSON.stringify(data);
    var headers = {};
    
    headers = {
        'Content-Type': 'application/json'
    };
    
    var options = {
        host: host,
        port: port,
        path: endpoint,
        method: method,
        headers: headers
    };
    
    var req = http.request(options, function(res) {
        res.setEncoding('utf-8');

        var responseString = '';
        
        res.on('data', function(data) {
            responseString += data;
        });

        res.on('end', function() {
//            console.log(responseString);
//            var responseObject = JSON.parse(responseString);
//            success(responseObject);
            success(responseString);
        });
    });
    
    req.write(dataString);
    req.end();
}

function sendSinglePost(orgId) {
    
    performRequest('/AMT_Project/api/sensors', 'POST', 
                {
                    description: "descriptionTest",
                    name: "nameTest",
                    organizationId: orgId,
                    publicSensor: true,
                    type: "typeTest"
                },
                 function(data)
                {                    
                    if (data) {
                        sensorId = data;
                        console.log('Inserted with id : ', sensorId);
                    }
                    else {
                        console.log("No data to log");
                        process.exit(-1);
                    }
                });
}

function sendMultiplePost(number) {
    
	var i = 0;

	for (i = 0; i < number; i++) { 
		sendSinglePost(i);
	}
}

function sendSinglePut(sensorId, orgId) {
    
    performRequest('/AMT_Project/api/sensors/' + sensorId, 'PUT', 
                {
                    organizationId: orgId,
                },
                 function(data)
                {
                    if (data) {
                        sensorId = data.id;
                        console.log('Modified sensor id : ', sensorId);
                    }
                    else {
                        console.log("No data to log");
                        process.exit(-1);
                    }
                });
}

function sendMultiplePut(number, idFirst) {
    
	var i = 0;

	for (i = 0; i < number; i++) { 
		sendSinglePut(idFirst + i, 8888);
	}
}

function sendSingleDelete(sensorId) {
    
    performRequest('/AMT_Project/api/sensors/' + sensorId, 'DELETE', '',
                 function(data)
                {
                    console.log("Deleting in progress");
//                    if (data) {
//                        sensorId = data.id;
//                        console.log('Deleted sensor id : ', sensorId);
//                    }
//                    else {
//                        console.log("No data to log");
//                        process.exit(-1);
//                    }
                });
}

function sendMultipleDelete(number, idFirst) {
    
	var i = 0;

	for (i = 0; i < number; i++) { 
		sendSingleDelete(idFirst + i);
	}
}

//sendMultiplePost(2000);
//sendMultiplePut(200, 50);
sendMultipleDelete(2000, 4000);