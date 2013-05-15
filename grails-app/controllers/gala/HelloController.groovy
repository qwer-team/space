package gala

class HelloController {

    def index = {
        log.error 'vassa'
        render 'hello world' 
    }
}
