import $ from "jquery";

var exports = module.exports = {};

exports.getTemplate = function(templateName, templateTitle) { 
				const templateString = '{' +
							'"metaData": {' +
							'"microServiceName": "", "template": "", "searchEngineKeywords": ["test"], "language": ["de"], "organization": "test" },' +
							'"templateData": {' +
							'"title": "", "header": "", "body": "" }' +
							'}';
				//const templateJsonString = JSON.stringify(this.templateString);
				const templateJson = JSON.parse(templateString);
				console.log(this.templateJson);
				templateJson.metaData.template = templateName;
				templateJson.templateData.title = templateTitle;
				const template = "";
				$.ajax({
								type: "POST",
								dataType: "json",
								url: "/dispenser/getTemplate",
								contentType: "application/json",
								data: JSON.stringify(templateJson),
								success: function(data) {
												template: data
								},
								failure: function(errMsg) {
        								alert(errMsg);
    								}
				});
				//$.post("/dispenser/getTemplate", templateJson);	
				//xhttp.open("POST", );
				template;
}
