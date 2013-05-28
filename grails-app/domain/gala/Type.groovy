package gala

class Type {
    
    static hasMany = [subtypes: Subtype]
    
    String name
    String tag
    Integer a = 0
    Integer b = 0
    Integer c = 0
    Integer delta1 = 0
    Integer delta2 = 0
    String message1
    String message2
    String message3
    Boolean onCost
    Integer cost
    Boolean onBet
    Integer bet
    Integer betType
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
        onCost nullable: true
        cost nullable: true
        onBet nullable: true
        bet nullable: true
        betType nullable: true
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

