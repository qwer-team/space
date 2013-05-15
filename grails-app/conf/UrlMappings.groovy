class UrlMappings {

	static mappings = {
            
                "/segment/reset/$number?"{
                    controller = "segment"
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
