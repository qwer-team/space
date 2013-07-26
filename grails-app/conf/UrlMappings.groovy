class UrlMappings {

	static mappings = {
               "/jump/proceed/$x/$y/$z"{
                    controller = "jump"
                    action = "proceed"
               }
               "/type/show/$tag?"{
                    controller = "type"
                    action = "show"
                    constraints{
                        tag(nullable:false)
                    }
                }
                "/getpoint/type/$x/$y/$z"{
                    controller = "pointType"
                    action = "getPointType"
                }
                "/segment/reset/$number?"{
                    controller = "segment"
                    action = "reset"
                    constraints{
                        number(matches:/\d+/)
                    }
                }
                "/prizeSegment/reset/$number?"{
                    controller = "prizeSegment"
                    action = "reset"
                    constraints{
                        number(matches:/\d+/)
                    }
                }
		"/$controller/$action?/$id?"{
			constraints {
				action(notEqual:"reset")
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
