package gala

import grails.converters.JSON
class PrizeElementController {

    def includeList = [
            'name',
            'available',
            'visible',
            'price',
            'account',
            'img1',
            'img2',
            'movingVariant',
            'blocked',
    ]
     
    def get(Integer id){
        def element = PrizeElement.get(id)
        render element as JSON
    }
    
    def add(Integer id){
        def prize = Prize.get(id)
        def element = new PrizeElement()
        element.prize = prize
        bindData(element, params, [include: includeList])
        if(params.blocked == null || params.blocked == ''){
            element.blocked = false
        }
        element.save()
        render ([result: 'success', id: element.id] as JSON)
    }
    
    def update(Integer id){
        def element = PrizeElement.get(id)
        bindData(element, params, [include: includeList])
        if(params.blocked == null || params.blocked == ''){
            element.blocked = false
        }
        if(!element.save()){
            element.errors.each{
                println it
            }
        }
        
        render ([result: 'success'] as JSON)
    }
    
    def delete(Integer id){
        //todo
        def element = PrizeElement.get(id)
        element.delete()
        
        render ([result: 'success'] as JSON)
    }
}
