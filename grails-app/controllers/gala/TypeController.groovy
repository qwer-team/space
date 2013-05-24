package gala

import grails.converters.JSON

class TypeController {

    def show(String tag) {
        render Type.findByTag(tag) as JSON
    }
    
    def update(){
        println params
        def type = Type.get(params.id)
        type.message1 = params.message1
        type.message2 = params.message2
        type.message3 = params.message3
        type.onCost = params.onCost != ''
        type.cost = parseInt(params.cost)
        type.onBet = params.onBet != ''
        type.bet = parseInt(params.bet)
        type.betType = parseInt(params.betType)
        type.onReturn = params.onReturn != ''
        type.returnValue = parseInt(params.returnValue)
        type.returnInPercent = params.returnInPercent != ''
        type.onNextStep = params.onNextStep != ''
        type.nextStepValue = parseInt(params.nextStepValue) 
        type.nextStepInPercent = params.nextStepValue != ''
        type.hours = parseInt(params.hours) 
        type.minutes = parseInt(params.minutes) 
        type.save()
        render ([result: 'success'] as JSON)
    }
    
    private parseInt(value){
        Integer.parseInt((value!= "") ? value :'0') 
    }
    
    def list(){
        render Type.list() as JSON
    }
}
