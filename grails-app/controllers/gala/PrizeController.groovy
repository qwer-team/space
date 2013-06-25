package gala

import grails.converters.JSON
class PrizeController {

    def includeList = [
            'name',
            'flipper',
            'sms',
            'message1',
            'message2',
            'penalty',
            'img1',
            'img2',
        ]
    
    def list() { 
        def prizes = Prize.list()
        def result = [:]
        prizes.each{
            result[it.id] = it
        }
        render result as JSON
    }
    
    def show(Integer id){
        render Prize.get(id) as JSON
    }
    
    def add(){
        def prize = new Prize()
        bindData(prize, params, [include: includeList])
        prize.save()
        render ([result: 'success', id: prize.id] as JSON)
    }
    
    def update(Integer id){
        def prize = Prize.get(id)
        bindData(prize, params, [include: includeList])
        prize.save()
        render ([result: 'success'] as JSON)
    }
    
    def delete(Integer id){
        //todo
        def prize = Prize.get(id)
        def result = [result: "success"]
        if(!prize){
            result.result = 'fail'
        } else{
            if(!prize.delete()){
                result.result = 'fail'
            }
        }
        render (result as JSON)
    }
}
