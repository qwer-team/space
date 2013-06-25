package gala

class PrizeElement {

    Prize prize
    String name
    Integer available = 0
    Integer visible = 0
    Float price = 0
    Integer account = 1
    String img1
    String img2
    Integer movingVariant
    Boolean blocked = false
    
    static constraints = {
        img1 nullable: true
        img2 nullable: true
    }
}
