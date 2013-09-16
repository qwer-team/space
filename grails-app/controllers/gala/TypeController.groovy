package gala

import grails.converters.JSON

class TypeController {

    def show(String tag) {
        render Type.findByTag(tag) as JSON
    }
    
    def update(){
        println params
        def type = Type.get(Integer.parseInt(params.id))
        def includeList = [
            'message1',
            'message2',
            'message3',
            'messActive',
            'messCost',
            'onBet',
            'messCountCost',
            'messCountDepActive',
            'onReturn',
            'returnValue',
            'returnInPercent',
            'nextStepValue',
            'onNextStep',
            'nextStepInPercent',
            'hours',
            'minutes',
            'image',
        ]
        bindData(type, params, [include: includeList])
        type.save()
        render ([result: 'success'] as JSON)
    }
    
    private parseInt(value){
        Integer.parseInt((value!= "") ? value :'0') 
    }
    
    def list(){
        render Type.list() as JSON
    }
    
    def updateCoords(){
        def type = Type.get(params.id)
        def includeList = [
            'a', 'b', 'c', 'delta1', 'delta2'
        ]
        bindData(type, params, [include: includeList])
        type.save()
        render ([result: 'success'] as JSON)
    }
}
