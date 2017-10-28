import $ from "jquery";

var exports = module.exports = {};

exports.getTemplate = function(templateName, templateTitle) { 
				var templateJson = {
								"metaData": {
												"microServiceName": "",
												"template": "",
												"searchEngineKeywords": ["test"],
												"language": ["de"],
												"organization": "test"
								},
								"templateData": {
												"title": "",
												"header": "",
												"body": ""
								}
				};
				templateJson.metaData.template = templateName;
				templateJson.templateData.title = templateTitle;
				const template = "";
				$.ajax({
								type: "POST",
								dataType: "json",
								url: "http://localhost:4002/getTemplate",
								contentType: "application/json",
								data: JSON.stringify({TemplateJson: templateJson}),
								success: function(data) {
												template: data
								},
								failure: function(errMsg) {
        								alert(errMsg);
    						}
				});
				document.write(template);
}
