# dispenser
Implements a node inside a Content-Delivery-Network (CDN) for the new Pool² infrastructure. It focuses on CSS (respectively LESS code) and a dynamic frame for other microservices UI.


INSTALL
=======

Dispenser can be build via sbt-buildtool.

Provided Widgets
================

dispenser_navigation_widget 

TEMPLATE
========

Dispenser can render HTML-Files with custom Context. For this you can simply send an Json in Format:
```json
{
	metaData: {
		msName: "",
		templateName: "",
		language: Option[""]
	},
	navigationData:{
		navigationName: "",
		active:"",
		user_id: Option UUID
	},
	templateData: {
		title: "",
		content:"",
                head: Option String
	}
}
```


to Dispenser @ http://HOST/dispenser/template/simple

  
TEMPLATE-FILES
==============

The name of the template must be specified in the template variable of the json.

Used variable:

simpleTemplate: header, body:


ChangeLog
=========

## Version 0.3.8 (2018-07-10)
* [[F] #35 - Widget for handling navigation via Javascript](https://github.com/Viva-con-Agua/dispenser/issues/35)
* [[B] #25 - Navigation not Found](https://github.com/Viva-con-Agua/dispenser/issues/25)
* [[F] #31 - Templates: add option for handling javascript](https://github.com/Viva-con-Agua/dispenser/issues/31)
* [[F] #28 - Impressum: template logic for handling imprints](https://github.com/Viva-con-Agua/dispenser/issues/28)
* [[F] #26 - Switch the host](https://github.com/Viva-con-Agua/dispenser/issues/26) 
* [[F] #21 - add init for navigation](https://github.com/Viva-con-Agua/dispenser/issues/23) 
* [[F] #1 -Embeds HTML-Snippets in an HTML-Form](https://github.com/Viva-con-Agua/dispenser/issues/1)
* [[F] #9 -  add mongoDB for navigation](https://github.com/Viva-con-Agua/dispenser/issues/9)
* [[F] #12 - view representation for navigation](https://github.com/Viva-con-Agua/dispenser/issues/12)
* [[F] #3 - handle HTML Template](https://github.com/Viva-con-Agua/dispenser/issues/3)
