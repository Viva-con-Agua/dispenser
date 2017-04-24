# dispenser
Implements a node inside a Content-Delivery-Network (CDN) for the new Pool² infrastructure. It focuses on CSS (respectively LESS code) and a dynamic frame for other microservices UI.


INSTALL
=======

Dispenser can be build via sbt-buildtool.



TEMPLATE
========

Dispenser can render HTML-Files with custom Context. For this you can simply send an Json in Format:
```json
{
	metaData: {
		microServiceName: "",
		template: "",
		searchEngineKeywords: "",
		language: "",
		organization: ""
	},
	templateData: {
		title: "",
		body: ""
	}
}
```


to Dispenser @ URL/getTemplate.  


ChangeLog
=========

## Version 0.1.0 (2017-04-24)

*	[[F] #3 - handle HTML Template](https://github.com/Viva-con-Agua/dispenser/issues/3)
