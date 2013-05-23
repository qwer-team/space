class UrlMappings {

	static mappings = {
               "/type/show/$tag?"{
                    controller = "type"
                    action = "show"
                    constraints{
                        tag(nullable:false)
                    }
                }
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
