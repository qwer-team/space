package gala

class Prize {
    static hasMany = [elements: PrizeElement]
    
    String name
    Integer flipper = 1
    String sms
    String message1
    String message2
    Integer penalty = 0
    String img1
    String img2
    
    static constraints = {
        message1 nullable: true
        message2 nullable: true
        img1 nullable: true
        img2 nullable: true
    }
}
