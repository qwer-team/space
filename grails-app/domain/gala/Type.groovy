package gala

class Type {
    
    static hasMany = [subtypes: Subtype]
    
    String name
    String tag
    Integer a = 5
    Integer b = 5
    Integer c = 5
    Integer delta1 = 25
    Integer delta2 = 25
    String message1
    String message2
    String message3
    Boolean messActive
    Integer messCost
    Boolean onBet
    Integer messCountCost
    Boolean messCountDepActive
    Boolean onReturn
    Integer returnValue
    Boolean returnInPercent
    Boolean onNextStep
    Integer nextStepValue
    Boolean nextStepInPercent
    Integer hours
    Integer minutes
    
    static constraints = {
        message1 nullable: true
        message2 nullable: true
        message3 nullable: true
        messActive nullable: true
        messCost nullable: true
        onBet nullable: true
        messCountCost nullable: true
        messCountDepActive nullable: true
        onReturn nullable: true
        returnValue nullable: true
        returnInPercent nullable: true
        onNextStep nullable: true
        nextStepValue nullable: true
        nextStepInPercent nullable: true
        tag nullable: true
        hours nullable: true
        minutes nullable: true
    }
}

