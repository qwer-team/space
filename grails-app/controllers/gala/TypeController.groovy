package gala

import grails.converters.JSON

class TypeController {

    def show(String tag) { 
        render Type.findByTag('black') as JSON
    }
    
    def update(){
        def type = Type.get(params.id)
        type.message = params.message
        type.save()
        render ([result: 'success'] as JSON)
    }
}
